package queue;

import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue {
    private Node root = new Node();

    @Override
    protected void enqueueImpl(Object element) {
        Node queueElement = new Node(root, root.getPrev(), element);
        root.getPrev().setNext(queueElement);
        root.setPrev(queueElement);
    }

    @Override
    public Object dequeueImpl() {
        Object result = root.getNext().getValue();
        root.setNext(root.getNext().getNext());
        root.getNext().setPrev(root);
        return result;
    }

    @Override
    protected Object elementImpl() {
        return root.getNext().getValue();
    }

    @Override
    public void clear() {
        size = 0;
        root = new Node();
    }

    // :NOTE: Дублирование
    @Override
    public void keepIf(Predicate<Object> predicate) {
        int newSize = 0;
        Node current = root.getNext();
        Node prev = root;
        for (int i = 0; i < size; i++) {
            if (predicate.test(current.getValue())) {
                prev.setNext(current);
                current.setPrev(prev);
                prev = prev.getNext();
                newSize++;
            }
            current = current.getNext();
        }
        prev.setNext(root);
        root.setPrev(prev);
        size = newSize;
    }

    @Override
    protected void takeDropWhile(Predicate<Object> predicate, int mod) {
        int changeSize = 0;
        Node current = root.getNext();
        for (int i = 0; i < size; i++) {
            if (predicate.test(current.getValue())) {
                current = current.getNext();
                changeSize++;
            } else {
                break;
            }
        }
        if (mod == 1) {
            current.getPrev().setNext(root);
            root.setPrev(current.getPrev());
            size = changeSize;
        } else if (mod == 2) {
            root.setNext(current);
            current.setPrev(root);
            size -= changeSize;
        }
    }
}
