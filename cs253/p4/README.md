Project 4 - CSV Makefile
=========================================================================

* Author: Tyler Bevan
* Class: CS253 Section 1

##Overview

This makefile compiles the source files in this folder and builds
dependencies automatically.

##Compiling and Using

To compile this program using make and gcc run the following in the folder
with the source code:

        make

To run the program do:
	
	make run

To clean up the build:

	make clean

##Discussion

The makefile uses the gcc flag -MMD to include the needed headers
into a .d file, which gets included by the makefile. This allows
the makefile to be very consise while being reliable.
