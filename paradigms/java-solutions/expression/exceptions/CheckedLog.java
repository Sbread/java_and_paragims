package expression.exceptions;

import expression.BinaryOperation;
import expression.Operand;

public class CheckedLog extends BinaryOperation {
    public CheckedLog(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public String getTag() {
        return "//";
    }

    @Override
    public String getSymbol() {
        return getTag();
    }

    @Override
    public int extraEvaluate(int first, int second) {
        if (first <= 0 || second < 2) {
            throw new ScopeOfDefinitionException();
        }
        int result = 0;
        while (first >= second) {
            first /= second;
            result++;
        }
        return result;
    }

    @Override
    protected boolean isCommutative() {
        return false;
    }

    @Override
    protected boolean isAssociative() {
        return false;
    }
}
