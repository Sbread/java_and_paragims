package expression;

import java.math.BigDecimal;

public class UnaryMinus extends UnaryOperation {
    public UnaryMinus(Operand operand) {
        super(operand);
    }

    @Override
    public String getTag() {
        return "-";
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    protected int extraEvaluate(int number) {
        return -number;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return operand.evaluate(x).multiply(BigDecimal.valueOf(-1));
    }
}
