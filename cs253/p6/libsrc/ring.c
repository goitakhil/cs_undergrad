#include <ring.h>
#include <signal.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

static struct {
  int curr;
  char log[MAX_LOG_ENTRY][MAX_STRING_LENGTH];
} buff;

static void onalarm(int signo);

/**
 * Creates buffer and starts alarm.
 */
void init_buffer() {
  //printf("Initialize the ring buffer\n");
  int i;
  for (i = 0; i < MAX_LOG_ENTRY; i++) {
    buff.log[i][0] = '\0';
  }
  buff.curr = 0;
  signal(SIGALRM, onalarm);
  alarm(alarm_interval);
}

// get the current timestamp (localtime) from the system
static char *getTimeString() {
  time_t myTime;
  myTime = time(NULL); // this is a system call
  char *timeString = ctime(&myTime);
  timeString[strlen(timeString) - 1] = '\0'; // erase the newline at the end
  return timeString;
}

/**
* Adds a new log message to the buffer.
* Current implementation is NOT thread safe.
*/
void log_msg(char *entry) {
  if (entry == NULL) {
    //printf("Skipping null log entry!\n");
    return;
  }

  char *timeString = getTimeString();
  //printf("Adding log entry into buffer\n");
  int idx = buff.curr;
  strncpy(buff.log[idx], timeString, MAX_STRING_LENGTH - 1);
  strncat(buff.log[idx], " -- ", 4);
  strncat(buff.log[idx], entry, MAX_STRING_LENGTH - strlen(timeString) - 4);
  /* Add null terminator manually per strncpy docs. */
  buff.log[idx][MAX_STRING_LENGTH - 1] = '\0';
  buff.curr++;
  if (buff.curr == MAX_LOG_ENTRY) {
    buff.curr = 0;
  }
}

/**
 * Writes the contents of the buffer to the log file in 
 * oldest to newest order. Overwrites the entire log file
 * on each dump.
 */
static void dump_buffer() {
  FILE *logFile = fopen(log_name, "w");
  int i;
  for (i = 0; i < MAX_LOG_ENTRY; i++) {
    int index = (buff.curr + i) % MAX_LOG_ENTRY;
    if (buff.log[index] != NULL) {
      fprintf(logFile, "log %d: %s\n", i, buff.log[index]);
    }
  }
}


/**
 * Calls dump_buffer() when SIGALRM is caught.
 * Restarts the alarm as well.
 */
static void onalarm(int signo) {
  dump_buffer();
  alarm(alarm_interval);
}
