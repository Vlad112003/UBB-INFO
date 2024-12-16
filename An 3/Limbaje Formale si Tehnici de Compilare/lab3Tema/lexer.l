%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define HASH_SIZE 100

typedef struct Symbol {
    char *name;
    int type; // 0 for identifier, 1 for constant
    int index;
    struct Symbol *next;
} Symbol;

Symbol *symbol_table[HASH_SIZE];
int line_num = 1;
int identifier_index = 1;
int constant_index = 1;
FILE *fip_file;
FILE *ts_file;

int yywrap(void) { return 1; }

unsigned int hash(const char *str) {
    unsigned int hash = 0;
    while (*str) {
        hash = (hash << 5) + *str++;
    }
    return hash % HASH_SIZE;
}

Symbol *insert_symbol(const char *name, int type) {
    unsigned int index = hash(name);
    Symbol *symbol = symbol_table[index];
    while (symbol) {
        if (strcmp(symbol->name, name) == 0) {
            return symbol;
        }
        symbol = symbol->next;
    }
    symbol = (Symbol *)malloc(sizeof(Symbol));
    symbol->name = strdup(name);
    symbol->type = type;
    if (type == 0) {
        symbol->index = identifier_index++;
    } else {
        symbol->index = constant_index++;
    }
    symbol->next = symbol_table[index];
    symbol_table[index] = symbol;
    fprintf(ts_file, "%s %d %d\n", name, type, symbol->index);
    return symbol;
}

void add_to_fip(const char *token, int code, int ts_index) {
    fprintf(fip_file, "%s %d %d\n", token, code, ts_index);
    //printf("%s %d %d\n", token, code, ts_index);
}

void yyerror(const char *s) {
    fprintf(stderr, "Error: %s at line %d\n", s, line_num);
}
%}

%%

[ \t]+           ; // Ignore spaces and tabs
\n               { line_num++; }
"int"            { add_to_fip("int", 32, -1); return 32; }
"main"           { add_to_fip("main", 2, -1); return 2; }
"float"          { add_to_fip("float", 11, -1); return 11; }
"char"           { add_to_fip("char", 12, -1); return 12; }
"string"         { add_to_fip("string", 13, -1); return 13; }
"cin"            { add_to_fip("cin", 7, -1); return 7; }
"cout"           { add_to_fip("cout", 8, -1); return 8; }
"if"             { add_to_fip("if", 9, -1); return 9; }
"while"          { add_to_fip("while", 10, -1); return 10; }
"do"             { add_to_fip("do", 33, -1); return 33; }
"cat timp"       { add_to_fip("cat timp", 34, -1); return 34; }
"executa"        { add_to_fip("executa", 35, -1); return 35; }
"sf cat timp"    { add_to_fip("sf cat timp", 36, -1); return 36; }
"("              { add_to_fip("(", 3, -1); return 3; }
")"              { add_to_fip(")", 4, -1); return 4; }
"{"              { add_to_fip("{", 5, -1); return 5; }
"}"              { add_to_fip("}", 6, -1); return 6; }
">>"             { add_to_fip(">>", 27, -1); return 27; }
"<<"             { add_to_fip("<<", 26, -1); return 26; }
"=="             { add_to_fip("==", 19, -1); return 19; }
"!="             { add_to_fip("!=", 20, -1); return 20; }
">="             { add_to_fip(">=", 25, -1); return 25; }
"<="             { add_to_fip("<=", 24, -1); return 24; }
">"              { add_to_fip(">", 29, -1); return 29; }
"<"              { add_to_fip("<", 28, -1); return 28; }
"="              { add_to_fip("=", 23, -1); return 23; }
"++"             { add_to_fip("++", 21, -1); return 21; }
"--"             { add_to_fip("--", 22, -1); return 22; }
"+"              { add_to_fip("+", 14, -1); return 14; }
"-"              { add_to_fip("-", 15, -1); return 15; }
"*"              { add_to_fip("*", 17, -1); return 17; }
"/"              { add_to_fip("/", 16, -1); return 16; }
"%"              { add_to_fip("%", 18, -1); return 18; }
";"              { add_to_fip(";", 30, -1); return 30; }
","              { add_to_fip(",", 31, -1); return 31; }
\"[^\"]*\"       { add_to_fip("STRING_LITERAL", 1, insert_symbol(yytext, 1)->index); return 13; }
[0-9]+(\.[0-9]+)? { add_to_fip("CONSTANT", 1, insert_symbol(yytext, 1)->index); return 1; }
-[0-9]+(\.[0-9]+)? { add_to_fip("CONSTANT", 1, insert_symbol(yytext, 1)->index); return 1; }
[a-zA-Z_][a-zA-Z0-9_]* { add_to_fip("IDENTIFIER", 0, insert_symbol(yytext, 0)->index); return 100; }
.                { yyerror("Unknown character"); }

%%

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
        int token;
        while ((token = yylex()) != 0) {
            // yylex() will print token details
        }

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