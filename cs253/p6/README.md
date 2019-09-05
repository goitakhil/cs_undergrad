
Ring Buffer Library {#mainpage}
===================

This program implements a ring buffer for storing and periodically writing 
logs to disk.

##Building
To build the program run:
  ```make```

The library comes with a test program that can be run as:
  ```./grader```

##Discussion

To print to a file instead of the terminal, I used the fprintf() method.
A SIGALRM is sent every alarm_interval seconds, and triggers a write to
disk. The program does not currently enforce thread safety, so a write
during the save will not be prevented. Every write removes the old
log file.

The provided program used modulus division to decice where to insert into 
the ring, but to prevent an integer overflow error I am now setting the 
current index to zero when it exceeds the size of the ring. Modulus is 
still used to print the records in chronological order.

##Sources
None used