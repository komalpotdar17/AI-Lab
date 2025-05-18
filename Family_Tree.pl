% Facts
% Parents (parent(child, parent))
parent(umesh, arun).
parent(mahesh, arun).
parent(ashwini, umesh).
parent(swara, ashwini).
parent(aiya, mahesh).
parent(neel, pratibha).
parent(ainav, prakash).

parent(umesh, archana).
parent(mahesh, archana).

% Grandparents
parent(arun, ganpat).
parent(archana, ganpat).
parent(ganpat, bhalchandra).
parent(smita, bhalchandra).

% Spouses
spouse(umesh, ashwini).
spouse(mahesh, pratibha).
spouse(prashant, pallavi).
spouse(prakash, smita).
spouse(arun, archana).
spouse(ganpat, smita).

% Bidirectional spouse relation
spouse(X, Y) :- spouse(Y, X).

% Define rules
father(X, Y) :- parent(Y, X), male(X).
mother(X, Y) :- parent(Y, X), female(X).
grandfather(X, Y) :- parent(X, Z), parent(Z, Y), male(X).
grandmother(X, Y) :- parent(X, Z), parent(Z, Y), female(X).
sibling(X, Y) :- parent(Z, X), parent(Z, Y), X \= Y.
brother(X, Y) :- sibling(X, Y), male(X).
sister(X, Y) :- sibling(X, Y), female(X).

uncle(X, Y) :- brother(X, Z), parent(Z, Y).
uncle(X, Y) :- spouse(X, W), sister(W, Z), parent(Z, Y). % Uncle by marriage

aunt(X, Y) :- sister(X, Z), parent(Z, Y).
aunt(X, Y) :- spouse(X, W), brother(W, Z), parent(Z, Y). % Aunt by marriage

% Gender facts
male(arun).
male(ganpat).
male(umesh).
male(mahesh).
male(bhalchandra).
male(prashant).
male(prakash).
male(neel).
male(ainav).

female(archana).
female(smita).
female(pratibha).
female(ashwini).
female(swara).
female(pallavi).
female(aiya).
