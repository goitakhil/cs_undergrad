#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#ifdef USE_MPI
#include <mpi.h>
#endif // USE MPI
using namespace std;
long double calculate_pi(unsigned long long iterations, int precision);

void print_help(char* const name) {
    printf("%s\n", name);
    printf("  -n <iterations>         :  Number of iterations to run\n");
    printf("  -p <decimal precision>  :  Number of correct decimal places to calculate\n");
    printf("  -h                      :  This help page\n");
}


int main( int argc, char* argv[] ) {
    #ifdef USE_MPI
    MPI_Init(&argc, &argv);
    #endif // USE MPI
    int c=0;
    unsigned long long nvalue = 0;
    int pvalue = 5;
    char *term;
    
    while ((c = getopt (argc, argv, "n:p:h")) != -1){
        switch(c) {
            case 'n':
                nvalue = strtoull(optarg, &term, 10);
                break;
            case 'p':
                pvalue = atoi(optarg);
                break;
            case 'h':
                print_help(argv[0]);
                return(0);
                break;
        }
    }
    long double result = calculate_pi(nvalue, pvalue);
    if (result > 0) {
        printf("%.20Lf\n", result);
    }
    #ifdef USE_MPI
    MPI_Finalize();
    #endif // USE MPI
}
