import java.io.*;

import static java.lang.Integer.parseInt;

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

public class BadTreap {

    static final byte[] types = {
            Character.DECIMAL_DIGIT_NUMBER
    };

    public static void main(String[] args) {
        int n;
        FastScanner scanner = new FastScanner(System.in);
        scanner.setCharters(types);
        try {
            n = parseInt(scanner.next());
            for (int i = -25000 * 710; n > 0; n--, i += 710) {
                System.out.println(i);
            }
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
