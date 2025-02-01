package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int countEll;

    public boolean isEmpty() {
        return size() == 0;
    }

    public void enqueue(Object obj) {
        Objects.requireNonNull(obj);
        enqueueImpl(obj);
        countEll++;
    }

    public void clear() {
        countEll = 0;
        clearImpl();
    }

    public int size() {
        return countEll;
    }

    public Object element() {
        assert countEll != 0;
        return elementImpl();
    }

    public Object dequeue() {
        assert countEll != 0;
        Object ell = element();
        dequeueImpl();
        countEll--;
        return ell;
    }

    public void dedup() {
        int cnt = countEll;
        while (cnt > 0) {
            Object ell = dequeue();
            if (cnt == 1 || !Objects.equals(ell, element())) {
                enqueue(ell);
            }
            cnt--;
        }
    }

    protected abstract void dequeueImpl();

    protected abstract Object elementImpl();

    protected abstract void clearImpl();

    protected abstract void enqueueImpl(Object element);
}