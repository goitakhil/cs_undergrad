
#define TRUE 1
#define FALSE 0

const int INSERTION_SORT_THRESHOLD = 100; //based on trial and error

struct arg_set {
    int *A;
    int p;
    int r;
    int threads;
};

// function prototypes
void serial_mergesort(int A[], int p, int r); 
void merge(int A[], int p, int q, int r);
void insertion_sort(int A[], int p, int r);
void *parallel_sort_thread(void* args);
