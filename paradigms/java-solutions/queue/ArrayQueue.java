package queue;

import java.util.Arrays;
import java.util.function.Predicate;

// Model: a[1]..a[N]
// Invariant: for i = 1..N a[i] != null
// let immutable(start, N, shift) for i = start..N a[i] == a'[i + shift]

public class ArrayQueue extends AbstractQueue {
    // :NOTE: 2 переменных
    private int head;
    private static Object[] elements;

    public ArrayQueue() {
        elements = new Object[5];
        head = 0;
        size = 0;
    }

    @Override
    protected void enqueueImpl(Object element) {
        elements[(head + size) % elements.length] = element;
        ensureCapacity(size + 1);
    }

    // Pred: true
    // Post: immutable(1, N, 0)
    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            Object[] intElements = new Object[2 * elements.length];
            for (int i = head + 1; i <= head + elements.length; i++) {
                intElements[i - head] = elements[i % elements.length];
            }
            size = elements.length;
            head = 0;
            elements = Arrays.copyOf(intElements, intElements.length);
        }
    }

    @Override
    protected Object elementImpl() {
        return elements[(head + 1) % elements.length];
    }

    @Override
    public Object dequeueImpl() {
        Object result = elements[(head + 1) % elements.length];
        elements[(head + 1) % elements.length] = null;
        head = (head + 1) % elements.length;
        return result;
    }

    // Pred: element != null
    // Post: N' = N + 1 && a'[1] = element && immutable(1, N, 1)
    public void push(Object element) {
        assert element != null;
        elements[(head + elements.length) % elements.length] = element;
        head = (head + elements.length - 1) % elements.length;
        size++;
        ensureCapacity(size + 1);
    }

    // Pred: N > 0
    // Post: R = a[N] && immutable(1, N, 0)
    public Object peek() {
        assert size > 0;
        return elements[(head + size + elements.length) % elements.length];
    }

    // Pred: N > 0
    // Post: R = a[N] && N' = N - 1 && immutable(1, N', 0)
    public Object remove() {
        assert size > 0;
        Object result = elements[(head + size + elements.length) % elements.length];
        elements[(head + size + elements.length) % elements.length] = null;
        size--;
        return result;
    }

    // Pred: element != null
    // Post: R = count for i = 1..N a[i] == element && immutable(1, N, 0)
    public int count(Object element) {
        assert element != null;
        int result = 0;
        for (int i = head + 1; i <= head + size; i++) {
            if (elements[i % elements.length].equals(element)) {
                result++;
            }
        }
        return result;
    }

    // Pred: element != null
    // Post: (R + 1 = first i = 1..N: a[i] = element || R = -1) && immutable(1, N, 0)
    public int indexOf(Object element) {
        assert element != null;
        int result = 0;
        if (size != 0) {
            for (int i = head + 1; i <= head + size; i++) {
                if (elements[i % elements.length].equals(element)) {
                    return result;
                }
                result++;
            }
        }
        return -1;
    }

    // Pred: element != null
    // Post: (R + 1 = last i = 1..N: a[i] = element || R = -1) && immutable(1, N, 0)
    public int lastIndexOf(Object element) {
        assert element != null;
        int result = size - 1;
        if (size != 0) {
            for (int i = head + size; i > head; i--) {
                if (elements[i % elements.length].equals(element)) {
                    return result;
                }
                result--;
            }
        }
        return -1;
    }

    public void clear() {
        head = 0;
        size = 0;
        elements = new Object[5];
    }

    @Override
    public void keepIf(Predicate<Object> predicate) {
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            Object obj = elements[(head + 1 + i) % elements.length];
            if (predicate.test(obj)) {
                elements[(head + 1 + newSize++) % elements.length] = obj;
            }
        }
        size = newSize;
    }

    @Override
    protected void takeDropWhile(Predicate<Object> predicate, int mod) {
        int changeSize = 0;
        for (int i = 0; i < size; i++) {
            Object obj = elements[(head + 1 + i) % elements.length];
            if (predicate.test(obj)) {
                changeSize++;
            } else {
                break;
            }
        }
        if (mod == 1) {
            size = changeSize;
        } else if (mod == 2) {
            head = (head + changeSize) % elements.length;
            size -= changeSize;
        }
    }
}
