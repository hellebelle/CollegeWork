%{
	
%}	
	
THREE_DIGIT_YEAR [1-9]{2}[12]
TWO_DIGIT_YEAR	 [0-9]{2}	
NEW_COUNTIES	 C|CE|CN|CW|D|DL|G|KE|KK|KY|L|LD|LH|LM|LS|MH|MN|MO|OY|RN|SO|T|W|WH|WX|WW	
OLD_COUNTIES	C|CE|CN|CW|D|G|DL|KE|KK|KY|L|LK|LD|LH|LM|LS|MH|MN|MO|OY|RN|SO|TN|TS|W|WD|WH|WX|WW                          
    	
NUMBER	         [1-9][0-9]{0,5}	
%%
[\n|\s|\t| ]*{THREE_DIGIT_YEAR}\-{NEW_COUNTIES}\-{NUMBER}[\n|\s|\t| ]* { 
                int year = yytext[1] - '0';
        	    int years = 9 - year;
  		        printf("%d\n",years);
}
	
[\n|\s|\t| ]*{TWO_DIGIT_YEAR}\-{OLD_COUNTIES}\-{NUMBER}[\n|\s|\t| ]* {
	            char temp[2];
=	            strncpy(temp, yytext, 2);
		        int year = atoi(temp);
		        int years = 0;
		        if (year > 86){
			        years = (100 - year) + 19;
		        }
 		        else{
 			        years = 19 - year;
 		        }
	            printf("%d\n",years);
}
	                
[\n|\s|\t| ]*.*[\n|\s|\t| ]* {printf("INVALID\n");}
	
%%
	
main(int argc, char **argv)
{
		yylex();
	return 0;
}