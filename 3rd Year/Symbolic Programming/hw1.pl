%Exercise1
numeral(0).
numeral(s(X)) :- numeral(X).
numeral(X+Y) :- numeral(X), numeral(Y).
numeral(p(X)) :- numeral(X).
numeral(-X) :- numeral(X).
numeral(X-Y) :- numeral(X), numeral(Y).

add(0,X,X).
add(s(X),Y,s(Z)) :- add(X,Y,Z).
add(p(X),Y, p(Z)) :- add(X,Y,Z).

add2(-X,Y,Z) :- minus(X,W), add2(W,Y,Z).
add2(X,-Y,Z) :- minus(Y,W), add2(X,W,Z).

add2(p(s(X)),Y, Z) :- add2(X,Y,Z).
add2(s(p(X)),Y,Z)  :-add2(X,Y,Z).
add2(s(X),p(Y),Z) :- add2(X,Y,Z).
add2(X,p(s(Y)),Z) :- add2(X,Y,Z).
add2(X,s(p(Y)),Z)  :-add2(X,Y,Z).
add2(p(X),s(Y),Z) :- add2(X,Y,Z).

add2(s(W+X),Y,s(Z)) :- add2(W+X,Y,Z).
add2(X,s(W+Y),s(Z)) :- add2(X,W+Y,Z).
add2(s(W+X),s(V+Y),s(Z)) :- add2(W+X,V+Y,Z). 

add2(W+X,Y,Z) :- add(W,X,R), add2(R,Y,Z).
add2(X,W+Y,Z) :- add(W,Y,R), add2(X,R,Z).
add2(W+X,V+Y,Z) :- add(W,X,R), add(V,Y,Q), add2(R,Q,Z).
add2(X,Y,Z) :- add(X,Y,Z).

minus(0,X) :- numeral(X).
minus(s(W)+p(X),Y) :- minus(s(W),A),minus(p(X),B),add2(A,B,Y).
minus(s(W)-p(X),Y) :-  minus(s(W),A),minus(-p(X),B),add2(A,B,Y).
minus(p(W)+s(X),Y) :- minus(p(W),A),minus(s(X),B),add2(A,B,Y).
minus(p(W)-s(X),Y) :- minus(p(W),A),minus(-s(X),B),add2(A,B,Y).
minus(-p(X),Y) :- minus(s(X),Y).
minus(-s(X),Y) :- minus(p(X),Y).

minus(s(p(X)),Y) :- minus(X,Y).
minus(p(s(X)),Y) :- minus(X,Y).
minus(p(X),s(Y)) :- minus(X,Y).
minus(s(X),p(Y)) :- minus(X,Y).

subtract(X,Y,Z) :- minus(X,V),minus(Y,W),add2(V,W,Z).
subtract(X,-Y,Z) :- minus(Y,W), subtract(X,W,Z).
subtract(-X,Y,Z) :- minus(X,W), subtract(W,Y,Z).

