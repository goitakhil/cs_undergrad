#!/bin/bash
RED='\033[0;31m'

../src/jacobi2d test1.dat > /dev/null 2>&1
STATUS=$?
EXIT=0
if [[ $STATUS == 0  ]]; then
    printf "${RED}TEST FAILED: test1.dat succeeded when it should have failed!\n"
    EXIT=1
fi

../src/jacobi2d test2.dat > /dev/null 2>&1
STATUS=$?
if [[ $STATUS == 0  ]]; then
    printf "${RED}TEST FAILED: test2.dat succeeded when it should have failed!\n"
    EXIT=1
fi

../src/jacobi2d test3.dat > /dev/null 2>&1
STATUS=$?
if [[ $STATUS == 0  ]]; then
    printf "${RED}TEST FAILED: test3.dat succeeded when it should have failed!\n"
    EXIT=1
fi

../src/jacobi2d test4.dat > /dev/null 2>&1
STATUS=$?
if [[ $STATUS == 0  ]]; then
    printf "${RED}TEST FAILED: test4.dat succeeded when it should have failed!\n"
    EXIT=1
fi
exit ${EXIT}
