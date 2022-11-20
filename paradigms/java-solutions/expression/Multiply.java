package expression;

import expression.generic.CalculationMode;

public class Multiply extends BinaryOperation {
    public Multiply(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected boolean isCommutative() {
        return true;
    }

    @Override
    protected boolean isAssociative() {
        return true;
    }

    @Override
    public String getTag() {
        return "*";
    }

    @Override
    public String getSymbol() {
        return getTag();
    }

    @Override
    public int extraEvaluate(int first, int second) {
        return first * second;
    }

    @Override
    protected <T extends Number> T extraEvaluate(T first, T second, CalculationMode<T> calculationMode) {
        return calculationMode.multiply(first, second);
    }
}
