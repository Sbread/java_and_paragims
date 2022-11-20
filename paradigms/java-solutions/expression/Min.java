package expression;

import expression.generic.CalculationMode;

public class Min extends BinaryOperation {
    public Min(Operand leftOperand, Operand rightOperand) {
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
        return Math.min(first, second);
    }

    @Override
    protected <T extends Number> T extraEvaluate(T first, T second, CalculationMode<T> calculationMode) {
        return calculationMode.min(first, second);
    }

    @Override
    public String getTag() {
        return "min";
    }

    @Override
    public String getSymbol() {
        return getTag();
    }
}
