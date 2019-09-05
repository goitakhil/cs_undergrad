#!/usr/bin/env python3
import datetime as dt
from datetime import date, timedelta
from flask import Flask, jsonify, abort, make_response, request
from flask_cors import CORS
import logging
from numba import jit, int64
import numpy as np
import pandas as pd
from pwd import getpwnam
import pymysql
import pymysql.cursors
import subprocess
from threading import Event, Thread, Lock
import time
import yaml

logging.basicConfig(level=logging.DEBUG)
log = logging.getLogger('main')

app = Flask(__name__)
CORS(app)

# Define globals
users = None
departments = None
colleges = None
jobs = None
waits = dict()
partlist = set()
totals = None
by_college = dict()
by_department = dict()
by_partition = dict()
mutex = Lock()

######## API Methods

@app.route('/api/v1/config', methods=['GET'])
def get_config():
    return jsonify({"departments": departments["department"].to_dict(),
                    "colleges": colleges["college"].to_dict(),
                    "dept_to_col": departments["college_id"].to_dict()})

@app.route('/api/v1/total', methods=['GET'])
def get_total():
    try:
        start = int(dt.datetime.strptime(request.args['start'], '%Y-%m-%d').timestamp())
        end = int(dt.datetime.strptime(request.args['end'], '%Y-%m-%d').timestamp())
        return totals[(totals.time >= start) & (totals.time <= end)].drop(columns=['time']).to_json(orient='records')
    except Exception as e:
        return make_response(jsonify({'error': str(e)}), 400)

@app.route('/api/v1/dept', methods=['GET'])
def get_by_dept():
    try:
        start = int(dt.datetime.strptime(request.args['start'], '%Y-%m-%d').timestamp())
        end = int(dt.datetime.strptime(request.args['end'], '%Y-%m-%d').timestamp())
        dept_id = int(request.args['dept_id'])
        if dept_id not in departments["department"].to_dict():
            raise Exception("Invalid Department ID")
        data = by_department[dept_id]
        return data[(data.time >= start) & (data.time <= end)].drop(columns=['time']).to_json(orient='records')
    except Exception as e:
        return make_response(jsonify({'error': str(e)}), 400)

@app.route('/api/v1/college', methods=['GET'])
def get_by_college():
    try:
        start = int(dt.datetime.strptime(request.args['start'], '%Y-%m-%d').timestamp())
        end = int(dt.datetime.strptime(request.args['end'], '%Y-%m-%d').timestamp())
        college_id = int(request.args['college_id'])
        if college_id not in colleges["college"].to_dict():
            raise Exception("Invalid College ID")
        data = by_college[college_id]
        return data[(data.time >= start) & (data.time <= end)].drop(columns=['time']).to_json(orient='records')
    except Exception as e:
        return make_response(jsonify({'error': str(e)}), 400)

@app.route('/api/v1/partition', methods=['GET'])
def get_by_partition():
    try:
        start = int(dt.datetime.strptime(request.args['start'], '%Y-%m-%d').timestamp())
        end = int(dt.datetime.strptime(request.args['end'], '%Y-%m-%d').timestamp())
        partition = int(request.args['partition'])
        if partition not in partlist:
            raise Exception("Invalid Partition")
        data = by_partition[partition]
        return data[(data.time >= start) & (data.time <= end)].drop(columns=['time']).to_json(orient='records')
    except Exception as e:
        return make_response(jsonify({'error': str(e)}), 400)

@app.route('/api/v1/wait', methods=['GET'])
def get_waits():
    try:
        start = int(dt.datetime.strptime(request.args['start'], '%Y-%m-%d').timestamp())
        end = int(dt.datetime.strptime(request.args['end'], '%Y-%m-%d').timestamp())
        temp_map = dict()
        temp_map['data'] = dict()
        max_wait = 0
        for partition in partlist:
            data = waits[partition]
            temp_map['data'][partition] = data[(data.time >= start) & (data.time <= end)].drop(columns=['time']).to_dict(orient='records')
            this_max = data[(data.time >= start) & (data.time <= end)]['waitave'].max()
            max_wait = max_wait if max_wait > this_max else this_max
        temp_map['max'] = max_wait
        return jsonify(temp_map)
    except Exception as e:
        return make_response(jsonify({'error': str(e)}), 400)

