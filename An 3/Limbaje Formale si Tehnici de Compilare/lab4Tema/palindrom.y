%{
#include <stdio.h>
#include <string.h>
#include <ctype.h>

void yyerror(const char *s);
int yylex();

int is_palindrome(const char *str);

extern char matched_string[];  // Declare the external buffer
%}

%token PALINDROME

%%

input:
    PALINDROME {
        if (is_palindrome(matched_string)) {
            printf("The word '%s' is a palindrome.\n", matched_string);
        } else {
            printf("The word '%s' is not a palindrome.\n", matched_string);
        }
    }
    ;

%%

void yyerror(const char *s) {
    fprintf(stderr, "Error: %s\n", s);
}

int main() {
    printf("Enter a word to check if it is a palindrome (format: pali#ipal):\n");
    fflush(stdout);  // Ensure the prompt is displayed
    yyparse();
    return 0;
}

int is_palindrome(const char *str) {
    int mid = strchr(str, '#') - str;
    int len = strlen(str);
    int second_half_start = mid + 1;
    int second_half_len = len - second_half_start;

    // Create buffers to store the cleaned parts
    char first_half[100], second_half[100];
    int j = 0, k = 0;

    // Copy non-space characters to the first half
    for (int i = 0; i < mid; i++) {
        if (!isspace(str[i])) {
            first_half[j++] = str[i];
        }
    }
    first_half[j] = '\0';

    // Copy non-space characters to the second half
    for (int i = second_half_start; i < len; i++) {
        if (!isspace(str[i])) {
            second_half[k++] = str[i];
        }
    }
    second_half[k] = '\0';

    // Check if the cleaned parts are of equal length
    if (j != k) {
        return 0;
    }

    // Compare the first half with the reversed second half
    for (int i = 0; i < j; i++) {
        if (first_half[i] != second_half[j - 1 - i]) {
            return 0;
        }
    }
    return 1;
}