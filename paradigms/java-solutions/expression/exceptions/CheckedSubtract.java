package expression.exceptions;

import expression.Operand;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public int extraEvaluate(int first, int second) {
        if (first <= 0 && second > 0 && first < Integer.MIN_VALUE + second) {
            throw new OverflowException();
        }
        if (first >= 0 && second < 0 && first > Integer.MAX_VALUE + second) {
            throw new OverflowException();
        }
        return first - second;
    }
}
