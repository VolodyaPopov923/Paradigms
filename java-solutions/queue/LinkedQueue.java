package queue;

import java.util.Objects;
import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    public void enqueueImpl(Object obj) {
        Node newNode = new Node(obj);
        if (head == null) head = newNode;
        else tail.next = newNode;
        tail = newNode;
    }


    public void dequeueImpl() {
        head.value = null;
        head = head.next;
    }

    public Object elementImpl() {
        return head.value;
    }


    public void clearImpl() {
        head = null;
        tail = null;
    }

    public int countIf(Predicate<Object> p) {
        int countPredicate = 0;
        for (Node node = head; node != null; node = node.next) {
            if (p.test(node.value)) {
                countPredicate++;
            }
        }
        return countPredicate;
    }

    private static class Node {
        private Object value;
        private Node next;

        public Node(Object value) {
            this.value = Objects.requireNonNull(value);
        }
    }
}
