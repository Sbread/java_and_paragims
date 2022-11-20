import java.util.Arrays;

public class IntList {
    private int[] array;
    private int size;

    public IntList() {
        this.array = new int[1];
        this.size = 0;
    }

    public IntList(int[] array) {
        this.array = array;
        this.size = array.length;
    }

    public void add(int a) {
        if (size >= array.length) {
            int[] biggerArray = Arrays.copyOf(array, array.length * 2);
            array = biggerArray;
        }
        array[size] = a;
        size++;
    }

    public int get(int i) {
        return array[i];
    }

    public void set(int i, int a) {
        array[i] = a;
    }

    public int size() {
        return size;
    }

    public void trimToSize() {
        int[] newArray = Arrays.copyOf(array, Math.min(array.length, size));
        array = newArray;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}