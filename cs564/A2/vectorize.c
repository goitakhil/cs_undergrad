#include <stdio.h>

#define xlen 750
#define ylen 375
#define zlen 100
#define fsize 4

int main(int argc, char** argv) {
  if (argc < 5) return 1;
  FILE* filex = fopen(argv[1], "rb");
  FILE* filey = fopen(argv[2], "rb");
  FILE* filez = fopen(argv[3], "rb");
  char X[fsize];
  char Y[fsize];
  char Z[fsize];
  FILE* outfile = fopen(argv[4], "wb");
  for (int i = 0; i < (xlen*ylen*zlen); i++) {
    fread(&X, fsize, 1, filex);
    fread(&Y, fsize, 1, filey);
    fread(&Z, fsize, 1, filez);
    fwrite(&X, fsize, 1, outfile);
    fwrite(&Y, fsize, 1, outfile);
    fwrite(&Z, fsize, 1, outfile);
  }
  fclose(filex);
  fclose(filey);
  fclose(filez);
  fclose(outfile);
}
