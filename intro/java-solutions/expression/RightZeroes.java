package expression;

import java.math.BigDecimal;

public class RightZeroes extends UnaryOperation {
    public RightZeroes(Operand operand) {
        super(operand);
    }

    @Override
    public String getTag() {
        return "t0";
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    protected int extraEvaluate(int number) {
        return Integer.numberOfTrailingZeros(number);
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return null;
    }
}
