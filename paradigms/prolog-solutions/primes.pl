composite(0).
composite(1). % :NOTE: -> composite

mark_false(J, MAX_N, I) :- J =< MAX_N, assert(composite(J)), assert(least_divisor_table(J, I)), J2 is J + I, mark_false(J2, MAX_N, I).

%assert(least_divisor_table(J, I)),
%assert(least_divisor_table(N, N)),

init1(N, MAX_N) :-
  N =< MAX_N, (\+ composite(N), assert(least_divisor_table(N, N)), N2 is N * N, mark_false(N2, MAX_N, N); true), N1 is N + 1, init1(N1, MAX_N).

prime(N) :- \+ composite(N).

init(MAX_N) :- \+ init1(2, MAX_N).

find_divisors(1, []) :- !.
find_divisors(N, [D | R]) :-
  least_divisor_table(N, D),
  N1 is N / D,
  find_divisors(N1, R).

sorted_non_ascending([]).
sorted_non_ascending([H]).
sorted_non_ascending([H1, H2 | T]) :- H1 =< H2, sorted_non_ascending([H2 | T]).


prime_divisors(N, Divisors) :- integer(N), !,
  find_divisors(N, Divisors), !.

multiply([], 1).
multiply([H | T], R) :- multiply(T, R1), R is R1 * H.

check_prime([]).
check_prime([H | T]) :- prime(H), check_prime(T).

prime_divisors(N, Divs) :-
  multiply(Divs, R),
  R = N,
  sorted_non_ascending(Divs),
  check_prime(Divs).

lcm3(D, [], R) :- !, multiply(D, R).
lcm3([], D, R) :- !, multiply(D, R).
lcm3([H1 | T1], [H2 | T2], R) :-
  (H1 = H2, !, lcm3(T1, T2, R1), R is R1 * H1);
  (H1 < H2, !, lcm3(T1, [H2 | T2], R1), R is R1 * H1);
  (H1 > H2, !, lcm3([H1 | T1], T2, R1), R is R1 * H2).

lcm(A, A, A) :- !.
lcm(A, B, R) :-
  prime_divisors(A, DivisorsA),
  prime_divisors(B, DivisorsB),
  lcm3(DivisorsA, DivisorsB, R).
