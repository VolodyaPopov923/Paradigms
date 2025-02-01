package queue.tests;

import queue.ArrayQueueModule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class MyArrayQueueModuleTests {
    public static void main(String[] args) {
        int countEll = 1000;
        fillNElements(countEll);
        dump(countEll);
        System.out.println("ArrayQueueModule works correct");
    }
    public static void fillNElements(int countElements) {
        for (int i = 1; i <= countElements; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void dump(int countEll) {
        int cnt = 1;
        Predicate<Object> p = new Predicate<Object>() {
            @Override
            public boolean test(Object o) {
                return o.equals(1) || o.equals(2) || o.equals(3);
            }
        };
        int count = ArrayQueueModule.countIf(p);
        if (count != 3){
            throw new IncorrectArrayQueue("ArrayQueue(CountIf) works non-correct");
        }
        while (!ArrayQueueModule.isEmpty()) {
            if (ArrayQueueModule.size() != countEll){
                throw new IncorrectArrayQueueModule("ArrayModule works non-correct");
            }
            countEll--;
            Object elementEll;
            Object dequeueEll;
            try {
                elementEll = ArrayQueueModule.element();
                dequeueEll = ArrayQueueModule.dequeue();
                System.out.println(ArrayQueueModule.size() + " " +elementEll + " " + dequeueEll);
            } catch (NoSuchElementException e){
                throw new IncorrectArrayQueueModule("ArrayModule works non-correct NoSuchElementException");
            }
            if (elementEll != dequeueEll || (int) dequeueEll != cnt){
                throw new IncorrectArrayQueueModule("ArrayModule works non-correct");
            }
            cnt++;
        }
    }
}