@app.route('/api/v1/percent/dept', methods=['GET'])
def get_percent_dept():
    try:
        start = int(dt.datetime.strptime(request.args['start'], '%Y-%m-%d').timestamp())
        end = int(dt.datetime.strptime(request.args['end'], '%Y-%m-%d').timestamp())
        result = dict()
        for key, _ in by_department.items():
            data = by_department[key]
            size = data.values.shape[0]
            result[key] = 0.0
            max_core = 0
            for row in data[(data.time >= start) & (data.time <= end)][["time","cores"]].values:
                result[key] += row[1]
                max_core += get_capacity(row[0])
        reslist = []
        for key, val in result.items():
            reslist.append({"id": key, "value": (val / max_core)})
        return jsonify(reslist)
    except Exception as e:
        return make_response(jsonify({'error': str(e)}), 400)

@app.route('/api/v1/percent/college', methods=['GET'])
def get_percent_college():
    try:
        start = int(dt.datetime.strptime(request.args['start'], '%Y-%m-%d').timestamp())
        end = int(dt.datetime.strptime(request.args['end'], '%Y-%m-%d').timestamp())
        result = dict()
        for key, _ in by_college.items():
            data = by_college[key]
            size = data.values.shape[0]
            result[key] = 0.0
            max_core = 0
            for row in data[(data.time >= start) & (data.time <= end)][["time","cores"]].values:
                result[key] += row[1]
                max_core += get_capacity(row[0])
        reslist = []
        for key, val in result.items():
            reslist.append({"id": key, "value": (val / max_core)})
        return jsonify(reslist)
    except Exception as e:
        return make_response(jsonify({'error': str(e)}), 400)

@app.route('/api/v1/disk', methods=['GET'])
def get_disk_space():
    try:
        result = dict()
        for drive in ['home', 'scratch']:
            df_res = subprocess.run(['df', '-BG', "/" + drive], stdout=subprocess.PIPE).stdout.decode('utf-8').split('\n')[1]
            total = float(df_res.split()[1].replace('G', '')) / 1024
            used = float(df_res.split()[2].replace('G', '')) / 1024
            result[drive] = {"total": total, "used": used}
        return jsonify(result)
    except Exception as e:
        return make_response(jsonify({'error': str(e)}), 400)

@app.route('/api/v1/maxcores', methods=['GET'])
def get_max_cores():
    try:
        start = dt.datetime.strptime(request.args['start'], '%Y-%m-%d')
        end = dt.datetime.strptime(request.args['end'], '%Y-%m-%d')
        delta = end - start         # timedelta
        dates = []
        for i in range(delta.days + 1):
            dates.append(start + timedelta(i))
        result = []
        for a_date in dates:
            result.append({'date': a_date.strftime('%Y-%m-%d'), 'value': get_capacity(a_date.timestamp())})
        return jsonify(result)
    except Exception as e:
        return make_response(jsonify({'error': str(e)}), 400)

######### Data Munging

def get_capacity(t):
    if t < dt.datetime(2017, 12, 15).timestamp():
        return 756
    elif t < dt.datetime(2018, 2, 14).timestamp():
        return 812
    elif t < dt.datetime(2018, 6, 8).timestamp():
        return 868
    elif t < dt.datetime(2018, 7, 9).timestamp():
        return 980
    else:
        return 1008


