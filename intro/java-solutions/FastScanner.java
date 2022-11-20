import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

public class FastScanner {

    private final Reader in;
    private final int SIZE = 1024;
    private char[] buffer = new char[SIZE];
    private int pos = 0, maxPos = 0;
    private Predicate<Character> predicate;

    public FastScanner(InputStream source, Predicate<Character> predicate) {
        in = new InputStreamReader(source);
        this.predicate = predicate;
    }

    public FastScanner(InputStream source, String encoding, Predicate<Character> predicate) throws UnsupportedEncodingException {
        in = new InputStreamReader(source, encoding);
        this.predicate = predicate;
    }

    public FastScanner(File source, String encoding, Predicate<Character> predicate) throws UnsupportedEncodingException, FileNotFoundException {
        in = new InputStreamReader(new FileInputStream(source), encoding);
        this.predicate = predicate;
    }

    public void reBuffer() throws IOException {
        maxPos = in.read(buffer);
        pos = 0;
    }

    public void close() throws IOException {
        in.close();
    }

    public boolean isNextLineSeparator() throws IOException {
        if (pos == maxPos) {
            reBuffer();
        }
        while (!predicate.test(buffer[pos])) {
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
                if (!predicate.test(buffer[pos])) {
                    if (!sb.isEmpty()) {
                        return sb.toString();
                    }
                } else {
                    sb.append(buffer[pos]);
                }
                pos++;
            }
        }
        if (sb.isEmpty()) {
            return null;
        }
        return sb.toString();
    }

    public String nextLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (maxPos != -1 || !isNextLineSeparator()) {
            if (pos == maxPos) {
                reBuffer();
            } else {
                sb.append(buffer[pos++]);
            }
        }
        return sb.toString();
    }
}
