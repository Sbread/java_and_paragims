package expression;

import expression.generic.CalculationMode;

public class Divide extends BinaryOperation {
    public Divide(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected boolean isCommutative() {
        return false;
    }

    @Override
    protected boolean isAssociative() {
        return false;
    }

    @Override
    public String getTag() {
        return "/";
    }

    @Override
    public String getSymbol() {
        return getTag();
    }

    @Override
    public int extraEvaluate(int first, int second) {
        return first / second;
    }

    @Override
    protected <T extends Number> T extraEvaluate(T first, T second, CalculationMode<T> calculationMode) {
        return calculationMode.divide(first, second);
    }
}