def load_db(config):
    log = logging.getLogger('updater')
    event = Event()
    while True:
        global users
        global departments
        global colleges
        global jobs
        global partlist
        global totals
        global by_college
        global by_department
        global by_partition
        global mutex
        mutex.acquire()
        users = []
        departments = []
        colleges = []
        mariadb_connection = pymysql.connect(user=config['xdmoddb']['username'], password=config['xdmoddb']['password'],
                                             db=config['xdmoddb']['name'], host='127.0.0.1',
                                             cursorclass=pymysql.cursors.DictCursor)
        with mariadb_connection.cursor() as cursor:
            cursor.execute('SELECT account_name, parent_id AS department_id '
                         + 'FROM hpcdb_accounts '
                         + 'JOIN hpcdb_requests USING (account_id) '
                         + 'JOIN hpcdb_principal_investigators USING (request_id) '
                         + 'JOIN hpcdb_fields_of_science ON (primary_fos_id = field_of_science_id)')
            result = cursor.fetchall()
            for row in result:
                users.append([row['account_name'], row['department_id']])

            cursor.execute('SELECT field_of_science_id AS department_id, parent_id AS college_id, description '
                         + 'FROM hpcdb_fields_of_science '
                         + 'WHERE abbrev LIKE "%div%" ')
            result = cursor.fetchall()
            for row in result:
                departments.append([row['department_id'], row['college_id'], row['description']])

            cursor.execute('SELECT field_of_science_id AS college_id, description '
                         + 'FROM hpcdb_fields_of_science '
                         + 'WHERE abbrev LIKE "%unit%" ')
            result = cursor.fetchall()
            for row in result:
                colleges.append([row['college_id'], row['description']])

        departments_df = pd.DataFrame(departments, columns=['dept_id', 'college_id', 'department']).drop_duplicates().set_index(['dept_id'])
        colleges_df = pd.DataFrame(colleges, columns=['college_id', 'college']).drop_duplicates().set_index(['college_id'])
        users_df = pd.DataFrame(users, columns=['username', 'dept_id']).drop_duplicates().replace('', np.nan).dropna().set_index(['username'])
        users_df = users_df.join(departments_df, on='dept_id').fillna(-1).drop(labels=['department'], axis=1)
        def get_uid(uname):
            try:
                uid = int(getpwnam(uname).pw_uid)
            except:
                uid = int(-1)
            return uid
        users_df['user_id'] = users_df.apply(lambda x: get_uid(x.name), axis=1)
        mariadb_connection.close()
        users = users_df
        departments = departments_df
        colleges = colleges_df
        log.info("User -> Department -> College mappings updated from database")

        joblist = []
        mariadb_connection = pymysql.connect(user=config['slurmdb']['username'], password=config['slurmdb']['password'],
                                             db=config['slurmdb']['name'], host='127.0.0.1',
                                             cursorclass=pymysql.cursors.DictCursor)
        with mariadb_connection.cursor() as cursor:
            cursor.execute("SELECT cpus_req, id_user, partition, time_eligible, time_start, time_end FROM slurm_cluster_job_table WHERE time_start != 0 ORDER BY time_start")
            result = cursor.fetchall()
            for row in result:
                joblist.append([int(row["cpus_req"]), row['id_user'], row['partition'], int(row["time_start"]), int(row["time_end"]), int(row["time_eligible"])])
        job_df = pd.DataFrame(joblist, columns=['cpus', 'user_id', 'partition', 'time_start', 'time_end', 'time_eligible']).fillna(0)
        job_df = job_df.join(users.reset_index().set_index('user_id'), on=['user_id']).drop(labels=['user_id', 'username'], axis=1)
        mariadb_connection.close()
        jobs = job_df
        partlist = set(jobs['partition'].values)
        partlist.discard('')
        log.info("partlist:  %s", partlist)
        log.info("Job list updated from database")
        
        start = dt.datetime(2017,1,1).timestamp()
        end = time.time()

        points = pd.DataFrame(generate_points(start, end, jobs[["cpus", "time_start", "time_end"]].values), columns=['time', 'cores'])
        points['year'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).year)
        points['month'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).month)
        points['day'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).day)
        averages = points.groupby(['year', 'month', 'day']).mean().reset_index()
        averages['date'] = averages['time'].map(lambda x: dt.datetime.fromtimestamp(x).strftime('%Y-%m-%d'))
        totals = averages.drop(columns=['year', 'month', 'day'])
        log.info('Total table updated')

        for partition in partlist:
            waittimes = pd.DataFrame(generate_waits(start, end, jobs[(jobs.partition == partition)][["time_eligible", "time_start", "time_end"]].values), columns=['time', 'waitave'])
            waittimes['year'] = waittimes['time'].map(lambda x: dt.datetime.fromtimestamp(x).year)
            waittimes['month'] = waittimes['time'].map(lambda x: dt.datetime.fromtimestamp(x).month)
            waittimes['day'] = waittimes['time'].map(lambda x: dt.datetime.fromtimestamp(x).day)
            averages = waittimes.groupby(['year', 'month', 'day']).mean().reset_index()
            averages['date'] = averages['time'].map(lambda x: dt.datetime.fromtimestamp(x).strftime('%Y-%m-%d'))
            waits[partition] = averages.drop(columns=['year', 'month', 'day'])
        log.info('Wait table updated')
        
        for dept_id in departments["department"].to_dict():
            points = pd.DataFrame(generate_points(start, end,
                                                  jobs[(jobs.dept_id == dept_id)][["cpus", "time_start", "time_end"]].values),
                                  columns=['time', 'cores'])
            points['year'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).year)
            points['month'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).month)
            points['day'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).day)
            averages = points.groupby(['year', 'month', 'day']).mean().reset_index()
            averages['date'] = averages['time'].map(lambda x: dt.datetime.fromtimestamp(x).strftime('%Y-%m-%d'))
            by_department[dept_id] = averages.drop(columns=['year', 'month', 'day'])
        log.info('Department tables updated')
        for college_id in colleges["college"].to_dict():
            points = pd.DataFrame(generate_points(start, end,
                                                  jobs[(jobs.college_id == college_id)][["cpus", "time_start", "time_end"]].values),
                                  columns=['time', 'cores'])
            points['year'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).year)
            points['month'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).month)
            points['day'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).day)
            averages = points.groupby(['year', 'month', 'day']).mean().reset_index()
            averages['date'] = averages['time'].map(lambda x: dt.datetime.fromtimestamp(x).strftime('%Y-%m-%d'))
            by_college[college_id] = averages.drop(columns=['year', 'month', 'day'])
        log.info('College tables updated')
        for partition in partlist:
            points = pd.DataFrame(generate_points(start, end,
                                                  jobs[(jobs.partition == partition)][["cpus", "time_start", "time_end"]].values),
                                  columns=['time', 'cores'])
            points['year'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).year)
            points['month'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).month)
            points['day'] = points['time'].map(lambda x: dt.datetime.fromtimestamp(x).day)
            averages = points.groupby(['year', 'month', 'day']).mean().reset_index()
            averages['date'] = averages['time'].map(lambda x: dt.datetime.fromtimestamp(x).strftime('%Y-%m-%d'))
            by_partition[partition] = averages.drop(columns=['year', 'month', 'day'])
        log.info('Partition tables updated')
        points = None
        mutex.release()
        event.wait(300)


