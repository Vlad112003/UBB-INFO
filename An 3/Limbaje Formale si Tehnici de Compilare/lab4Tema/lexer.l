%{
#include "parser.tab.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#define HASH_SIZE 100

typedef struct Symbol {
    char *name;
    int type; // 0 for identifier, 1 for constant
    int index;
    struct Symbol *next;
} Symbol;

Symbol *symbol_table[HASH_SIZE];
int line_num = 1; // Define the line_num variable
int identifier_index = 1;
int constant_index = 1;
FILE *fip_file;
FILE *ts_file;

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
}

extern void yyerror(const char *s);
%}

%option noyywrap

%%

[ \t\r\n]+       { if (*yytext == '\n') line_num++; } // Update line_num on newlines
"int"            { add_to_fip("int", 32, -1); return INT; }
"main"           { add_to_fip("main", 2, -1); return MAIN; }
"float"          { add_to_fip("float", 11, -1); return FLOAT; }
"char"           { add_to_fip("char", 12, -1); return CHAR; }
"string"         { add_to_fip("string", 13, -1); return STRING; }
"cin"            { add_to_fip("cin", 7, -1); return CIN; }
"cout"           { add_to_fip("cout", 8, -1); return COUT; }
"if"             { add_to_fip("if", 9, -1); return IF; }
"while"          { add_to_fip("while", 10, -1); return WHILE; }
"do"             { add_to_fip("do", 33, -1); return DO; }
"cat timp"       { add_to_fip("cat timp", 34, -1); return CAT_TIMP; }
"executa"        { add_to_fip("executa", 35, -1); return EXECUTA; }
"sf cat timp"    { add_to_fip("sf cat timp", 36, -1); return SF_CAT_TIMP; }
"("              { add_to_fip("(", 3, -1); return LPAREN; }
")"              { add_to_fip(")", 4, -1); return RPAREN; }
"{"              { add_to_fip("{", 5, -1); return LBRACE; }
"}"              { add_to_fip("}", 6, -1); return RBRACE; }
">>"             { add_to_fip(">>", 27, -1); return INPUT; }
"<<"             { add_to_fip("<<", 26, -1); return OUTPUT; }
"=="             { add_to_fip("==", 19, -1); return EQ; }
"!="             { add_to_fip("!=", 20, -1); return NEQ; }
">="             { add_to_fip(">=", 25, -1); return GE; }
"<="             { add_to_fip("<=", 24, -1); return LE; }
">"              { add_to_fip(">", 29, -1); return GT; }
"<"              { add_to_fip("<", 28, -1); return LT; }
"="              { add_to_fip("=", 23, -1); return ASSIGN; }
"++"             { add_to_fip("++", 21, -1); return INC; }
"--"             { add_to_fip("--", 22, -1); return DEC; }
"+"              { add_to_fip("+", 14, -1); return PLUS; }
"-"              { add_to_fip("-", 15, -1); return MINUS; }
"*"              { add_to_fip("*", 17, -1); return MULT; }
"/"              { add_to_fip("/", 16, -1); return DIV; }
"%"              { add_to_fip("%", 18, -1); return MOD; }
";"              { add_to_fip(";", 30, -1); return SEMICOLON; }
","              { add_to_fip(",", 31, -1); return COMMA; }
\"[^\"]*\"       { yylval.str = strdup(yytext); add_to_fip("STRING_LITERAL", 1, insert_symbol(yytext, 1)->index); return STRING_LITERAL; }
[0-9]+(\.[0-9]+)? { yylval.str = strdup(yytext); add_to_fip("CONSTANT", 1, insert_symbol(yytext, 1)->index); return CONSTANT; }
-[0-9]+(\.[0-9]+)? { yylval.str = strdup(yytext); add_to_fip("CONSTANT", 1, insert_symbol(yytext, 1)->index); return CONSTANT; }
[a-zA-Z_][a-zA-Z0-9_]* { yylval.str = strdup(yytext); add_to_fip("IDENTIFIER", 0, insert_symbol(yytext, 0)->index); return IDENTIFIER; }
.                { yyerror("Unknown character"); }

%%