/*
 * File name: CmdLine_unittests.cpp
 * Created on: 24-May-2017
 * Author: ravi
 */

#include "../src/Configuration.hpp"
#include "gtest/gtest.h"
#include <getopt.h>


class ConfigurationTest : public testing::Test {
  protected:

  char** commonArgSpace;
  int numberOfArgs;
  int maxLengthOfArg;

  virtual void SetUp(){
    numberOfArgs = 10;
    maxLengthOfArg = 256;
    commonArgSpace = allocateTestArgs(numberOfArgs,maxLengthOfArg);
  }

  static char** allocateTestArgs(int N,int M){
    char** someArgs = (char**)malloc(sizeof(char*)*N);
    for(int i=0;i<N;i++){
      someArgs[i] = (char*)malloc(sizeof(char)*M);
    }
    return someArgs;
  }
};


/****************************************************************************
*
* Begin valid command line option tests
*
****************************************************************************/

// Tests valid short command line options
TEST_F(ConfigurationTest, validInt) {
  optind = 0; // Need to reset optind to 0 for each test
  numberOfArgs = 3;
  strncpy(commonArgSpace[0],"test",5);
  strncpy(commonArgSpace[1],"-c",3);
  strncpy(commonArgSpace[2],"2",2);

  Configuration config;
  config.addParamInt("count",'c',0,"-c [num] the number of something");
  ASSERT_NO_THROW({
    config.parse(numberOfArgs,commonArgSpace);
  });

  // Get any particular command-line input.
  int c = config.getInt("count");
  ASSERT_EQ(2,c);

  // Generate an LDAP string to output configuration.
  string outString = config.toLDAPString();
  ASSERT_TRUE(outString.find("count:2")!= std::string::npos);

}

