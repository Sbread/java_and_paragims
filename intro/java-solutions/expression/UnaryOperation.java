package expression;

import java.math.BigDecimal;

abstract public class UnaryOperation implements Operand {
    protected final Operand operand;
    private final int hashCode;

    public UnaryOperation(Operand operand) {
        this.operand = operand;
        hashCode = (operand.hashCode() * 16745 + getTag().hashCode() * 12495) % Integer.MAX_VALUE;
    }

    abstract public String getTag();

    abstract protected int extraEvaluate(int number);

    @Override
    public int evaluate(int x) {
        return extraEvaluate(operand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return extraEvaluate(operand.evaluate(x, y, z));
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
        return getTag() + "(" + operand + ")";
    }

    @Override
    public String toMiniString() {
        if (Math.abs(operand.getPriority()) > 1) {
            return getTag() + "(" + operand.toMiniString() + ")";
        } else {
            return getTag() + " " + operand.toMiniString();
        }
    }
}
