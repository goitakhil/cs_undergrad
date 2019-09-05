#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

using namespace std;
long double calculate_pi(unsigned long long iterations, int precision, int nthreads);

void print_help(char* const name) {
    printf("%s\n", name);
    printf("  -n <iterations>         :  Number of iterations to run\n");
    printf("  -p <decimal precision>  :  Number of correct decimal places to calculate\n");
    printf("  -t <threads>  :  Number of threads to use\n");
    printf("  -h                      :  This help page\n");
}


int main( int argc, char* const argv[] ) {
    int c=0;
    unsigned long long nvalue = 0;
    char *term;
    int pvalue = 5;
    int nthreads = 1;
    
    while ((c = getopt (argc, argv, "t:n:p:h")) != -1){
        switch(c) {
            case 'n':
                nvalue = strtoull(optarg, &term, 10);
                break;
            case 'p':
                pvalue = atoi(optarg);
                break;
            case 't':
                nthreads = atoi(optarg);
                break;
            case 'h':
                print_help(argv[0]);
                return(0);
                break;
        }
    }
    if (nvalue < 0 || pvalue < 0 || nthreads < 1){
        print_help(argv[0]);
        return(0);
    }
    long double result = calculate_pi(nvalue, pvalue, nthreads);
    printf("%.20Lf\n", result);

}
