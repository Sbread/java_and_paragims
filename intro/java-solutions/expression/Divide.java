package expression;

import java.math.BigDecimal;

public class Divide extends BinaryOperation {
    public Divide(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int getPriority() {
        return -3;
    }

    @Override
    protected String getTag() {
        return "/";
    }

    @Override
    int extraEvaluate(int first, int second) {
        return first / second;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return leftOperand.evaluate(x).divide(rightOperand.evaluate(x));
    }
}
