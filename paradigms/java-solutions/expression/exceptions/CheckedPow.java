package expression.exceptions;

import expression.BinaryOperation;
import expression.Operand;
import expression.Variable;

public class CheckedPow extends BinaryOperation {
    public CheckedPow(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public String getTag() {
        return "**";
    }

    @Override
    public String getSymbol() {
        return getTag();
    }

    @Override
    public int extraEvaluate(int first, int second) {
        if (second < 0 || (first == 0 && second == 0)) {
            throw new ScopeOfDefinitionException();
        }
        CheckedMultiply multiply = new CheckedMultiply(new Variable("x"), new Variable("y"));
        int result = 1;
        while (second != 0) {
            if (second % 2 == 1) {
                result = multiply.evaluate(result, first, 0);
            }
            second /= 2;
            if (second > 0) {
                first = multiply.evaluate(first, first, 0);
            }
        }
        return result;
    }

    @Override
    protected boolean isCommutative() {
        return false;
    }

    @Override
    protected boolean isAssociative() {
        return true;
    }
}
