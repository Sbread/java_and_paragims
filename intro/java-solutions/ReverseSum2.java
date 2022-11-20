import java.util.Scanner;
import java.util.ArrayList;

public class ReverseSum2 {
    public static void main(String[] args) {
        ArrayList<IntList> input = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        int lastAns = 0;
        int amountOfColumn = 0;
        while (scan.hasNextLine()) {
            Scanner scanLine = new Scanner(scan.nextLine());
            IntList line = new IntList();
            while (scanLine.hasNextInt()) {
                line.add(scanLine.nextInt());
            }
            line.trimToSize();
            amountOfColumn = Math.max(amountOfColumn, line.size());
            input.add(line);
        }
        input.trimToSize();
        int[] prefixSum = new int[amountOfColumn];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                prefixSum[j] += input.get(i).get(j);
                if (j > 0) {
                    lastAns += prefixSum[j - 1];
                } else {
                    lastAns = 0;
                }
                System.out.print(prefixSum[j] + lastAns + " ");
            }
            System.out.println();
        }
    }
}