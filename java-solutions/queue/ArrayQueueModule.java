package queue;

import java.util.Objects;
import java.util.function.Predicate;

// Model: a[0]..a[countEll - 1]
// Inv: head >= 0 && tail >= 0
// Let: immutable(n): forall i=0..(n - 1): a'[i] = a[i]
public class ArrayQueueModule {
    private static int head; // Pointer to head existing element
    private static int countEll; // Count element(not null) in elements
    private static Object[] elements = new Object[4]; // Array elements

    // Pred: element != null
    // Post: a'[tail'] = element &&
    //      tail' = (tail + 1) % a.length &&
    //      countEll' = countEll + 1
    public static void enqueue(Object obj) {
        Objects.requireNonNull(obj);
        expansionArray();
        elements[getTail()] = obj;
        countEll++;
    }

    // Pred: countEll > 0
    // Post: R = a[head] &&
    //       head' = (head + 1) % a.length &&
    //       a[head] = null &&
    //       countEll' = countEll - 1 &&
    public static Object dequeue() {
        assert countEll > 0;
        Object ell = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        countEll--;
        return ell;
    }

    // Pred: countEll != 0
    // Post: R = a[head] && head' = head &&
    //       tail' = tail && immutable(elements.length)
    public static Object element() {
        assert countEll != 0;
        return elements[head];
    }

    // Pred: true
    // Post: R = countEll && head' = head &&
    //       tail' = tail && immutable(elements.length)
    public static int size() {
        return countEll;
    }

    // Pred: true
    // Post: R = (countEll == 0) &&
    //       countEll' = countEll && immutable(elements.length) &&
    //       head' = head &&
    //       tail' = tail
    public static boolean isEmpty() {
        return countEll == 0;
    }
    // Pred: true
    // Post: head = tail = countEll = 0 &&
    //       ∀i ∈ [0, len(a') - 1]: a'[i] == null;

    public static void clear() {
        elements = new Object[4];
        head = countEll = 0;
    }

    // Pred: true
    // Post: R = sum([i for i in elements]) &&
    //       countEll' = countEll
    //       immutable(elements.length)
    //       head' = head && tail' = tail
    public static int countIf(Predicate<Object> p) {
        int countEllPredicate = 0;
        for (int i = head, cnt = 0; cnt != countEll; i = (i + 1) % elements.length, cnt++) {
            if (p.test(elements[i])) {
                countEllPredicate++;
            }
        }
        return countEllPredicate;
    }

    // Pred: true
    // Post: head' = 0 && tail' = countEll &&
    //       countEll' = countEll
    private static void expansionArray() {
        if (countEll == elements.length) {
            final Object[] temporaryElements = new Object[elements.length * 3];
            System.arraycopy(elements, head, temporaryElements, 0, elements.length - head);
            System.arraycopy(elements, 0, temporaryElements, elements.length - head, head);
            elements = temporaryElements;
            head = 0;
        }
    }

    // Pred: true
    // Post: head' = 0 && tail' = countEll &&
    //       countEll' = countEll &&
    //       immutable(elements.length)
    private static int getTail() {
        return (head + countEll) % elements.length;
    }

}