#Project 1

* Author: Tyler Bevan
* Class: CS253 Section 1

##Overview

This program read in text from Standard In (stdin) and counts how many
characters, lines, words, and digits are in the file. It reports these
statistics by printing to Standard Out, which can be redirected to a file.

##Compiling and Using

To compile this program using make and gcc run the following in the folder
with the source code:

    make

If you do not have make on your system, you can compile using gcc only:

    gcc -Wall -pipe -o homework main.c

To run the program using an input file and output file run:

    ./homework < file > results

The results will be placed in a text file named "results".

##Testing

I tested the program using the provided known good program as a reference.
I used several data files as inputs and this program reports the same output
as the reference program.

In addition, I wrote a script that performs the same analysis using the
standard unix utilities cat, grep, and wc. The script compares the output
of those utilities against the output from main.c. To test the program, run:

    bash tester.bash filename

The script will print PASS if the output is the same and FAIL if it is not.

##Discussion

I did not want to have to pass lots of variables around so I used a struct.
I had to research how to define a struct and create a global variable. In
the future I would likly pass by address instead, but in this small program
a global variable made sense.

I had trouble getting the case statement working for counting digits. After
much testing and trouble, I realized I was comparing the char code to the
integers 0-9. I changed it to compare them to the ASCII codes 48-57 and it
works as expected.

##Sources used

[A guide on how to use a global struct variable.](http://fresh2refresh.com/c-programming/c-passing-struct-to-function/)

