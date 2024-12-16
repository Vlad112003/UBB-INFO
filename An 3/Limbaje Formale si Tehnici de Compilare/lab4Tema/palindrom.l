%{
#include "palindrom.tab.h"
#include <string.h>

char matched_string[100];  // Define the external buffer

void yyerror(const char *s);  // Declare yyerror function
%}

%%

[a-zA-Z ]+#[a-zA-Z ]+ {
    strcpy(matched_string, yytext);
    return PALINDROME;
}

.|\n {
    yyerror("Invalid input");
}

%%

int yywrap() {
    return 1;
}