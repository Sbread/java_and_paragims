package expression;

import expression.generic.CalculationMode;

abstract public class BinaryOperation implements Operand {
    protected final Operand leftOperand;
    protected final Operand rightOperand;
    private final int hashCode;

    public BinaryOperation(Operand leftOperand, Operand rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.hashCode = leftOperand.hashCode() * 103487 + getTag().hashCode() * 99724 + rightOperand.hashCode() * 78964
                % Integer.MAX_VALUE;
    }

    abstract protected boolean isCommutative();

    abstract protected boolean isAssociative();

    abstract protected int extraEvaluate(int first, int second);

    protected <T extends Number> T extraEvaluate(T first, T second, CalculationMode<T> calculationMode) {
        return null;
    }

    @Override
    public int evaluate(int x) {
        return extraEvaluate(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return extraEvaluate(leftOperand.evaluate(x, y, z), rightOperand.evaluate(x, y, z));
    }

    @Override
    public <T extends Number> T evaluate(int x, int y, int z, CalculationMode<T> opCalculationMode) {
        return extraEvaluate(leftOperand.evaluate(x, y, z, opCalculationMode), rightOperand.evaluate(x, y, z, opCalculationMode), opCalculationMode);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            BinaryOperation operation = (BinaryOperation) obj;
            return leftOperand.equals(operation.leftOperand) && rightOperand.equals(operation.rightOperand);
        }
    }

    @Override
    public String toString() {
        return "(" + leftOperand + " " + getTag() + " " + rightOperand + ")";
    }

    @Override
    public String toMiniString() {
        String s = "";
        int firstOpPrority = operationsPriority.get(leftOperand.getTag());
        int secondOpPriority = operationsPriority.get(rightOperand.getTag());
        int priority = operationsPriority.get(getTag());
        if (firstOpPrority > priority) {
            s += "(" + leftOperand.toMiniString() + ")";
        } else {
            s += leftOperand.toMiniString();
        }
        s += " " + getTag() + " ";
        int rightDifPriority = priority - secondOpPriority;
        if (rightDifPriority < 0 || (secondOpPriority > 0 && rightDifPriority == 0
            && (!((BinaryOperation)rightOperand).isAssociative()
                && !rightOperand.getTag().equals(getTag()) || !isCommutative()))) {
            s += "(" + rightOperand.toMiniString() + ")";
        } else {
            s += rightOperand.toMiniString();
        }
        return s;
    }
}
