package queue;

import java.util.function.Predicate;

// Model: a[1]..a[N]
// Invariant: for i = 1..N a[i] != null
// let immutable(start, N, shift) for i = start..N a[i] == a'[i + shift]
// :NOTE: Нельзя
// let saveOrder a'[] = subsequence a[]
public interface Queue {

    // Pred: element != null
    // Post: N' = N + 1 && a[N'] = element && immutable(1, N, 0)
    void enqueue(Object element);

    // Pred: N > 0
    // Post: R = a[1] && N' = N - 1 && immutable(2, N, -1)
    Object dequeue();

    // Pred: N > 0
    // Post: R = a[1] && immutable(1, N, 0)
    Object element();

    // Pred: true
    // Post: R = N == 0
    boolean isEmpty();

    // Pred: true
    // Post: R = N
    int size();

    // Pred: true
    // Post: N = 0;
    void clear();

    // Pred: predicate != null
    // Post: for i = 1..N if test(a[i]) == true a'[] contains a[i] && saveOrder && N' = amount test(a[i]) == true
    void removeIf(Predicate<Object> predicate);

    // Pred: predicate != null
    // Post: for i = 1..N if test(a[i]) == false a'[] contains a[i] && saveOrder && N' = amount test(a[i]) == false
    void retainIf(Predicate<Object> predicate);

    // Pred: predicate != null
    // N' + 1 = (first i 1..N test(a[i])) == false && immutable(1, N', 0) && saveOrder
    void takeWhile(Predicate<Object> predicate);

    // Pred: predicate != null
    // Post: N' = N + 1 - (first i = 1..N test(a[i]) == false) && immutable(N - N', N, N' - N) && saveOrder
    void dropWhile(Predicate<Object> predicate);
}
