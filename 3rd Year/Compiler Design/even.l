%{
    int even_int = 0 ;
%}

%%
[0-9]*[02468] { even_int++;}
[0-9]*[13579] {}
(.|\n)        {}
%%

int main()
{
    yylex();
    printf("%d\n", even_int);
    return 0;
}