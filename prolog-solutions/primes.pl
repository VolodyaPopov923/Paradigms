prime(2).

fact(N, F) :- N mod F =:= 0.
fact(N, F) :- F * F < N, F2 is F + 1, fact(N, F2).

div(N, D, []) :-
    N < D, !.

div(N, D, [D|Ds]) :-
    0 is N mod D, !,
    N1 is N // D,
    div(N1, D, Ds).

div(N, D, Ds) :-
    D1 is D + 1,
    div(N, D1, Ds).

prime(N) :- N > 2, not(fact(N, 2)).
composite(N) :- N > 2, fact(N, 2).
prime_divisors(N, Divisors) :- div(N, 2, Divisors).


nth_prime(N, P) :-
  number(N),
  nth_prime_recursive(N, 1, P).

nth_prime_recursive(0, P, P).
nth_prime_recursive(N, P, Res) :-
  N > 0,
  next_prime(P, NextPr),
  N1 is N - 1,
  nth_prime_recursive(N1, NextPr, Res).

next_prime(P, R) :-
  P1 is P + 1,
  (prime(P1) ->
    R is P1;
    next_prime(P1, R)).