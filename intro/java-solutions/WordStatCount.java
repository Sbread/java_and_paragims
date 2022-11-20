import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordStatCount {

    static boolean isGoodChar(char chr) {
        return ((Character.getType(chr) == Character.DASH_PUNCTUATION) || (Character.isLetter(chr)) || (chr == '\''));
    }

    public static void main(String[] args) {
        LinkedHashMap<String, Integer> stat = new LinkedHashMap<>();
        try {
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(args[0]), StandardCharsets.UTF_8
            );
            try {
                StringBuilder sb = new StringBuilder();
                StringBuilder sbWord = new StringBuilder();
                while (true) {
                    char[] buffer = new char[1024];
                    int amount = reader.read(buffer);
                    sb.setLength(0);
                    if (amount == -1) {
                        break;
                    } else {
                        sb.append(buffer);
                    }
                    String s = sb.toString();
                    for (int i = 0; i < s.length(); i++) {
                        char chr = s.charAt(i);
                        if (isGoodChar(chr)) {
                            sbWord.append(chr);
                        } else {
                            if (!sbWord.isEmpty()) {
                                String word = sbWord.toString().toLowerCase();
                                sbWord.setLength(0);
                                stat.put(word, stat.getOrDefault(word, 0) + 1);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("read throws exception" + e.getMessage());
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("can not find input file " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("unsupported encoding " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException was raised " + e.getMessage());
        }
        List<Map.Entry<String, Integer>> output = new ArrayList<>(stat.entrySet());
        output.sort(Map.Entry.comparingByValue());
        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]), StandardCharsets.UTF_8
                    )
            );
            try {
                for (int i = 0; i < output.size(); i++) {
                    writer.write(output.get(i).getKey() + " " + output.get(i).getValue() + "\n");
                }
            } catch (IOException e) {
                System.out.println("can not write " + e.getMessage());
            } finally {
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("can not find output file " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("unsupported encoding " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException was raised " + e.getMessage());
        }
    }
}