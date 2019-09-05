#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <mpi.h>
using namespace std;
long double calculate_pi(unsigned long long samples, int seed, bool timing);

void print_help(char* const name) {
    printf("%s\n", name);
    printf("  -n <samples>         :  Number of samples to run\n");
    printf("  -s <seed>            :  Seed for srand\n");
    printf("  -d                   :  Print debug info (overrides timing mode)\n");
    printf("  -t                   :  Time the code\n");
    printf("  -h                   :  This help page\n");
}


int main( int argc, char* argv[] ) {
    MPI_Init(&argc, &argv);
    int c=0;
    unsigned long long nvalue = 0;
    int seed = -1;
    char *term;
    bool debug = false;
    bool timing = false;
    
    while ((c = getopt (argc, argv, "n:s:dth")) != -1){
        switch(c) {
            case 'n':
                nvalue = strtoull(optarg, &term, 10);
                break;
            case 's':
                seed = atoi(optarg);
                break;
            case 'd':
                debug = true;
                break;
            case 't':
                timing = true;
                break;
            case 'h':
                print_help(argv[0]);
                MPI_Finalize();
                return(0);
                break;
        }
    }
    long double result = calculate_pi(nvalue, seed, timing);
    if (result > 0) {
        if (debug) {
            printf("Our Result:\n");
            printf("%.80Lf\n", result);
            printf("For reference:\n");
            long double pild = 3.14159265358979323846264338327950288419716939937510582097494459230781640628620899L;
            double pid = pild;
            float pif = pid;
            printf("%s\n%1.80f\n%1.80f\n%1.80Lf\n", "3.14159265358979323846264338327950288419716939937510582097494459230781640628620899", pif, pid, pild);
        } else if (!timing) {
            printf("%1.80Lf\n", result);
        }
    }
    MPI_Finalize();
}
