package search;

public class BinarySearch {

    // Pred: a != {} && a[] sorted non-ascending
    // Post: a'[] = a[]; (a[R] <= x || R == a[a.length]) && (R == 0 || a[R - 1] > x)
    public static int binarySearchIter(int x, int[] a) {
        int left = -1;
        int right = a.length;
        int mid;
        //  (right == a.length || a[right] <= x) && left <= right && (left == -1 || a[left] > x)
        while (left + 1 < right) {
            //  left' <= right' && (right' == a.length || a[right'] <= x) && (left' == -1 || a[left'] > x)
            mid = (right + left) / 2;
            // left' + 1 < right' -> left' < mid < right'
            // left' <= mid <= right' && left' <= right' && (right' == a.length || a[right'] <= x)
            // && (left' == -1 || a[left'] > x)
            if (a[mid] <= x) {
                // left' <= mid <= right' && a[mid] <= x && a[] sorted non-ascending -> a[y] <= x,
                // for all y where mid < y <= right'
                right = mid;
                // left' < mid < right' -> left' = left'' < right'' < right'
                // a[right'] <= x && left' <= right' && (left' == -1 || a[left'] > x)
            } else {
                // left' <= mid <= right' && a[mid] > x && a[] sorted non-ascending -> a[y] > x,
                // for all y, where 0 <= y <= mid'
                left = mid;
                //left' < mid < right' -> left' < left'' < right'' = right'
                // a[left'] > x && left' <= right' && (right' == a.length || a[right'] <= x)
            }
        }
        // left' + 1 >= right' && a[left'] > x && a[right'] <= x
        return right;
    }

    // Pred: a != {} && a[] sorted non-ascending && -1 <= left <= right <= a.length && (left == -1 || a[left] > x)
    // Post: a'[] = a[]; (a[R] <= x || R == a[a.length])
    public static int binarySearchRec(int x, int[] a, int left, int right) {
        //a != {} && a[] sorted non-ascending && -1 <= left <= right <= a.length && (left == -1 || a[left] > x)
        if (left + 1 >= right) {
            //left <= right && left + 1 >= right && (left == -1 || a[left] > x) && (right == a.length || a[right] <= x)
            return right;
        }
        if (a[(right + left) / 2] <= x) {
            // left <= (right + left) / 2 <= right && a[(right + left) / 2] <= x -> a[y] <= x,
            // for all y, where , (right + left) / 2 < y <= right'
            return binarySearchRec(x, a, left, (right + left) / 2);
        } else {
            // left <= (right + left) / 2 <= right && a[(right + left) / 2] > x -> a[y] > x,
            // for all y, where 0 <= y <= mid
            return binarySearchRec(x, a,(right + left) / 2, right);
        }
    }

    // Pred: a != {}, i = 1 .. args.length - 1 a[i] <= a[i - 1]
    // Post: a'[] = a[]; sout (a[R] <= x || R == a[a.length]) && (R == 0 || a[R - 1] > x)
    public static void main(String[] args) {
        // args != {}
        int x = Integer.parseInt(args[0]);
        if (args.length > 1) {
            // args.length > 1 -> a != {}
            int[] a = new int[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                a[i - 1] = Integer.parseInt(args[i]);
            }
            // a[0] == args[1] && ... && a[args.length - 2] == args[args.length - 1]
            System.out.println(binarySearchIter(x, a));
            //System.out.println(binarySearchRec(x, a, -1, a.length));
        } else {
            // args.length == 1 -> a == {}
            System.out.println(0);
        }
    }
}