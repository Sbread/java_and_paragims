package expression.generic;

import expression.exceptions.DBZException;
import expression.exceptions.EvaluateException;

public class LongMode implements CalculationMode<Long> {
    @Override
    public Long negate(Long arg) throws EvaluateException {
        return -arg;
    }

    @Override
    public Long add(Long first, Long second) throws EvaluateException {
        return first + second;
    }

    @Override
    public Long subtract(Long first, Long second) throws EvaluateException {
        return first - second;
    }

    @Override
    public Long multiply(Long first, Long second) throws EvaluateException {
        return first * second;
    }

    @Override
    public Long divide(Long first, Long second) throws EvaluateException {
        if (second == 0) {
            throw new DBZException();
        }
        return first / second;
    }

    @Override
    public Long max(Long first, Long second) throws EvaluateException {
        return Long.max(first, second);
    }

    @Override
    public Long min(Long first, Long second) throws EvaluateException {
        return Long.min(first, second);
    }

    @Override
    public Long count(Long arg) throws EvaluateException {
        return (long) Long.bitCount(arg);
    }

    @Override
    public Long getValue(Number arg) throws EvaluateException {
        return arg.longValue();
    }
}
