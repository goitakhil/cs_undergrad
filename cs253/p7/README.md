
Thread Safe Ring Buffer Library {#mainpage}
===================

This program implements a ring buffer for storing and periodically writing 
logs to disk.

##Building
To build the program run:
 ```
 make
 ```

The library comes with a test program that can be run as:
```
./run.sh
```

##Testing

This program is tested within the run.sh script using valgrind and a series of
different settings. This program passes valgrind so long as the testing program
closes the log file and buffer properly before exiting.

##Discussion

This program uses a thread to periodically write to disk. The thread is
in a loop that logs then sleeps. It will exit if an exit variable is set in its
scope. To ensure thread safety, a mutex is used to lock the ring buffer while
the logger is writing to disk.

In addition, only one thread may be logging into the buffer at a time. This
is also enforced using mutex. Once all threads writing to the buffer are
finished, the programmer should call the close_buffer method on the library
to cleanly exit the logger thread.

This method was created to fix a persistant memory leak in the library. 
While working on it, I found that the logger thread was getting terminated
by the parent instead of calling Pthread_exit. By closing the buffer
first, I can ensure that all the buffer's memory is freed properly. There
are still some small memory warnings in valgrind, but they are inside of the
Pthread library.

The performance was higher using smaller numbers of threads. Because only one
thread can write to the disk at a time, more threads only adds overhead to the
process.
```
  Threads       Logs   Time(sec)
        8     125000        7.41
        4     250000        7.38
        2     500000        7.13
        1    1000000        6.44
```
##Sources
None used