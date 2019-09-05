/*
 * File name: FibonacciTests.cpp
 * Created on: September 5 2018
 * Author: Greg Brasier
 */

#include "../include/Fibonacci.hpp"
#include "../../googletest/googletest/include/gtest/gtest.h"
#include <getopt.h>


class FibonacciTest : public testing::Test {
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

// Test positive inputs of n
TEST(FibonacciTest, testPositiveInputs) {
    EXPECT_EQ(find_fib(0), 0);
    EXPECT_EQ(find_fib(1), 1);
    EXPECT_EQ(find_fib(2), 1);
    EXPECT_EQ(find_fib(3), 2);
    EXPECT_EQ(find_fib(4), 3);
    EXPECT_EQ(find_fib(5), 5);
    EXPECT_EQ(find_fib(6), 8);
    EXPECT_EQ(find_fib(7), 13);
    EXPECT_EQ(find_fib(8), 21);
    EXPECT_EQ(find_fib(9), 34);
    EXPECT_EQ(find_fib(10), 55);
    EXPECT_EQ(find_fib(11), 89);
    EXPECT_EQ(find_fib(12), 144);
    EXPECT_EQ(find_fib(13), 233);
    EXPECT_EQ(find_fib(14), 377);
    EXPECT_EQ(find_fib(15), 610);
    EXPECT_EQ(find_fib(25), 75025);
    EXPECT_EQ(find_fib(30), 832040);
    EXPECT_EQ(find_fib(40), 102334155);
}

// Test negative inputs of n. Should return some error code (0)
// if not implementing negative index.
TEST(FibonacciTest, testNegativeInputs) {

    // TODO: How to implement a test that expects an error? Just expect -1?
    EXPECT_EQ(find_fib(-1), -1);
    EXPECT_EQ(find_fib(-2), -1);   
    EXPECT_EQ(find_fib(-3), -1);   
    EXPECT_EQ(find_fib(-4), -1);
    EXPECT_EQ(find_fib(-5), -1);         
}

/*
// Test non-integer values of n.  Should return some kind of error code or message.
TEST(FibonacciTest, testNonIntegers) {
    // TODO: How to implement a test that expects an error? Just expect -1?
    EXPECT_EQ(find_fib(0.1), -1);
    EXPECT_EQ(find_fib(1.1), -1);
    //EXPECT_EQ(find_fib("abc"), -1);      // Doesn't work because of param type
    //EXPECT_EQ(find_fib("a1b2c3"), 0);    // Doesn't work because of param type
    //EXPECT_EQ(find_fib("1st"), 0);       // Doesn't work because of param type
    //EXPECT_EQ(find_fib("ten"), 0);       // Doesn't work because of param type
    //EXPECT_EQ(find_fib(NULL), 0);        // Gives a warning because of NULL
    EXPECT_EQ(find_fib(2147483648), 0); // One over INT_MAX
}
*/