@jit(int64[:,:](int64,int64,int64[:,:]), nopython=True)
def generate_points(start, end, jobs):
    running = [0]
    points = np.zeros((((int(end)-int(start))//(10*60)),2), dtype=np.int64)
    cursor = 0
    this_time = int(start)
    step = 0
    while this_time < int(end):
        points[step][0] = this_time
        while cursor < len(jobs) and jobs[cursor][1] < this_time:
            running.append(cursor)
            cursor += 1
        for job in running[:]:
            if jobs[job][2] < this_time and jobs[job][2] > 0:
                running.remove(job)
        for job in running:
            points[step][1] += jobs[job][0]
        step += 1
        this_time += 10*60
    return points


@jit(int64[:,:](int64,int64,int64[:,:]), nopython=True)
def generate_waits(start, end, jobs):
    running = [0]
    points = np.zeros((((int(end)-int(start))//(10*60)),2), dtype=np.int64)
    cursor = 0
    this_time = int(start)
    step = 0
    while this_time < int(end):
        points[step][0] = this_time
        while cursor < len(jobs) and jobs[cursor][1] < this_time:
            running.append(cursor)
            cursor += 1
        for job in running[:]:
            if jobs[job][2] < this_time and jobs[job][2] > 0:
                running.remove(job)
        for job in running:
            points[step][1] += ((jobs[job][1] - jobs[job][0]) / len(running)) / (60*60)
        step += 1
        this_time += 10*60
    return points


if __name__ == '__main__':
    with open('config.yml', 'r') as conf_file:
        config = yaml.load(conf_file)
    log.info('Read config.yml')
    loader = Thread(target=load_db, kwargs={'config': config})
    loader.daemon = True
    loader.start()
    log.info('Spawned updater thread')
    app.run(debug=False)
