%{
    #include <stdio.h>
    int yylex();
    void yyerror(char *s)
%}

/*declare tokens*/
%token NUMBER


void yyerror(char *s)
{
    fprintf(stderr, "syntax error\n");
}
