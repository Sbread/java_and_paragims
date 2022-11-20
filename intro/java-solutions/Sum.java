public class Sum {
    private static int summ(String str) {
        int ans = 0;
        str += " ";
        for (int i = 0; i < str.length(); i++) {
            int start = i;
            while (!Character.isWhitespace(str.charAt(i))) {
                i++;
            }
            String nowElem = str.substring(start, i);
            if (!nowElem.isEmpty()) {
                ans += Integer.parseInt(nowElem);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int ans = 0;
        for (int i = 0; i < args.length; i++) {
            ans += summ(args[i]);
        }
        System.out.println(ans);
    }
}