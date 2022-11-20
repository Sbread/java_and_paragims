import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reverse {
    public static boolean isGoodChr(char chr) {
        return Character.getType(chr) == Character.DECIMAL_DIGIT_NUMBER || chr == '-';
    }

    public static void main(String[] args) {
        ArrayList<Vector> input = new ArrayList<>();
        input.add(new Vector());
        int last = 0;
        try {
            FastScanner scan = new FastScanner(System.in, Reverse::isGoodChr);
            try {
                while (true) {
                    if (scan.isNextLineSeparator()) {
                        input.get(last).trimToSize();
                        last++;
                        input.add(new Vector());
                    } else {
                        String next = scan.next();
                        if (next == null) {
                            break;
                        }
                        input.get(last).add(Integer.parseInt(next));
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException was raised" + e.getMessage());
            } finally {
                scan.close();
            }
        } catch (IOException e) {
            System.err.println("Cannot close FastScanner" + e.getMessage());
        }
        input.trimToSize();
        for (int i = input.size() - 2; i >= 0; i--) {
            for (int j = input.get(i).size() - 1; j >= 0; j--) {
                System.out.print((input.get(i).get(j)) + " ");
            }
            System.out.println();
        }
    }
}