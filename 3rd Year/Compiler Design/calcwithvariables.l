%option noyywrap

%{
# include "calcwithvariables.tab.h"
void yyerror(char *s);
%}

%%
[a-z]{1} {yylval = yytext[0] - '0'; return VAR;}
[0-9]+	{ yylval = atoi(yytext); return NUM; }
"+"	{ return ADD; }
"-"	{ return SUB; }
"*"	{ return MUL; }
"/"	{ return DIV; }
":="    {return ASSIGNMENT;}
"print" {return PRINT;}
";"     {return SEMIC;}

[ \t\n]   { /* ignore white space */ }
.	{ yyerror("syntax error");}
%%
