package expression;

import java.math.BigDecimal;

public class LeftZeroes extends UnaryOperation {
    public LeftZeroes(Operand operand) {
        super(operand);
    }

    @Override
    public String getTag() {
        return "l0";
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    protected int extraEvaluate(int number) {
        return Integer.numberOfLeadingZeros(number);
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return null;
    }
}
