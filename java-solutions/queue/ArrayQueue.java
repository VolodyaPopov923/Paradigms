package queue;

import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
    private int head;
    private Object[] elements = new Object[4];

    public void enqueueImpl(Object obj) {
        expansionArray();
        elements[getTail(countEll)] = obj;
    }


    private void expansionArray() {
        if (countEll == elements.length) {
            final Object[] temporaryElements = new Object[elements.length * 3];
            System.arraycopy(elements, head, temporaryElements, 0, elements.length - head);
            System.arraycopy(elements, 0, temporaryElements, elements.length - head, head);
            elements = temporaryElements;
            head = 0;
        }
    }

    public void dequeueImpl() {
        elements[head] = null;
        head = (head + 1) % elements.length;
    }

    public Object elementImpl() {
        return elements[head];
    }


    public void clearImpl() {
        elements = new Object[4];
        head = 0;
    }


    public int countIf(Predicate<Object> p) {
        int countPredicate = 0;
        for (int i = head, cnt = 0; cnt != countEll; i = (i + 1) % elements.length, cnt++) {
            if (p.test(elements[i])) {
                countPredicate++;
            }
        }
        return countPredicate;
    }

    // Pred: true
    // Post: head' = 0 && tail' = countEll &&
    //       countEll' = countEll &&
    //       immutable(elements.length)
    private int getTail(int countEll) {
        return (head + countEll) % elements.length;
    }
}