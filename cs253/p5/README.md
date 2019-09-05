
Bash Test Script
================

Tyler Bevan
Project 5
CS 253

This project consists of a script that tests a given program against a series
of input files.

This program runs make to ensure that the program to be tested is built. It 
then uses a for loop to run tests on all files in the folder named input. It 
will create an output folder if one does not exist.

The script runs each file using different sets of options and compares the 
output of the two using diff. If the two outputs are the same, the file passes.
To ensure that the files are in the same order, they are passed through sort.
The program also times the run using both options for additional information.
