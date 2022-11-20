package expression.generic;

import expression.exceptions.DBZException;
import expression.exceptions.EvaluateException;

public class IntMode implements CalculationMode<Integer> {
    @Override
    public Integer add(Integer first, Integer second) throws EvaluateException {
        return first + second;
    }

    @Override
    public Integer subtract(Integer first, Integer second) throws EvaluateException {
        return first - second;
    }

    @Override
    public Integer multiply(Integer first, Integer second) throws EvaluateException {
        return first * second;
    }

    @Override
    public Integer divide(Integer first, Integer second) throws EvaluateException {
        if (second == 0) {
            throw new DBZException();
        }
        return first / second;
    }

    @Override
    public Integer max(Integer first, Integer second) throws EvaluateException {
        return Integer.max(first, second);
    }

    @Override
    public Integer min(Integer first, Integer second) throws EvaluateException {
        return Integer.min(first, second);
    }

    @Override
    public Integer negate(Integer integer) throws EvaluateException {
        return -integer;
    }

    @Override
    public Integer count(Integer integer) throws EvaluateException {
        return Integer.bitCount(integer);
    }

    @Override
    public Integer getValue(Number arg) throws EvaluateException {
        return arg.intValue();
    }
}
