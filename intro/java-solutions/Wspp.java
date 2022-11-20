import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Wspp {
    public static boolean isGoodChr(char chr) {
        int type = Character.getType(chr);
        return type == Character.TITLECASE_LETTER
                || type == Character.UPPERCASE_LETTER
                || type == Character.LOWERCASE_LETTER
                || type == Character.OTHER_LETTER
                || type == Character.MODIFIER_LETTER
                || type == Character.DASH_PUNCTUATION
                || chr == '\'';
    }

    public static void main(String[] args) {
        Map<String, Integer> statAmount = new LinkedHashMap<>();
        Map<String, IntList> stat = new LinkedHashMap<>();
        int position = 1;
        try {
            FastScanner scan = new FastScanner(new File(args[0]), "utf8", Wspp::isGoodChr);
            try {
                while (true) {
                    String word = "";
                    word = scan.next();
                    if (word == null) {
                        break;
                    } else {
                        word = word.toLowerCase();
                        statAmount.put(word, statAmount.getOrDefault(word, 0) + 1);
                        if (!stat.containsKey(word)) {
                            stat.put(word, new IntList());
                            IntList info = stat.get(word);
                            info.add(position);
                            stat.put(word, info);
                        } else {
                            IntList info = stat.get(word);
                            info.add(position);
                            stat.put(word, info);
                        }
                        position++;
                    }
                }
            } catch (IOException e) {
                System.out.println("next() throws exception" + e.getMessage());
            } finally {
                scan.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("can not find input file " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("unsupported encoding " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException was raised " + e.getMessage());
        }
        List<Map.Entry<String, Integer>> outputFirst = new ArrayList<>(statAmount.entrySet());
        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]), "utf8"
                    )
            );
            try {
                for (int i = 0; i < outputFirst.size(); i++) {
                    IntList value = stat.get(outputFirst.get(i).getKey());
                    writer.append(outputFirst.get(i).getKey()).append(" ")
                            .append(String.valueOf(outputFirst.get(i).getValue()));
                    if (value.size() != 0) {
                        writer.write(" " + value.toString());
                    }
                    writer.newLine();
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
