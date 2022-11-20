import java.math.*;

public class SumBigIntegerHex {
    public static BigInteger summ(String arg) {
        BigInteger sumElem = BigInteger.ZERO;

        arg += " ";
        for (int i = 0; i < arg.length(); i++) {
            int start = i;
            while (!Character.isWhitespace(arg.charAt(i))) {
                i++;
            }
            String nowElem = arg.substring(start, i);
            if (nowElem.length() != 0) {





                int radix = 10;
                if (nowElem.startsWith("0X") || nowElem.startsWith("0x")) {
                    nowElem = nowElem.substring(2);
                    radix = 16;
                }
                sumElem = sumElem.add(new BigInteger(nowElem, radix));
            }
        }
        return sumElem;
    }

    public static void main(String[] args) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < args.length; ++i) {
            sum = sum.add(summ(args[i]));
        }
        System.out.println(sum);
    }
}