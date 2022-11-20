import java.io.*;

import static java.lang.Integer.parseInt;
import static java.lang.Math.max;
import static java.lang.Math.min;

class FastScanner {

    private final Reader in;
    private final int SIZE = 1024;
    private char[] buffer = new char[SIZE];
    private int pos = 0, maxPos = 0;
    private byte[] types;
    private char[] chars;

    public FastScanner(InputStream source) {
        in = new InputStreamReader(source);
    }

    public FastScanner(InputStream source, String encoding) throws UnsupportedEncodingException {
        in = new InputStreamReader(source, encoding);
    }

    public FastScanner(File source, String encoding) throws UnsupportedEncodingException, FileNotFoundException {
        in = new InputStreamReader(new FileInputStream(source), encoding);
    }

    public void setCharters(byte[] types, char... chars) {
        this.types = types;
        this.chars = chars;
    }

    public void reBuffer() throws IOException {
        maxPos = in.read(buffer);
        pos = 0;
    }

    boolean isPartOfWord(char chr) {
        for (var type : types) {
            if (Character.getType(chr) == type) {
                return true;
            }
        }
        for (var c : chars) {
            if (chr == c) {
                return true;
            }
        }
        return false;
    }

    public void close() throws IOException {
        in.close();
    }

    public boolean isNextLineSeparator() throws IOException {
        if (pos == maxPos) {
            reBuffer();
        }
        while (!isPartOfWord(buffer[pos])) {
            if (maxPos == -1) {
                return false;
            }
            char chr = buffer[pos];
            if (chr == '\n' || chr == '\u000C' || chr == '\u0085' ||
                    chr == '\u2029' || chr == '\u000B' || chr == '\u2028') {
                pos++;
                return true;
            } else if (chr == '\r') {
                pos++;
                if (pos == maxPos) {
                    reBuffer();
                }
                if (buffer[pos] == '\n') {
                    pos++;
                }
                return true;
            }
            pos++;
            if (pos == maxPos) {
                reBuffer();
            }
        }
        return false;
    }

    public String next() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (maxPos != -1) {
            if (pos == maxPos) {
                reBuffer();
            } else {
                if (!isPartOfWord(buffer[pos])) {
                    if (sb.length() > 0) {
                        return sb.toString();
                    }
                } else {
                    sb.append(buffer[pos]);
                }
                pos++;
            }
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }
}

public class IdealPyramid {

    static final byte[] types = {
            Character.DECIMAL_DIGIT_NUMBER
    };

    static final char[] chars = {'-'};

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        scanner.setCharters(types, chars);
        try {
            int n = parseInt(scanner.next());
            int xLeft = Integer.MAX_VALUE;
            int xRight = -Integer.MAX_VALUE;
            int yLeft = Integer.MAX_VALUE;
            int yRight = -Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                int xCoordinate = parseInt(scanner.next());
                int yCoordinate = parseInt(scanner.next());
                int height = parseInt(scanner.next());
                xLeft = min(xLeft, xCoordinate - height);
                xRight = max(xRight, xCoordinate + height);
                yLeft = min(yLeft, yCoordinate - height);
                yRight = max(yRight, yCoordinate + height);
            }
            int height = (max(xRight - xLeft, yRight - yLeft) + 1) / 2;
            System.out.println((xLeft + xRight) / 2 + " " + (yLeft + yRight) / 2 + " " + height);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
