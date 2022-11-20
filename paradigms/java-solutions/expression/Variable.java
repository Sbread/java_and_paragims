package expression;

import expression.exceptions.EvaluateException;
import expression.generic.CalculationMode;

public class Variable implements Operand {
    private final String variable;
    private final int hashCode;

    public Variable(String variable) {
        this.variable = variable;
        this.hashCode = variable.hashCode();
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            return hashCode() == obj.hashCode();
        }
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (variable) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new AssertionError("Cannot initialize variable");
        };
    }

    @Override
    public <T extends Number> T evaluate(int x, int y, int z, CalculationMode<T> opCalculationMode) throws EvaluateException {
        return switch (variable) {
            case "x" -> opCalculationMode.getValue(x);
            case "y" -> opCalculationMode.getValue(y);
            case "z" -> opCalculationMode.getValue(z);
            default -> null;
        };
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public String toMiniString() {
        return variable;
    }

    @Override
    public String getTag() {
        return "var";
    }

    @Override
    public String getSymbol() {
        return getTag();
    }
}
