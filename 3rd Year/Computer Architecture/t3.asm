add r0, #4, r9               ; g = 4 

min:                         ;int min (int a, int b, int c){
        add r0, r26, r8     ;       int v = a;
        sub r27, r8, r0, {C};       if(b<v)    
        jge continue         ;          {
        xor r0, r0 , r0
        add r0, r27, r8     ;            v = b;    
continue:                    ;          }
        sub r28, r8, r0, {C};       if(c<v) 
        jge continue2        ;          {
        xor r0, r0 , r0
        add r0, r28, r8     ;            v = c;
continue2:                   ;          }                  
        ret r25              ; return address saved in r25
        xor r0, r0 , r0      ;}

p:                           ; int p (int i, int j, int k, int l){
        add r0, r9, r10      ;        r10 = r9 = g; 
        add r0, r26, r11     ;        r11 = r26 = i;
        add r0, r27, r12     ;        r12 = r27 = j;
        callr r25, min       ;        r25 = min(g, i, j);
        
        xor r0, r0 , r0      ;
        add r0, r8, r10     ;        r10 = r25 = min(g, i, j); 
        add r0, r28, r11     ;        r11 = r28 = k;
        add r0, r29, r12     ;        r12 = r29 = l;
        callr r25, min       ;        min(min(g, i, j), k, l); 
        
        xor r0, r0 , r0      ;
        ret r25              ;        return min(min(g, i, j), k, l);
        xor r0, r0 , r0      ;}

gcd:    
        sub r27, r0, r0, {C} ;
        
        je gcdRet            ;
