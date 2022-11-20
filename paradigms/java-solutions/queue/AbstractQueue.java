package queue;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int size;

    @Override
    public void enqueue(Object element) {
        assert element != null;
        size++;
        enqueueImpl(element);
    }

    protected abstract void enqueueImpl(Object element);

    @Override
    public Object dequeue() {
        assert size > 0;
        size--;
        return dequeueImpl();
    }

    protected abstract Object dequeueImpl();

    @Override
    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public abstract void clear();

    @Override
    public void removeIf(Predicate<Object> predicate) {
        assert predicate != null;
        keepIf(Predicate.not(predicate));
    }

    @Override
    public void retainIf(Predicate<Object> predicate) {
        keepIf(Objects.requireNonNull(predicate));
    }

    public abstract void keepIf(Predicate<Object> predicate);

    @Override
    public void takeWhile(Predicate<Object> predicate) {
        assert predicate != null;
        takeDropWhile(predicate, 1);
    }

    @Override
    public void dropWhile(Predicate<Object> predicate) {
        assert predicate != null;
        takeDropWhile(predicate, 2);
    }

    protected abstract void takeDropWhile(Predicate<Object> predicate, int mod);
}
