package expression.generic;

import expression.Operand;
import expression.exceptions.EvaluateException;
import expression.exceptions.ExpressionParser;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        CalculationMode<?> calcCalculationMode = switch (mode) {
            case "i" -> new CheckedIntMode();
            case "u" -> new IntMode();
            case "bi" -> new BigIntegerMode();
            case "t" -> new TruncatedMode();
            case "l" -> new LongMode();
            case "d" -> new DoubleMode();
            default -> throw new IllegalStateException("Inavalid mode: " + mode);
        };

        Operand expression1 = new ExpressionParser().parse(expression);
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    Number res = null;
                    try {
                        res = expression1.evaluate(i, j, k, calcCalculationMode);
                    } catch (EvaluateException ignored) {

                    }
                    result[i - x1][j - y1][k - z1] = res;
                }
            }
        }
        return result;
    }
}
