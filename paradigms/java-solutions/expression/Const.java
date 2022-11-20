package expression;

import expression.exceptions.EvaluateException;
import expression.generic.CalculationMode;

public class Const implements Operand {
    private final int hashCode;
    private final Number cnst;

    public Const(int cnst) {
        this.cnst = cnst;
        this.hashCode = cnst;
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
            return hashCode() == obj.hashCode();
        }
    }

    @Override
    public int evaluate(int x) {
        return cnst.intValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return cnst.intValue();
    }

    @Override
    public <T extends Number> T evaluate(int x, int y, int z, CalculationMode<T> opCalculationMode) throws EvaluateException {
        return opCalculationMode.getValue(cnst);
    }

    @Override
    public String toString() {
        return cnst.toString();
    }

    @Override
    public String toMiniString() {
        return cnst.toString();
    }

    @Override
    public String getTag() {
        return "const";
    }

    @Override
    public String getSymbol() {
        return getTag();
    }
}
