#include <ring.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

static void *thread_main(void *numLogs)
{
    int i;
    int max = *((int *)numLogs);
    for (i = 0; i < max; i++)
    {
        char buffer[MAX_STRING_LENGTH];
        sprintf(buffer, "Thread %lu: log %i", pthread_self(), i);
        log_msg(buffer);
    }
    pthread_exit(NULL);
}

int main(int argc, char **args)
{
    if (argc < 2)
    {
        fprintf(stderr, "Usage: %s <num_threads> <num_logs>\n", args[0]);
        exit(1);
    }

    init_buffer();

    int maxThreads = atoi(args[1]);
    int maxLogs = atoi(args[2]);

    pthread_t *tids;
    tids = (pthread_t *)malloc(sizeof(pthread_t) * maxThreads);

    int i;
    for (i = 0; i < maxThreads; i++)
    {
        pthread_create(&tids[i], NULL, thread_main, (void *)&maxLogs);
    }

    for (i = 0; i < maxThreads; i++)
    {
        pthread_join(tids[i], NULL);
    }
    free(tids);
    free_buffer();
    sleep(5);
    exit(EXIT_SUCCESS);
}
