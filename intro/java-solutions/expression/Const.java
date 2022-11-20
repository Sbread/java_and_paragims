package expression;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Const implements Operand {
    private final int hashCode;
    private final Number cnst;

    public Const(int cnst) {
        this.cnst = cnst;
        this.hashCode = cnst;
    }

    public Const(BigDecimal cnstBig) {
        this.cnst = cnstBig;
        this.hashCode = cnstBig.intValue();
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public int getPriority() {
        return 0;
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
    public BigDecimal evaluate(BigDecimal x) {
        return (BigDecimal) cnst;
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
    public String toString() {
        return cnst.toString();
    }

    @Override
    public String toMiniString() {
        return cnst.toString();
    }
}
