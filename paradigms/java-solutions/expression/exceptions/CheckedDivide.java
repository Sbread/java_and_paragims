package expression.exceptions;

import expression.Divide;
import expression.Operand;

public class CheckedDivide extends Divide {
    public CheckedDivide(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public int extraEvaluate(int first, int second) {
        if (second == 0) {
            throw new DBZException();
        }
        if (second == -1 && first == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return first / second;
    }
}
