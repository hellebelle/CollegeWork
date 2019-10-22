%{
    char code   [50000];
%}

%%
\"[^\"]*\"      {strcat(code, yytext);}
\*\*.*          {}
\{[^\}]*\}      {}
\{[^\}]*        {strcat(code, "syntax error\n"); return 0;}
\[^\{]*\}       {strcat(code, "syntax error\n"); return 0;}
\}              {strcat(code, "syntax error\n"); return 0;}
\n              {strcat(code, yytext);}
.               {strcat(code, yytext);}
%%

int main ()
{
    yylex();
    printf("%s", code);
    return 0;
}