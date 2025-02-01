package queue;

import java.util.function.Predicate;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=1..k: a'[i] = a[i]
public interface Queue {

    // Pred: element != null
    // Post: a'[tail'] = element &&
    //      tail' = (tail + 1) % a.length &&
    //      countEll' = countEll + 1
    void enqueue(Object obj);

    // Pred: countEll > 0
    // Post: R = a[head] &&
    //       head' = (head + 1) % a.length &&
    //       a[head] = null &&
    //       countEll' = countEll - 1 &&
    Object dequeue();

    // Pred: countEll != 0
    // Post: R = a[head] && head' = head &&
    //       tail' = tail && immutable(elements.length)
    Object element();

    // Pred: true
    // Post: R = countEll && head' = head &&
    //       tail' = tail && immutable(elements.length)
    int size();

    // Pred: true
    // Post: R = (countEll == 0) &&
    //       countEll' = countEll && immutable(elements.length) &&
    //       head' = head &&
    //       tail' = tail
    boolean isEmpty();
    // Pred: true
    // Post: head = tail = countEll = 0 &&
    //       ∀i ∈ [0, len(a') - 1]: a'[i] == null;

    void clear();

    // Pred: true
    // Post: R = sum([i for i in elements]) &&
    //       countEll' = countEll
    //       immutable(elements.length)
    //       head' = head && tail' = tail
    int countIf(Predicate<Object> p);

    // Pred: true
    // Post: ∀ i ∈ [0, len(elements) - 1], если elements[i] != elements[i + 1] : elements'.append(elements[i])
    public void dedup();
}
