package expression;

import expression.generic.CalculationMode;

public class Max extends BinaryOperation {
    public Max(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected boolean isCommutative() {
        return true;
    }

    @Override
    protected boolean isAssociative() {
        return false;
    }

    @Override
    protected int extraEvaluate(int first, int second) {
        return Math.max(first, second);
    }

    @Override
    protected <T extends Number> T extraEvaluate(T first, T second, CalculationMode<T> calculationMode) {
        return calculationMode.max(first, second);
    }

    @Override
    public String getTag() {
        return "max";
    }

    @Override
    public String getSymbol() {
        return getTag();
    }
}
