CS410 Databases Project
=======================

Files
-----

* Makefile - GNU Makefile
* pom.xml - Maven build descriptor
* /src/main/java/cs410/taskshell/TaskShell.java - Task shell source

Usage
-----

This project requires maven to build.
To build the project do:

`make`

To run the shell do:

`java -jar target/taskshell-1.0-SNAPSHOT-jar-with-dependencies.jar <jdbc string>`

Design
------

To build the shell, I used the example code from the charity database. I am
using the cliche library to power the shell code. I did not have any real
issues while working on this project aside from figuring out how to get the
full text search working correctly. I didn't struggle too bad with getting
the index set up, but more with the SQL syntax on match/against queries.

I also had a little trouble getting the tags to show up correctly in the show
function, as I have to run a second query to get the list of tags for the
selected task.
