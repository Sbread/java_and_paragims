package expression;

import expression.exceptions.EvaluateException;
import expression.generic.CalculationMode;

import java.util.HashMap;
import java.util.Map;

public interface Operand extends Expression, TripleExpression {

    String getTag();
    String getSymbol();

    Map<String, Integer> operationsPriority = new HashMap<>() {{
        put("const", -1);
        put("var", -1);
        put("negate", 0);
        put("abs", 0);
        put("**", 1);
        put("//", 1);
        put("*", 2);
        put("/", 2);
        put("+", 3);
        put("-", 3);
        put(">>>", 4);
        put(">>", 4);
        put("<<", 4);
    }};

    default <T extends Number> T evaluate(int x, int y, int z, CalculationMode<T> opCalculationMode) throws EvaluateException {
        return null;
    }
}
