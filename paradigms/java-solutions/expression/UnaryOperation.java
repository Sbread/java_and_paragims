package expression;

import expression.generic.CalculationMode;

abstract public class UnaryOperation implements Operand {
    protected final Operand operand;
    private final int hashCode;

    public UnaryOperation(Operand operand) {
        this.operand = operand;
        hashCode = (operand.hashCode() * 16745 + getTag().hashCode() * 12495) % Integer.MAX_VALUE;
    }

    abstract public String getTag();

    abstract public int extraEvaluate(int number);

    protected <T extends Number> T extraEvaluate(T arg, CalculationMode<T> calculationMode) {
        return null;
    }

    @Override
    public int evaluate(int x) {
        return extraEvaluate(operand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return extraEvaluate(operand.evaluate(x, y, z));
    }

    @Override
    public <T extends Number> T evaluate(int x, int y, int z, CalculationMode<T> calculationMode) {
        return extraEvaluate(operand.evaluate(x, y, z, calculationMode), calculationMode);
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
            UnaryOperation operation = (UnaryOperation) obj;
            return operand.equals(operation);
        }
    }

    @Override
    public String toString() {
        return getSymbol() + "(" + operand + ")";
    }

    @Override
    public String toMiniString() {
        if (operationsPriority.get(operand.getTag()) > 0) {
            return getSymbol() + "(" + operand.toMiniString() + ")";
        } else {
            return getSymbol() + " " + operand.toMiniString();
        }
    }
}
