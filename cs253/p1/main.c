/**
 * @file main.c
 * @brief Project 1 - Text Statistics.
 *
 * Analyzes text from standard input and reports
 * statistics to standard out.
 * @author Tyler Bevan
 */

/* We need stdio to read in and print out */
#include <stdio.h>

/** @brief Boolean value enumerator.
 */
enum bool {
    TRUE, /**< Represents a boolean TRUE state. */
    FALSE /**< Represents a boolean FALSE state. */
};

/** @brief Structure to hold result data */
struct Results {
    long words;         /**< Number of words in the input */
    long characters;    /**< Number of characters in the input*/
    long lines;         /**< Number of lines in the input */
    long digits[10];    /**< Array of digit counts from 0 to 9 */
};

struct Results results; /**< Global instance of Results used by all functions. */

/* Define functions for main to find */
void initStruct(void);
void analyzeFile(void);
void printResults(void);

/**
 * @brief Main method, calls all other functions in order.
 *
 * @return int - 1 always.
 */
int main(void) {
    initStruct();
    analyzeFile();
    printResults();
    return 1;
};

/**
 * @brief Zeros out all values in the struct.
 */
void initStruct(void){
    results.words = 0;
    results.characters = 0;
    results.lines = 0;
    for (int i = 0; i < 10; i++) {
        results.digits[i] = 0;
    }
};


/**
 * @brief Reads from stdin and counts characters, words, lines, and digits.
 */
void analyzeFile(void){
    enum bool inWord = FALSE;
    int nextChar = getchar();

    while (nextChar !=EOF) {
        results.characters++;
        if (nextChar >= 48 && nextChar <= 57) {
            inWord = TRUE;
            results.digits[nextChar - 48]++;
        } else if (nextChar == 32 ) {
            if (inWord == TRUE) {
                results.words++;
                inWord = FALSE;
            }
        } else if (nextChar == 10 ) {
            results.lines++;
            if (inWord == TRUE) {
                results.words++;
                inWord = FALSE;
            }
        } else {
            inWord = TRUE;
        }
        nextChar = getchar();
    }
};

/**
 * @brief Prints the results to stdout.
 */
void printResults(void){
    printf("words: %li\n", results.words);
    printf("chars: %li\n", results.characters);
    printf("lines: %li\n", results.lines);
    for (int i = 0; i < 10; i++){
        printf("digit %i: %li\n", i, results.digits[i]);
    }
}
