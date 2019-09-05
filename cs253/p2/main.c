/** 
*   @file main.c
*   @brief Parses a csv file and prints to stdout.
*   @author Tyler Bevan
*   @date 2 September, 2016
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
*   Logical states for parsing a field in the input.
*/
enum State {
    START,          // Represents a non-quoted value.
    BREAK,          // We are between values.
    IN_VAL,         // We are inside a non-quoted value.
    QUOTED,         // Represents a quoted value.
    DOUBLE_QUOTED,  // Represents a double quoted section within a quoted value.
    END             // End of values, if there are more we have a problem.
};

/**
 *  Parses the given char* line and stores the result in workingString.
 */
void parseLine(size_t length, char *line, char* workingString){
    enum State state = START;       // Initalize the state.
    size_t i = 0;                   // Counter for the loop.
    while (i < length) {
        switch (state){             // This switch statement checks the current state.
            case START:             // If in START state.
                switch (line[i]) {
                    case ',':
                        state = BREAK;                      // Go to BREAK state.
                        strcat(workingString, "XXXXXX,");   // Values is empty, insert placeholder.
                        break;
                    case '"':
                        state = QUOTED;                                     // Go to QUOTED state.
                        strcat(workingString, (char[2]) {line[i], '\0'});   // Append the character.
                        break;
                    case '\n':
                        state = END;        // This should not happen, but prevents errors.
                        break;
                    default:
                        strcat(workingString, (char[2]) {line[i], '\0'});   // Append character.
                        state = IN_VAL;                                     // We are now in a value.
                }
                break;
            case BREAK:
                switch (line[i]) {
                    case ',':
                        if (line[i-1]  == ','){             // If previous char was also a ','
                        state = BREAK;                      // Still BREAK
                        strcat(workingString, "XXXXXX,");   // Insert Placeholder for value.
                        }
                        break;
                    case '"':
                        state = QUOTED;                                     // Begin QUOTED value.
                        strcat(workingString, (char[2]) {line[i], '\0'});   // Append quote char.
                        break;
                    case '\n':
                        state = END;                        // End of line.
                        strcat(workingString, "XXXXXX");    // Final val is empty, appent placeholder.
                        break;
                    default:
                        strcat(workingString, (char[2]) {line[i], '\0'});   // Append first char of value.
                        state = IN_VAL;                                     // We are now in a value.
                }
                break;
            case IN_VAL:
                switch (line[i]) {
                    case ',':
                        state = BREAK;                                      // End of value.
                        strcat(workingString, (char[2]) {line[i], '\0'});   // Append ','
                    case '"':
                        break;  // Quotes are not allowed at this point.
                    case '\n':
                        state = END;    // Final values ends here.
                        break;
                    default:
                        state = IN_VAL;                                     // Continue reading value.
                        strcat(workingString, (char[2]) {line[i], '\0'});   // Append char to output.
                }
                break;
            case QUOTED:
                switch (line[i]) {
                    case '"':
                        if (line[i+1] == '"') {                                         // If two quotes.
                            state = DOUBLE_QUOTED;                                      // Enter double quoted value.
                            strcat(workingString, (char[3]) {line[i], line[i+1], '\0'});// Append both quotes.
                            i++;
                        } else {
                            state = BREAK;                                          // End of quoted value.
                            strcat(workingString, (char[3]) {line[i], ',', '\0'});  // Write closing quote and a comma.
                        }
                        break;
                    case '\n':
                        break;  // Newlines are ignored inside of quoted values.
                    default:
                        state = QUOTED;                                     // Stay QUOTED
                        strcat(workingString, (char[2]) {line[i], '\0'});   // Append current char to output.
                }
                break;
            case DOUBLE_QUOTED:
                switch (line[i]) {
                    case '"':
                        if (line[i+1] == '"'){                                          // If double quotes
                            strcat(workingString, (char[3]) {line[i], line[i+1], '\0'});// Write double quotes
                            i++;
                            state = QUOTED;                                             // Return to quoted state.
                        }
                        break;
                    case '\n':
                        break;  // Ignore newline.
                    default:
                        state = DOUBLE_QUOTED;                              // Remain double quoted.
                        strcat(workingString, (char[2]) {line[i], '\0'});   // Write current char to output.
                }
                break;
            case END:
                break;  // Prevent processing beyond end state.
        }
        i++; // Next Char
    }
}

/**
*   @brief Main method reads one line at a time and passes it to the parseLine function.
*   @return int - Should always be 0.
*/
int main(void)
{
    char *line = NULL;                          // Initalize line pointer for getline method.
    size_t size;                                // Declare size_t for getline method.
    
    while (getline(&line, &size, stdin) != -1){ // While lines remain in input.

        char workingString[255];                // Create temporary string for output.
        workingString[0] = '\0';                // Set first char to null terminator.

        parseLine(size, line, workingString);   // Call parseLine on line from getline().

        printf("%s\n", workingString);          // Print the resulting line to stdout.
        memset(workingString, '\0', 255);       // Clear all data from workingString.
    }
    free(line);                                 // Free the line pointer before exiting.
    return 0;                                   // Return Success.
}
