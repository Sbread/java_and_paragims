package expression;

import java.math.BigDecimal;

public class Multiply extends BinaryOperation {
    public Multiply(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int getPriority() {
        return 2;
    }

    @Override
    protected String getTag() {
        return "*";
    }

    @Override
    int extraEvaluate(int first, int second) {
        return first * second;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return leftOperand.evaluate(x).multiply(rightOperand.evaluate(x));
    }
}
