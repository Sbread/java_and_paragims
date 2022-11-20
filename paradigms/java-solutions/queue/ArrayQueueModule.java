package queue;

import java.util.Arrays;

// Model: a[1]..a[N]
// Invariant: for i = 1..N a[i] != null
// let immutable(start, N, shift) for i = start..N a[i] == a'[i + shift]
// let saveOrder for all i,j: i < j a'[i'] = a[i] a'[j'] = a[j] -> i' < j'

public class ArrayQueueModule {
    private static int head = 0, size = 0;
    private static Object[] elements = new Object[5];

    // Pred: element != null
    // Post: N' = N + 1 && a[N'] = element && immutable(1, N, 0)
    public static void enqueue(Object element) {
        assert element != null;
        ensureCapacity(size() + 1);
        elements[(head + size + 1) % elements.length] = element;
        size++;
        ensureCapacity(size + 1);
    }

    // Pred: true
    // Post: immutable(1, N, 0)
    private static void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            Object[] intElements = new Object[2 * elements.length];
            for (int i = head + 1; i <= head + size; i++) {
                intElements[i - head] = elements[i % elements.length];
            }
            size = elements.length;
            head = 0;
            elements = Arrays.copyOf(intElements, intElements.length);
        }
    }

    // Pred: N > 0
    // Post: R = a[1] && immutable(1, N, 0)
    public static Object element() {
        assert size > 0;
        return elements[(head + 1) % elements.length];
    }

    // Pred: N > 0
    // Post: R = a[1] && N' = N - 1 && immutable(2, N, -1)
    public static Object dequeue() {
        assert size > 0;
        Object result = elements[(head + 1) % elements.length];
        elements[(head + 1) % elements.length] = null;
        head = (head + 1) % elements.length;
        size--;
        return result;
    }

    // Pred: element != null
    // Post: N' = N + 1 && a'[1] = element && immutable(1, N, 1)
    public static void push(Object element) {
        assert element != null;
        elements[(head + elements.length) % elements.length] = element;
        head = (head + elements.length - 1) % elements.length;
        size++;
        ensureCapacity(size + 1);
    }

    // Pred: N > 0
    // Post: R = a[N] && immutable(1, N, 0)
    public static Object peek() {
        assert size > 0;
        return elements[(head + size) % elements.length];
    }

    // Pred: N > 0
    // Post: R = a[N] && N' = N - 1 && immutable(1, N', 0)
    public static Object remove() {
        assert size > 0;
        Object result = elements[(head + size) % elements.length];
        elements[(head + size) % elements.length] = null;
        size--;
        return result;
    }

    // Pred: element != null
    // Post: R = count for i = 1..N a[i] == element && immutable(1, N, 0)
    public static int count(Object element) {
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
    public static int indexOf(Object element) {
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
    public static int lastIndexOf(Object element) {
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

    // Pred: true
    // R = N && immutable(1, N, 0)
    public static int size() {
        return size;
    }

    // Pred: true
    // Post: R = N == 0 && immutable(1, N, 0)
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pred: true
    // Post: N = 0
    public static void clear() {
        head = 0;
        size = 0;
        elements = new Object[5];
    }
}
