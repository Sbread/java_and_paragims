package expression.generic;

import expression.exceptions.DBZException;
import expression.exceptions.EvaluateException;

import java.math.BigInteger;

public class BigIntegerMode implements CalculationMode<BigInteger> {
    @Override
    public BigInteger negate(BigInteger arg) throws EvaluateException {
        return arg.negate();
    }

    @Override
    public BigInteger add(BigInteger first, BigInteger second) throws EvaluateException {
        return first.add(second);
    }

    @Override
    public BigInteger subtract(BigInteger first, BigInteger second) throws EvaluateException {
        return first.subtract(second);
    }

    @Override
    public BigInteger multiply(BigInteger first, BigInteger second) throws EvaluateException {
        return first.multiply(second);
    }

    @Override
    public BigInteger divide(BigInteger first, BigInteger second) throws EvaluateException {
        if (second.equals(BigInteger.ZERO)) {
            throw new DBZException();
        }
        return first.divide(second);
    }

    @Override
    public BigInteger max(BigInteger first, BigInteger second) throws EvaluateException {
        return first.max(second);
    }

    @Override
    public BigInteger min(BigInteger first, BigInteger second) throws EvaluateException {
        return first.min(second);
    }

    @Override
    public BigInteger count(BigInteger arg) throws EvaluateException {
        return BigInteger.valueOf(arg.bitCount());
    }

    @Override
    public BigInteger getValue(Number arg) throws EvaluateException {
        return new BigInteger(arg.toString());
    }
}
