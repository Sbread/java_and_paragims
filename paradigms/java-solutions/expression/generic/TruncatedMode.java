package expression.generic;

import expression.exceptions.DBZException;
import expression.exceptions.EvaluateException;

public class TruncatedMode implements CalculationMode<Integer> {

    private Integer round(Integer arg) throws EvaluateException {
        arg -= arg % 10;
        return arg;
    }

    @Override
    public Integer negate(Integer arg) throws EvaluateException {
        return -round(arg);
    }

    @Override
    public Integer add(Integer first, Integer second) throws EvaluateException {
        return round(round(first) + round(second));
    }

    @Override
    public Integer subtract(Integer first, Integer second) throws EvaluateException {
        return round(round(first) - round(second));
    }

    @Override
    public Integer multiply(Integer first, Integer second) throws EvaluateException {
        return round(round(first) * round(second));
    }

    @Override
    public Integer divide(Integer first, Integer second) throws EvaluateException {
        second = round(second);
        if (second == 0) {
            throw new DBZException();
        }
        return round((round(first) / second));
    }

    @Override
    public Integer max(Integer first, Integer second) throws EvaluateException {
        return round(Integer.max(first, second));
    }

    @Override
    public Integer min(Integer first, Integer second) throws EvaluateException {
        return round(Integer.min(first, second));
    }

    @Override
    public Integer count(Integer arg) throws EvaluateException {
        arg = round(arg);
        return round(Integer.bitCount(arg));
    }

    @Override
    public Integer getValue(Number arg) throws EvaluateException {
        return round(arg.intValue());
    }
}
