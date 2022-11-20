package expression;

import expression.generic.CalculationMode;

public class Count extends UnaryOperation {
    public Count(Operand operand) {
        super(operand);
    }

    @Override
    public String getSymbol() {
        return getTag();
    }

    @Override
    public String getTag() {
        return "count";
    }

    @Override
    public int extraEvaluate(int number) {
        return Integer.bitCount(number);
    }

    @Override
    protected <T extends Number> T extraEvaluate(T arg, CalculationMode<T> calculationMode) {
        return calculationMode.count(arg);
    }
}
