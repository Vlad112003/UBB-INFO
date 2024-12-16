%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void yyerror(const char *s);
int yylex(void);

extern FILE *fip_file;
extern FILE *ts_file;
void yy_scan_string(const char *str);

extern int line_num; // Declare the line_num variable

%}

%union {
    char *str;
}

%token <str> INT MAIN FLOAT CHAR STRING CIN COUT IF WHILE DO CAT_TIMP EXECUTA SF_CAT_TIMP
%token <str> LPAREN RPAREN LBRACE RBRACE INPUT OUTPUT EQ NEQ GE LE GT LT ASSIGN INC DEC
%token <str> PLUS MINUS MULT DIV MOD SEMICOLON COMMA STRING_LITERAL CONSTANT IDENTIFIER

%%

program:
    /* empty */
    | program statement
    ;

statement:
    INT { printf("Keyword: int\n"); }
    | MAIN { printf("Keyword: main\n"); }
    | FLOAT { printf("Keyword: float\n"); }
    | CHAR { printf("Keyword: char\n"); }
    | STRING { printf("Keyword: string\n"); }
    | CIN { printf("Keyword: cin\n"); }
    | COUT { printf("Keyword: cout\n"); }
    | IF { printf("Keyword: if\n"); }
    | WHILE { printf("Keyword: while\n"); }
    | DO { printf("Keyword: do\n"); }
    | CAT_TIMP { printf("Keyword: cat timp\n"); }
    | EXECUTA { printf("Keyword: executa\n"); }
    | SF_CAT_TIMP { printf("Keyword: sf cat timp\n"); }
    | LPAREN { printf("Symbol: (\n"); }
    | RPAREN { printf("Symbol: )\n"); }
    | LBRACE { printf("Symbol: {\n"); }
    | RBRACE { printf("Symbol: }\n"); }
    | INPUT { printf("Symbol: >>\n"); }
    | OUTPUT { printf("Symbol: <<\n"); }
    | EQ { printf("Symbol: ==\n"); }
    | NEQ { printf("Symbol: !=\n"); }
    | GE { printf("Symbol: >=\n"); }
    | LE { printf("Symbol: <=\n"); }
    | GT { printf("Symbol: >\n"); }
    | LT { printf("Symbol: <\n"); }
    | ASSIGN { printf("Symbol: =\n"); }
    | INC { printf("Symbol: ++\n"); }
    | DEC { printf("Symbol: --\n"); }
    | PLUS { printf("Symbol: +\n"); }
    | MINUS { printf("Symbol: -\n"); }
    | MULT { printf("Symbol: *\n"); }
    | DIV { printf("Symbol: /\n"); }
    | MOD { printf("Symbol: %\n"); }
    | SEMICOLON { printf("Symbol: ;\n"); }
    | COMMA { printf("Symbol: ,\n"); }
    | STRING_LITERAL { printf("String literal: %s\n", $1); free($1); }
    | CONSTANT { printf("Constant: %s\n", $1); free($1); }
    | IDENTIFIER { printf("Identifier: %s\n", $1); free($1); }
    ;

%%

void yyerror(const char *s) {
    extern int line_num; // Access the line_num variable
    fprintf(stderr, "Error: %s at line %d\n", s, line_num);
}

int main(int argc, char **argv) {
    if (argc > 1) {
        FILE *file = fopen(argv[1], "r");
        if (!file) {
            perror(argv[1]);
            return 1;
        }
        fip_file = fopen("FIP.txt", "w");
        ts_file = fopen("TS.txt", "w");
        if (!fip_file || !ts_file) {
            perror("Unable to open output files");
            return 1;
        }

        fseek(file, 0, SEEK_END);
        long file_size = ftell(file);
        fseek(file, 0, SEEK_SET);

        char *file_content = (char *)malloc(file_size + 1);
        if (!file_content) {
            perror("Unable to allocate memory for file content");
            fclose(file);
            return 1;
        }

        fread(file_content, 1, file_size, file);
        file_content[file_size] = '\0';
        fclose(file);

        printf("File content:\n%s\n", file_content);

        // Set the input for the lexer
        yy_scan_string(file_content);

        // Process the input
        yyparse();

        // Free the allocated memory
        free(file_content);
        fclose(fip_file);
        fclose(ts_file);
    } else {
        printf("No input file provided.\n");
        return 1;
    }
    return 0;
}