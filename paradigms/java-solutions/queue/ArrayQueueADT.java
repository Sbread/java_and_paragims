package queue;

import java.util.Arrays;

// Model: a[1]..a[N]
// Invariant: for i = 1..N a[i] != null
// let immutable(start, N, shift) for i = start..N a[i] == a'[i + shift]
// let saveOrder for all i,j: i < j a'[i'] = a[i] a'[j'] = a[j] -> i' < j'

public class ArrayQueueADT {
    private int head = 0, size = 0;
    private Object[] elements = new Object[5];

    // Pred: element != null && queue != null
    // Post: N' = N + 1 && a[N'] = element && immutable(1, N, 0)
    public static void enqueue(final ArrayQueueADT queue, final Object element) {
        assert element != null;
        queue.elements[(queue.head + queue.size + 1) % queue.elements.length] = element;
        queue.size++;
        ensureCapacity(queue, queue.size + 1);
    }

    // Pred: queue != null
    // Post: immutable(1, N, 0)
    private static void ensureCapacity(final ArrayQueueADT queue, final int capacity) {
        if (capacity > queue.elements.length) {
            // :NOTE: Руками
            final Object[] intElements = new Object[2 * queue.elements.length];
            for (int i = queue.head + 1; i <= queue.head + queue.elements.length; i++) {
                intElements[i - queue.head] = queue.elements[i % queue.elements.length];
            }
            queue.size = queue.elements.length;
            queue.head = 0;
            queue.elements = Arrays.copyOf(intElements, intElements.length);
        }
    }

    // Pred: N > 0 && queue != null
    // Post: R = a[1] && immutable(1, N, 0)
    public static Object element(final ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[(queue.head + 1) % queue.elements.length];
    }

    // Pred: N > 0 && queue != null
    // Post: R = a[1] && N' = N - 1 && immutable(2, N, -1)
    public static Object dequeue(final ArrayQueueADT queue) {
        assert queue.size > 0;
        final Object result = queue.elements[(queue.head + 1) % queue.elements.length];
        queue.elements[(queue.head + 1) % queue.elements.length] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return result;
    }

    // Pred: element != null && queue != null
    // Post: N' = N + 1 && a'[1] = element && immutable(1, N, 1)
    public static void push(final ArrayQueueADT queue, final Object element) {
        assert element != null;
        queue.elements[(queue.head + queue.elements.length) % queue.elements.length] = element;
        queue.head = (queue.head + queue.elements.length - 1) % queue.elements.length;
        queue.size++;
        ensureCapacity(queue, queue.size + 1);
    }

    // Pred: N > 0 && queue != null
    // Post: R = a[N] && immutable(1, N, 0)
    public static Object peek(final ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[(queue.head + queue.size + queue.elements.length) % queue.elements.length];
    }

    // Pred: N > 0 && queue != null
    // Post: R = a[N] && N' = N - 1 && immutable(1, N', 0)
    public static Object remove(final ArrayQueueADT queue) {
        assert queue.size > 0;
        final Object result = queue.elements[(queue.head + queue.size + queue.elements.length) % queue.elements.length];
        queue.elements[(queue.head + queue.size + queue.elements.length) % queue.elements.length] = null;
        queue.size--;
        return result;
    }

    // Pred: element != null && queue != null
    // Post: R = count for i = 1..N a[i] == element && immutable(1, N, 0)
    public static int count(final ArrayQueueADT queue, final Object element) {
        assert element != null;
        int result = 0;
        for (int i = queue.head + 1; i <= queue.head + queue.size; i++) {
            if (queue.elements[i % queue.elements.length].equals(element)) {
                result++;
            }
        }
        return result;
    }

    // Pred: element != null && queue != null
    // Post: (R + 1 = first i = 1..N: a[i] = element || R = -1) && immutable(1, N, 0)
    public static int indexOf(final ArrayQueueADT queue, final Object element) {
        assert element != null;
        int result = 0;
        if (queue.size != 0) {
            for (int i = queue.head + 1; i <= queue.head + queue.size; i++) {
                if (queue.elements[i % queue.elements.length].equals(element)) {
                    return result;
                }
                result++;
            }
        }
        return -1;
    }

    // Pred: element != null && queue != null
    // Post: (R + 1 = last i = 1..N: a[i] = element || R = -1) && immutable(1, N, 0)
    public static int lastIndexOf(final ArrayQueueADT queue, final Object element) {
        assert element != null;
        int result = queue.size - 1;
        if (queue.size != 0) {
            for (int i = queue.head + queue.size; i > queue.head; i--) {
                if (queue.elements[i % queue.elements.length].equals(element)) {
                    return result;
                }
                result--;
            }
        }
        return -1;
    }

    // Pred: queue != null
    // R = N && immutable(1, N, 0)
    public static int size(final ArrayQueueADT queue) {
        return queue.size;
    }

    // Pred: queue != null
    // Post: R = N == 0 && immutable(1, N, 0)
    public static boolean isEmpty(final ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pred: queue != null
    // Post: N = 0
    public static void clear(final ArrayQueueADT queue) {
        queue.head = 0;
        queue.size = 0;
        queue.elements = new Object[5];
    }
}
