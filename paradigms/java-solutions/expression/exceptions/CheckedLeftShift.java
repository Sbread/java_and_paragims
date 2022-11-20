package expression.exceptions;

import expression.BinaryOperation;
import expression.Operand;

public class CheckedLeftShift extends BinaryOperation {
    public CheckedLeftShift(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public String getTag() {
        return "<<";
    }

    @Override
    public String getSymbol() {
        return getTag();
    }

    @Override
    public int extraEvaluate(int first, int second) {
//        if (second < 0) {
//            throw new ScopeOfDefinitionException();
//        }
        return first << second;
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
