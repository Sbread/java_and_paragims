package expression.exceptions;

import expression.Operand;
import expression.UnaryOperation;

public class CheckedAbs extends UnaryOperation {
    public CheckedAbs(Operand operand) {
        super(operand);
    }

    @Override
    public String getTag() {
        return "abs";
    }

    @Override
    public String getSymbol() {
        return getTag();
    }

    @Override
    public int extraEvaluate(int number) {
        if (number == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return number >= 0 ? number : -number;
    }
}
