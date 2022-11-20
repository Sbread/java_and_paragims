% treap((X, Y, Value), LeftTree, RightTree) V - Value
% T - Treap

correct_size(nil) :- !.
correct_size(treap((X, Y, V, 1), nil, nil)) :- !.
correct_size(treap((X, Y, V, S), nil, treap((X2, Y2, V2, S2), LT2, RT2))) :- !,
    S is (S2 + 1).
correct_size(treap((X, Y, V, S), treap((X1, Y1, V1, S1), LT1, RT1), nil)) :- !,
    S is (S1 + 1).
correct_size(treap((X, Y, V, S), treap((X1, Y1, V1, S1), LT1, RT1), treap((X2, Y2, V2, S2), LT2, RT2))) :-!,
    S is (S1 + S2 + 1).

merge(nil, T, T) :- !.
merge(T, nil, T) :- !.
merge(treap((X1, Y1, V1, S1), LT1, RT1), treap((X2, Y2, V2, S2), LT2, RT2), treap((X1, Y1, V1, S3), LT1, NRT)) :-
	Y1 > Y2, !,
	correct_size(treap((X1, Y1, V1, S1), LT1, RT1)),
	correct_size(treap((X2, Y2, V2, S2), LT2, RT2)),
	merge(RT1, treap((X2, Y2, V2, S2), LT2, RT2), NRT).
	correct_size(NRT),
	correct_size(treap((X1, Y1, V1, S3), LT1, NRT)).

merge(treap((X1, Y1, V1, S1), LT1, RT1), treap((X2, Y2, V2, S2), LT2, RT2), treap((X2, Y2, V2, S3), NLT, RT2)) :-
	Y1 =< Y2, !,
	correct_size(treap((X1, Y1, V1, S1), LT1, RT1)),
  correct_size(treap((X2, Y2, V2, S2), LT2, RT2)),
	merge(treap((X1, Y1, V1, S1), LT1, RT1), LT2, NLT),
	correct_size(NLT),
	correct_size(treap((X2, Y2, V2, S3), NLT, RT2)).

split(nil, _, nil, nil).

split(treap((X, Y, Val, S1), LN, RN), Key, LT, treap((X, Y, Val, S2), RT, RN)) :-
	Key < X,
	split(LN, Key, LT, RT),
	correct_size(LT),
	correct_size(treap((X, Y, Val, S2), RT, RN)).

split(treap((X, Y, Val, S1), LN, RN), Key, treap((X, Y, Val, S2), LN, RLT), RT) :-
	correct_size(treap((X, Y, Val, S1), LN, RN)),
	Key > X,
	split(RN, Key, RLT, RT),
	correct_size(RT),
	correct_size(treap((X, Y, Val, S2), LN, RLT)).

split(treap((X, Y, Val, S1), LN, RN), X, LN, treap((X, Y, Val, S2), nil, RN)) :-
	correct_size(treap((X, Y, Val, S1), LN, RN)),
	correct_size(LN),
	correct_size(treap((X, Y, Val, S2), nil, RN)).

map_remove(T, Key, NT) :-
	split(T, Key, T1, T2),
	Key1 is (Key + 1),
	split(T2, Key1, T3, T4),
	merge(T1, T4, NT).

map_put(T, Key, V, NT) :-
  map_remove(T, Key, NT1),
	split(NT1, Key, T1, T2),
	rand_int(100000, Y),
	merge(T1, treap((Key, Y, V, 1), nil, nil), NT2),
	merge(NT2, T2, NT).

map_build([], nil) :- !.
map_build([(Key, V) | Tail], CT) :-
	map_build(Tail, T1),
	map_put(T1, Key, V, CT).

map_get(treap((X, _, _, _), _, RN), Key, V) :-
	Key > X, !,
	map_get(RN, Key, V).

map_get(treap((X, _, _, _), LN, _), Key, V) :-
	Key < X, !,
	map_get(LN, Key, V).

map_get(treap((X, Y, V, S), LN, RN), X, V) :- !.

map_subMapSize(T, From, To, 0) :-
    split(T, From, T1, T2),
    split(T2, To, nil, T4), !.

map_subMapSize(T, From, To, S) :-
    split(T, From, T1, T2),
    split(T2, To, treap((X, Y, V, S), LT, RT), T4), !.
