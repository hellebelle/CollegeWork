add r0, #4, r9               ; g = 4 

;int min (int a, int b, int c)
;parameters: a, b, c,
;           a = r26
;           b = r27
;           c = r28
min:                         ;int min (int a, int b, int c){
        add r0, r26, r8      ;       int v = a;
        sub r27, r8, r0, {C} ;       if(b<v)    
        jge continue         ;          {
        xor r0, r0 , r0
        add r0, r27, r8      ;            v = b;    
continue:                    ;          }
        sub r28, r8, r0, {C} ;       if(c<v) 
        jge continue2        ;          {
        xor r0, r0 , r0
        add r0, r28, r8      ;            v = c;
continue2:                   ;          }                  
        ret r25, 0           ; return address saved in r25
        xor r0, r0 , r0      ;}

; int p (int i, int j, int k, int l)
; parameters: i, j, k, l
;           i = r26
;   	    j = r27
;           k = r28
;           l = r29
;           min function result returned in r8


p:                           ; int p (int i, int j, int k, int l){
        add r0, r9, r10      ;        r10 = r9 = g; 
        add r0, r26, r11     ;        r11 = r26 = i;
        add r0, r27, r12     ;        r12 = r27 = j;
        callr r25, min       ;        r25 = min(g, i, j);
        
        xor r0, r0 , r0      ;
        add r0, r8, r10      ;        r10 = r8 = min(g, i, j); 
        add r0, r28, r11     ;        r11 = r28 = k;
        add r0, r29, r12     ;        r12 = r29 = l;
        callr r25, min       ;        min(min(g, i, j), k, l); 
        
        xor r0, r0 , r0      ;
        ret r25, 0           ;        return min(min(g, i, j), k, l);
        xor r0, r0 , r0      ;}

gcd:                         ;int gcd(int a, int b){
        add r0, r26, r7      ;      r7 = r26 = a
        sub r27, r0, r0, {C} ;      if (b == 0){  
        je gcdRet            ;          return a }
        add r0, r26, r10     ;      r10 = r26 = a
        add r0, r27, r11     ;      r11 = r27 = b
        callr r25, mod       ;external function mod(x,y) = (a % b)

        add r0, r27, r10     ;      r10 = r27 = b
        add r0, r6, r11      ;      r11 = r6(assumed return addr of mod(x,y))
        callr r25, gcd       ;      gcd(b, a % b)

gcdRet:
        ret r25, 0 
        xor r0, r0, r0