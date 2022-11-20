package expression;

import java.math.BigDecimal;

import static java.lang.Math.abs;

abstract public class BinaryOperation implements Operand {
    protected final Operand leftOperand;
    protected final Operand rightOperand;
    private final int hashCode;

    protected BinaryOperation(Operand leftOperand, Operand rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.hashCode = leftOperand.hashCode() * 103487 + getTag().hashCode() * 99724 + rightOperand.hashCode() * 78964
                % Integer.MAX_VALUE;
    }

    abstract protected String getTag();

    abstract int extraEvaluate(int first, int second);

    @Override
    public int evaluate(int x) {
        return extraEvaluate(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return extraEvaluate(leftOperand.evaluate(x, y, z), rightOperand.evaluate(x, y, z));
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
            BinaryOperation operation = (BinaryOperation) obj;
            return leftOperand.equals(operation.leftOperand) && rightOperand.equals(operation.rightOperand);
        }
    }

    @Override
    public String toString() {
        return "(" + leftOperand + " " + getTag() + " " + rightOperand + ")";
    }

    @Override
    public String toMiniString() {
        String s = "";
        if (abs(leftOperand.getPriority()) > abs(getPriority()) + 1) {
            s += "(" + leftOperand.toMiniString() + ")";
        } else {
            s += leftOperand.toMiniString();
        }
        s += " " + getTag() + " ";
        if ((abs(abs(rightOperand.getPriority()) - abs(getPriority())) < 2 && getPriority() < 0)
                || abs(rightOperand.getPriority()) > abs(getPriority())) {
            s += "(" + rightOperand.toMiniString() + ")";
        } else {
            s += rightOperand.toMiniString();
        }
        return s;
    }
}
