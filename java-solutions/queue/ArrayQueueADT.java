package queue;

import java.util.Objects;
import java.util.function.Predicate;

// Model: a[0]..a[countEll - 1]
// Inv: head >= 0 && tail >= 0
// Let: immutable(n): forall i=0..(n - 1): a'[i] = a[i]
public class ArrayQueueADT {
    private int head; // Pointer to head existing element
    private int countEll; // Count element(not null) in elements
    private Object[] elements = new Object[4]; // Array elements

    // Pred: element != null
    // Post: a'[tail'] = element &&
    //      tail' = (tail + 1) % a.length &&
    //      countEll' = countEll + 1
    public static void enqueue(ArrayQueueADT queue, Object obj) {
        Objects.requireNonNull(obj);
        expansionArray(queue);
        queue.elements[getTail(queue)] = obj;
        queue.countEll++;
    }

    // Pred: true
    // Post: head' = 0 && tail' = countEll &&
    //       countEll' = countEll
    private static void expansionArray(ArrayQueueADT queue) {
        if (queue.countEll == queue.elements.length) {
            final Object[] temporaryElements = new Object[queue.elements.length * 3];
            System.arraycopy(queue.elements, queue.head, temporaryElements, 0, queue.elements.length - queue.head);
            System.arraycopy(queue.elements, 0, temporaryElements, queue.elements.length - queue.head, queue.head);
            queue.elements = temporaryElements;
            queue.head = 0;
        }
    }

    // Pred: countEll > 0
    // Post: R = a[head] &&
    //       head' = (head + 1) % a.length &&
    //       a[head] = null &&
    //       countEll' = countEll - 1 &&
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.countEll > 0;
//        if (queue.mapObject.get(queue.elements[queue.head]) != null) {
//            queue.mapObject.remove(queue.elements[queue.head]);
//        } else {
//            queue.mapObject.put(queue.elements[queue.head], queue.mapObject.get(queue.elements[queue.head]) - 1);
//        }
        Object ell = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.countEll--;
        return ell;
    }

    // Pred: countEll != 0
    // Post: R = a[head] && head' = head &&
    //       tail' = tail && immutable(elements.length)
    public static Object element(ArrayQueueADT queue) {
        assert queue.countEll != 0;
        return queue.elements[queue.head];
    }

    // Pred: true
    // Post: R = countEll && head' = head &&
    //       tail' = tail && immutable(elements.length)
    public static int size(ArrayQueueADT queue) {
        return queue.countEll;
    }

    // Pred: true
    // Post: R = (countEll == 0) &&
    //       countEll' = countEll && immutable(elements.length) &&
    //       head' = head &&
    //       tail' = tail
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.countEll == 0;
    }
    // Pred: true
    // Post: head = tail = countEll = 0 &&
    //       ∀i ∈ [0, len(a') - 1]: a'[i] == null;

    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[4];
        queue.head = queue.countEll = 0;
    }

    // Pred: true
    // Post: R = sum([i for i in elements]) &&
    //       countEll' = countEll
    //       immutable(elements.length)
    //       head' = head && tail' = tail
    public static int countIf(ArrayQueueADT queue, Predicate<Object> p) {
        int countEllPredicate = 0;
        for (int i = queue.head, cnt = 0; cnt != queue.countEll; i = (i + 1) % queue.elements.length, cnt++) {
            if (p.test(queue.elements[i])) {
                countEllPredicate++;
            }
        }
        return countEllPredicate;
    }

    // Pred: true
    // Post: head' = 0 && tail' = countEll &&
    //       countEll' = countEll &&
    //       immutable(elements.length)
    private static int getTail(ArrayQueueADT queue) {
        return (queue.head + queue.countEll) % queue.elements.length;
    }
}