package expression;

import expression.generic.CalculationMode;

public class Negate extends UnaryOperation {
    public Negate(Operand operand) {
        super(operand);
    }

    @Override
    public String getTag() {
        return "negate";
    }

    @Override
    public String getSymbol() {
        return "-";
    }

    @Override
    public int extraEvaluate(int number) {
        return -number;
    }

    @Override
    protected <T extends Number> T extraEvaluate(T arg, CalculationMode<T> calculationMode) {
        return calculationMode.negate(arg);
    }
}
