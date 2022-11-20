package expression;

import java.math.BigDecimal;

public class LeftShift extends BinaryOperation {
    public LeftShift(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getTag() {
        return "<<";
    }

    @Override
    public int getPriority() {
        return -7;
    }

    @Override
    int extraEvaluate(int first, int second) {
        return first << second;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return null;
    }
}
