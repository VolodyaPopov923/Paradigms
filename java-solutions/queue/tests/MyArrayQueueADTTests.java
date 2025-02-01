package queue.tests;

import queue.ArrayQueue;
import queue.ArrayQueueADT;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class MyArrayQueueADTTests {
    public static void main(String[] args) {
        ArrayQueueADT stack1 = new ArrayQueueADT();
        int countEll = 1000;
        fillNElements(stack1, countEll);
        dump(stack1, countEll);
        System.out.println("ArrayQueueADT works correct");
    }
    public static void fillNElements(ArrayQueueADT stack, int countElements) {
        for (int i = 1; i <= countElements; i++) {
            ArrayQueueADT.enqueue(stack, i);
        }
    }

    public static void dump(ArrayQueueADT stack, int countEll) {
        int cnt = 1;
        Predicate<Object> p = new Predicate<Object>() {
            @Override
            public boolean test(Object o) {
                return o.equals(1) || o.equals(2) || o.equals(3);
            }
        };
        int count = ArrayQueueADT.countIf(stack, p);
        if (count != 3){
            throw new IncorrectArrayQueue("ArrayQueue(CountIf) works non-correct");
        }
        while (!ArrayQueueADT.isEmpty(stack)) {
            if (ArrayQueueADT.size(stack) != countEll){
                throw new IncorrectArrayQueueADT("ArrayQueue works non-correct");
            }
            countEll--;
            Object elementEll;
            Object dequeueEll;

            try {
                elementEll = ArrayQueueADT.element(stack);
                dequeueEll = ArrayQueueADT.dequeue(stack);
                System.out.println(ArrayQueueADT.size(stack) + " " + elementEll + " " + dequeueEll);
            } catch (NoSuchElementException e){
                throw new IncorrectArrayQueueADT("ArrayQueue works non-correct, NoSuchElementException");
            }
            if (elementEll != dequeueEll || (int) dequeueEll != cnt){
                throw new IncorrectArrayQueueADT("ArrayQueue works non-correct");
            }
            cnt++;
        }
    }
}
