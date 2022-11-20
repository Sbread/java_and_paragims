import java.io.IOException;
import java.util.ArrayList;

public class ReverseHexAbc2 {

    public static boolean isGoodChr(char chr) {
        int type = Character.getType(chr);
        return type == Character.DECIMAL_DIGIT_NUMBER
                || type == Character.LOWERCASE_LETTER
                || type == Character.UPPERCASE_LETTER
                || chr == '-';
    }

    public static String changeIntToString(int number) {
        StringBuilder sb = new StringBuilder();
        String str = Integer.valueOf(number).toString();
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if ('0' <= chr && chr <= '9') {
                chr += 'a' - '0';
            }
            sb.append(chr);
        }
        return sb.toString();
    }

    public static int changeStringToInt(String str) {
        str = str.toLowerCase();
        if (str.startsWith("0x")) {
            return Integer.parseUnsignedInt(str.substring(2), 16);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (chr >= 'a' && chr <= 'j') {
                chr += '0' - 'a';
            }
            sb.append(chr);
        }
        return Integer.parseInt(sb.toString());
    }

    public static void main(String[] args) {
        ArrayList<Vector> input = new ArrayList<>();
        input.add(new Vector());
        int last = 0;
        try {
            FastScanner scan = new FastScanner(System.in, ReverseHexAbc2::isGoodChr);
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
                        input.get(last).add(changeStringToInt(next));
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
                System.out.print(changeIntToString(input.get(i).get(j)) + " ");
            }
            System.out.println();
        }
    }
}