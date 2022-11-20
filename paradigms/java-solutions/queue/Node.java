package queue;

public class Node {
    private Node next, prev;
    private Object value;

    public Node() {
        next = this;
        prev = this;
        value = null;
    }

    public Node(Node next, Node prev, Object value) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public Object getValue() {
        return value;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
