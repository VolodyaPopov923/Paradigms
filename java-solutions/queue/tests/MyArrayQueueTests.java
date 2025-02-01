package queue.tests;

import queue.ArrayQueue;
import queue.ArrayQueueModule;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class MyArrayQueueTests {
    public static void main(String[] args) {
        ArrayQueue stack1 = new ArrayQueue();
        int countEll = 1000;
        fillNElements(stack1, countEll);
        dump(stack1, countEll);
        System.out.println("ArrayQueueModule works correct");

    }
    public static void fillNElements(ArrayQueue stack, int countElements) {
        for (int i = 1; i <= countElements; i++) {
            stack.enqueue(i);
        }
    }

    public static void dump(ArrayQueue stack, int countEll) {
        Predicate<Object> p = new Predicate<Object>() {
            @Override
            public boolean test(Object o) {
                return o.equals(1) || o.equals(2) || o.equals(3);
            }
        };
        int count = stack.countIf(p);
        if (count != 3){
            throw new IncorrectArrayQueue("ArrayQueue(CountIf) works non-correct");
        }
        System.err.println(count);
        System.err.println(p.test(1));
        int cnt = 1;
        while (!stack.isEmpty()) {
            if (stack.size() != countEll){
                throw new IncorrectArrayQueue("ArrayQueue works non-correct");
            }
            countEll--;
            Object elementEll;
            Object dequeueEll;
            try {
                elementEll = stack.element();
                dequeueEll = stack.dequeue();
                System.err.println(stack.size() + " " +elementEll + " " + dequeueEll);
            } catch (NoSuchElementException e){
                throw new IncorrectArrayQueue("ArrayQueue works non-correct NoSuchElementException");
            }
            if (elementEll != dequeueEll || (int) dequeueEll != cnt){
                throw new IncorrectArrayQueue("ArrayQueue works non-correct");
            }
            cnt++;
        }
    }
}
