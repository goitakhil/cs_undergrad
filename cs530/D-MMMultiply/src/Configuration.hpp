// Created by Erman Gurses on 2/8/16.
// Modified by Rutuja Patil on 3/28/2016.
// Modified by Sarah Willer on 06/24/2016.
/*****************************************************************************
 * Configuration.h header file
 *
 * See themes/Configuration.txt for the common command line parameters. These
 * and custom parameters are added through explicit calls to the addParam 
 * methods. addParams call hasConflict to check that no duplicate fieldnames or
 * command characters are added.
 *
 * Assumptions:
 *      Max number of options is 50.
 *
 * Usage:
 *      Configuration config();
 *      config.addParamInt("count",'c',0,"-c [num] the number of something");
 *      config.parse(argc,argv);
 *
 *      // Get any particular command-line input.
 *      int bx = config.getInt("bx");
 *
 *      // Generate an LDAP string to output configuration.
 *      string outString = config.toLDAPString();
 *
 ****************************************************************************/
#ifndef CONFIGURATION_H
#define CONFIGURATION_H

#define MAX_NUM_OPTIONS 50

#include <string>
#include <map>
#include <set>
#include <list>
#include <getopt.h>

using namespace std;

class Configuration
{
  public:

    Configuration();
    ~Configuration();

    int getInt(string fieldname);
    bool getBool(string fieldname);
    string getString(string fieldname);

    void parse(int argc, char* argv[]);
    string toLDAPString();

    bool hasConflict(string fieldname, char commandChar);
    void addParamInt(string fieldname, char single_char,
                     int init_val, string help_str);
    void addParamBool(string fieldname, char single_char,
                             bool init_val, string help_str);    
    void addParamString(string fieldname,char single_char,
                   string init_val,string help_str);
    void terminateOptionsArray();

  protected:

    void processArgVal(string fieldname, char * arg_val);
    int parseInt( char* string );

  private:

    string mExecName;
    string mUsageString;
    string mCommandChars;          // For use with long_options 
    int mCount; // count of objects
    list<int> mCharOrder;
    map<int,string> mCharToFieldname;
    set<int> mKnownChars;
    map<string,int> mIntMap;
    map<string,bool> mBoolMap;
    map<string,string> mStringMap;

    struct option mLongOptions[MAX_NUM_OPTIONS];
 

};
#endif   
