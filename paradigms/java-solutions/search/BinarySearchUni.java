package search;

public class BinarySearchUni {

    // Pred: a != {} && i = 1 .. k  a[i] < a[i - 1], i = k + 1 .. n a[i] > a[i - 1], k + 1 <= n (second part is not empty)
    // Post: a'[] = a[]; R - min : i = 1 .. R  a[i] < a[i - 1], i = R .. n a[i] > a[i - 1]
    public static int binarySearchUniIter(int[] a) {
        // let a[-1] = +inf, a[a.length] = +inf
        int lBounce = -1;
        int rBounce = a.length;
        int mid;
        // invariant: i = lBounce .. k  a[i] < a[i - 1], i = k + 1 .. rBounce a[i] > a[i - 1]
        // let a[-1] = +inf, a[a.length] = +inf && lBounce + 1 < rBounce
        while (lBounce + 1 < rBounce) {
            mid = (lBounce + rBounce) / 2;
            // lBounce' + 1 < rBounce' -> lBounce' < mid < rBounce'
            if (mid + 1 < a.length && a[mid + 1] <= a[mid] && mid > 0 && a[mid] < a[mid - 1]) {
                // mid + 1 < a.length && a[mid + 1] <= a[mid] && mid > 0 && a[mid] < a[mid - 1]
                // -> a[mid - 1] > a[mid] >= a[mid + 1] -> a[mid] belongs to the first part of the array
                lBounce = mid;
                // a[lBounce'] belongs to the first part of the array, subarray a[lBounce'...rBounce'] matches invariant
                // lBounce' < mid < rBounce' -> lBounce' < lBounce'' < rBounce' = rBounce''
            } else if (mid + 1 < a.length && a[mid + 1] <= a[mid]) {
                // mid == 0 && mid + 1 < a.length && a[mid + 1] <= a[mid]
                // -> a[mid] belongs to the first part of the array
                // lBounce' < mid < rBounce' -> lBounce' < lBounce'' < rBounce' = rBounce''
                lBounce = mid;
                // a[lBounce'] belongs to the first part of the array, subarray a[lBounce'...rBounce'] matches invariant
            } else {
                // a[mid] < a[mid + 1] -> a[mid + 1] belongs to the second part of the array
                // || mid + 1 == a.length -> because second part is not empty a[mid] belongs to the second part of the array
                rBounce = mid;
                // lBounce' < mid < rBounce' -> lBounce'' = lBounce' < rBounce'' < rBounce'
                // a[rBounce'] belongs to the second part of the array, subarray a[lBounce'...rBounce'] matches invariant
            }
        }
        // lBounce + 1 >= rBounce, a[lBounce'] belongs to the first part of the array
        // && a[rBounce'] belongs to the second part of the array -> rBounce is length of first part
        return rBounce;
    }

    // Pred: // i = lBounce .. k  a[i] < a[i - 1], i = k + 1 .. rBounce a[i] > a[i - 1]
    // let a[-1] = +inf, a[a.length] = +inf
    // Post: a'[] = a[]; R - min : i = lBounce .. R  a[i] < a[i - 1], i = R .. rBounce a[i] > a[i - 1]
    public static int binarySearchUniRec(int[] a, int lBounce, int rBounce) {
        if (lBounce + 1 >= rBounce) {
            // lBounce + 1 >= rBounce, a[lBounce'] belongs to the first part of the array
            // && a[rBounce'] belongs to the second part of the array -> rBounce is length of first part
            return rBounce;
        }
        int mid = (lBounce + rBounce) / 2;
        // lBounce + 1 < rBounce -> lBounce < mid < rBounce
        if (mid + 1 < a.length && a[mid + 1] <= a[mid] && mid > 0 && a[mid] < a[mid - 1]) {
            // mid + 1 < a.length && a[mid + 1] <= a[mid] && mid > 0 && a[mid] < a[mid - 1]
            // -> a[mid - 1] > a[mid] >= a[mid + 1] -> a[mid] belongs to the first part of the array
            // lBounce < mid < rBounce -> lBounce < lBounce' < rBounce = rBounce'
            return binarySearchUniRec(a, mid, rBounce);
        } else if (mid + 1 < a.length && a[mid + 1] <= a[mid]) {
            // mid == 0 && mid + 1 < a.length && a[mid + 1] <= a[mid]
            // -> a[mid] belongs to the first part of the array
            // lBounce < mid < rBounce -> lBounce < lBounce' < rBounce = rBounce'
            return binarySearchUniRec(a, mid, rBounce);
        } else {
            // a[mid] < a[mid + 1] -> a[mid + 1] belongs to the second part of the array
            // || mid + 1 == a.length -> because second part is not empty a[mid] belongs to the second part of the array
            // lBounce < mid < rBounce -> lBounce' = lBounce < rBounce' < rBounce
            return binarySearchUniRec(a, lBounce, mid);
        }
    }

    // Pred: args != {} && i = 1 .. k  args[i] < args[i - 1], i = k + 1 .. n args[i] > args[i - 1], k + 1 <= n
    // (second part is not empty)
    // Post: sout R - min : i = lBounce .. R  a[i] < a[i - 1], i = R .. rBounce a[i] > a[i - 1]
    public static void main(String[] args) {
        int[] a = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            a[i] = Integer.parseInt(args[i]);
        }
        // i = 0..n - 1, a[i] = Integer.parseInt(args[i])
        //System.out.println(binarySearchUniIter(a));
        System.out.println(binarySearchUniRec(a, -1, a.length));
    }
}
