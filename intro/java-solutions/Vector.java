import java.util.Arrays;

public class Vector {
    private int[] array = new int[1];
    private int size = 0;

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
}