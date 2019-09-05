#include <ring.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

typedef struct buffer buffer;
/**
* Ring buffer to store logs in a log file.
*/
static struct buffer
{
  int curr;
  char log[MAX_LOG_ENTRY][MAX_STRING_LENGTH];
  pthread_mutex_t mutex;
  enum states
  {
    OPEN,
    CLOSED
  } state;
} buff;

static void *alarmWriter(void *);

/**
 * Creates buffer and starts alarm.
 */
void init_buffer()
{
  //printf("Initialize the ring buffer\n");
  int i;
  for (i = 0; i < MAX_LOG_ENTRY; i++)
  {
    buff.log[i][0] = '\0';
  }
  buff.curr = 0;
  pthread_mutex_init(&(buff.mutex), NULL);

  pthread_t alarmthread;

  pthread_create(&alarmthread, NULL, &alarmWriter, (void *)&dump_interval);
  pthread_detach(alarmthread);
  //free(alarmthread);
}

/**
* Get the current timestamp (localtime) from the system
*/
static char *getTimeString()
{
  time_t myTime;
  myTime = time(NULL); // this is a system call
  char *timeString = ctime(&myTime);
  timeString[strlen(timeString) - 1] = '\0'; // erase the newline at the end
  return timeString;
}

/**
* Adds a new log message to the buffer.
* Current implementation is NOT thread safe.
*/
void log_msg(char *entry)
{
  if (entry == NULL)
  {
    //printf("Skipping null log entry!\n");
    return;
  }

  char *timeString = getTimeString();
  //printf("Adding log entry into buffer\n");
  pthread_mutex_lock(&(buff.mutex));
  int idx = buff.curr;
  strncpy(buff.log[idx], timeString, MAX_STRING_LENGTH - 1);
  strncat(buff.log[idx], " -- ", 4);
  strncat(buff.log[idx], entry, MAX_STRING_LENGTH - strlen(timeString) - 4);
  /* Add null terminator manually per strncpy docs. */
  buff.log[idx][MAX_STRING_LENGTH - 1] = '\0';
  buff.curr++;
  if (buff.curr == MAX_LOG_ENTRY)
  {
    buff.curr = 0;
  }
  pthread_mutex_unlock(&(buff.mutex));
}

/**
 * Writes the contents of the buffer to the log file in 
 * oldest to newest order. Overwrites the entire log file
 * on each dump.
 */
static void dump_buffer()
{
  pthread_mutex_lock(&(buff.mutex));
  FILE *logFile = fopen(log_name, "w");
  int i;
  int linenum = 0;
  for (i = 0; i < MAX_LOG_ENTRY; i++)
  {
    int index = (buff.curr + i) % MAX_LOG_ENTRY;
    if (strlen(buff.log[index]) != 0)
    {
      fprintf(logFile, "log %d: %s\n", linenum, buff.log[index]);
      linenum++;
    }
  }
  fclose(logFile);
  pthread_mutex_unlock(&(buff.mutex));
}

/**
* Writes the buffer to log periodically as defined by its parameter.
*/
void *alarmWriter(void *interval)
{
  int time = *(int *)interval;
  while (0 == 0)
  {
    dump_buffer();
    if (buff.state == CLOSED)
    {
      break;
    }
    sleep(time);
  }
  pthread_exit(NULL);
}

/**
* Tells the writer thread to exit on its next iteration.
*/
void free_buffer()
{
  buff.state = CLOSED;
}
