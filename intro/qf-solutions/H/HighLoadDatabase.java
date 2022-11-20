import java.io.*;

import static java.lang.Integer.parseInt;
import static java.lang.Math.max;

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

public class HighLoadDatabase {

    static final byte[] types = {
            Character.DECIMAL_DIGIT_NUMBER
    };

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        scanner.setCharters(types);
        try {
            int n = parseInt(scanner.next());
            int[] transactions = new int[n];
            int[] prefSum = new int[n + 1];
            int maxTransaction = -Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                transactions[i] = parseInt(scanner.next());
                maxTransaction = max(maxTransaction, transactions[i]);
                prefSum[i + 1] = transactions[i] + prefSum[i];
            }
            int[] f = new int[prefSum[n] + 1];
            for (int i = 1; i <= prefSum[n]; i++) {
                if (i >= prefSum[f[i - 1]]) {
                    f[i] = f[i - 1] + 1;
                } else {
                    f[i] = f[i - 1];
                }
            }
            int q = parseInt(scanner.next());
            int maxQ = -Integer.MAX_VALUE;
            int[] queries = new int[q];
            for (int i = 0; i < q; i++) {
                queries[i] = parseInt(scanner.next());
                maxQ = max(maxQ, queries[i]);
            }
            int[] ans = new int[maxQ + 1];
            for (int i = 1; i <= maxQ; i++) {
                if (i < maxTransaction) {
                    ans[i] = 0;
                } else {
                    int res = 0;
                    int cur = 0;
                    while (cur < n) {
                        if (prefSum[cur] + i >= f.length) {
                            cur = n;
                        } else {
                            cur = f[prefSum[cur] + i] - 1;
                        }
                        res++;
                    }
                    ans[i] = res;
                }
            }
            for (int i = 0; i < q; i++) {
                if (ans[queries[i]] == 0) {
                    System.out.println("Impossible");
                } else {
                    System.out.println(ans[queries[i]]);
                }
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
