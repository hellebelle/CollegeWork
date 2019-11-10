%{
    #include <stdio.h>
    int yylex();
    void yyerror(char *s)
%}

/*declare tokens*/
%token ONE, FOUR, FIVE, NINE, TEN, FORTY, FIFTY, NINETY, HUNDRED, FOUR_HUNDRED, FIVE_HUNDRED, NINE_HUNDRED, THOUSAND
%token EOL

%%
expr:
    | expr val EOL { printf ("%d\n", $2);}
;

val: term
    | val term {
        if ($1 + $2 > 1000 & S1 < 1000){
            yyerror("syntax error\n");
        }
        if ($1 != $2 || ($1 >= 1000 && $2 >= 1000))
        {
            $$ = $1 + $2;
        }
        else{
             yyerror("syntax error\n");
        }
    }
;
term: ONE {$$ = $1;} 
    | FOUR {$$ = $1;}
    | FIVE {$$ = $1;}
    | NINE {$$ = $1;}
    | TEN {$$ = $1;}
    | FORTY {$$ = $1;}
    | FIFTY {$$ = $1;}
    | NINETY {$$ = $1;}
    | HUNDRED {$$ = $1;}
    | FOUR_HUNDRED {$$ = $1;}
    | FIVE_HUNDRED {$$ = $1;}
    | NINE_HUNDRED {$$ = $1;}
    | THOUSAND {$$ = $1;}
;

%%
void yyerror(char *s)
{
    printf("%s", s);
    exit(0);
}


int main(){
    yyparse();
    return 0;
}
