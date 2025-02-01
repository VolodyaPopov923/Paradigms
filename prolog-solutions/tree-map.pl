split_tree(nil, _, nil, nil) :- !.
split_tree([(Key1, Value1), LeftTree1, Priority1, RightTree1], Key2, SplitLeft, SplitRight) :-
    Key2 > Key1,
    split_tree(RightTree1, Key2, SplitTree1, SplitRight),
    SplitLeft = [(Key1, Value1), LeftTree1, Priority1, SplitTree1].
split_tree([(Key1, Value1), LeftTree1, Priority1, RightTree1], Key2, SplitLeft, SplitRight) :-
    Key2 =< Key1,
    split_tree(LeftTree1, Key2, SplitLeft, SplitTree2),
    SplitRight = [(Key1, Value1), SplitTree2, Priority1, RightTree1].

compare_priority(Priority1, Priority2, Tree1, Tree2, MergedTree) :-
    Priority1 > Priority2,
    Tree1 = [(Key1, Value1), LeftTree1, _, RightTree1],
    merge_tree(RightTree1, Tree2, MergedTree1),
    MergedTree = [(Key1, Value1), LeftTree1, Priority1, MergedTree1].

compare_priority(Priority1, Priority2, Tree1, Tree2, MergedTree) :-
    Priority1 =< Priority2,
    Tree2 = [(Key2, Value2), LeftTree2, Priority2, RightTree2],
    merge_tree(Tree1, LeftTree2, MergedTree2),
    MergedTree = [(Key2, Value2), MergedTree2, Priority2, RightTree2].

merge_tree(Tree, nil, Tree) :- !.
merge_tree(nil, Tree, Tree) :- Tree \= nil.
merge_tree([(Key1, Value1), LeftTree1, Priority1, RightTree1], [(Key2, Value2), LeftTree2, Priority2, RightTree2], MergedTree) :-
    compare_priority(Priority1, Priority2, [(Key1, Value1), LeftTree1, Priority1, RightTree1], [(Key2, Value2), LeftTree2, Priority2, RightTree2], MergedTree).

map_get([(Key, Value) | _], Key, Value) :- !.
map_get([(Key1, Value1), LeftTree, _, RightTree], Key, Value) :-
    Key1 < Key, map_get(RightTree, Key, Value).
map_get([(Key1, Value1), LeftTree, _, RightTree], Key, Value) :-
    Key1 > Key, map_get(LeftTree, Key, Value).

next_key(Key, NextKey) :-
    NextKey is Key + 0.1.

map_remove(Tree, Key, OutputTree) :-
    next_key(Key, NextKey),
    split_tree(Tree, Key, LeftTree, RightTree),
    split_tree(RightTree, NextKey, DiscardedTree, RightTree1),
    merge_tree(LeftTree, RightTree1, OutputTree).

map_put(Tree, Key, Value, OutputTree) :-
    next_key(Key, NextKey),
    split_tree(Tree, Key, LeftTree, RightTree),
    split_tree(RightTree, NextKey, _, RightTree1),
    rand_float(Priority),
    Node = [(Key, Value), nil, Priority, nil],
    merge_tree(LeftTree, Node, LeftTree2),
    merge_tree(LeftTree2, RightTree1, OutputTree).

map_build([], nil) :- !.
map_build([(Key, Value) | Tail], TreeMap) :-
    map_build(Tail, TempTree),
    split_tree(TempTree, Key, LeftTree, RightTree2),
    rand_float(Priority),
    Node = [(Key, Value), nil, Priority, nil],
    merge_tree(LeftTree, Node, LeftTree2),
    merge_tree(LeftTree2, RightTree2, TreeMap).


tree_size(nil, 0) :- !.
tree_size([(_, _), LeftTree, _, RightTree], Size) :-
    tree_size(LeftTree, LeftSize),
    tree_size(RightTree, RightSize),
    Size is LeftSize + RightSize + 1.

map_headMapSize(Map, ToKey, Size) :-
    split_tree(Map, ToKey, LeftTree, _),
    tree_size(LeftTree, Size).

map_tailMapSize(Map, FromKey, Size) :-
    split_tree(Map, FromKey, _, RightTree),
    tree_size(RightTree, Size).


tree_max_key([(Key, Value), _, _, nil], (Key, Value)) :- !.
tree_max_key([(_, _), _, _, RightTree], Max) :-
    RightTree \= nil,
    tree_max_key(RightTree, Max).

map_lastKey(Map, Key) :-
    tree_max_key(Map, (Key, _)).
map_lastValue(Map, Value) :-
    tree_max_key(Map, (_, Value)).
map_lastEntry(Map, (Key, Value)) :-
    tree_max_key(Map, (Key, Value)).

