package expression;

import java.math.BigDecimal;

public class Subtract extends BinaryOperation {

    public Subtract(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int getPriority() {
        return -5;
    }

    @Override
    protected String getTag() {
        return "-";
    }

    @Override
    int extraEvaluate(int first, int second) {
        return first - second;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return leftOperand.evaluate(x).subtract(rightOperand.evaluate(x));
    }
}
