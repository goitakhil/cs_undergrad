
Doubly-linked Lists {#mainpage}
===================

This program is a library that implements a doubly linked list. As it is a
library, it can be tested by running other programs that use its functions.

To compile the library, run make on Makefile.

  make

To run the test programs you will need to set the paths to find the library:

  export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./lib

Then run the test programs as follows:

  testsuite/SimpleTestList

Next, run the unit test.

  testsuite/UnitTestList

Finally, run a larger test that uses the list.

  testsuite/RandomTestList

Each program takes command line arguments and will give a help message if run
without the command line arguments. The test programs test all fuctions with
three, one, and no nodes.
