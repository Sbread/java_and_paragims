package expression.generic;

import expression.exceptions.DBZException;
import expression.exceptions.EvaluateException;
import expression.exceptions.OverflowException;

public class CheckedIntMode extends IntMode {
    @Override
    public Integer add(Integer first, Integer second) throws EvaluateException {
        if (first > 0 && second > 0 && first > Integer.MAX_VALUE - second) {
            throw new OverflowException();
        }
        if (first < 0 && second < 0 && first < Integer.MIN_VALUE - second) {
            throw new OverflowException();
        }
        return super.add(first, second);
    }

    @Override
    public Integer subtract(Integer first, Integer second) throws EvaluateException {
        if (first <= 0 && second > 0 && first < Integer.MIN_VALUE + second) {
            throw new OverflowException();
        }
        if (first >= 0 && second < 0 && first > Integer.MAX_VALUE + second) {
            throw new OverflowException();
        }
        return super.subtract(first, second);
    }

    @Override
    public Integer multiply(Integer first, Integer second) throws EvaluateException {
        if (first > 0 && second > 0 && first > Integer.MAX_VALUE / second) {
            throw new OverflowException();
        }
        if (first > 0 && second < 0 && second < Integer.MIN_VALUE / first) {
            throw new OverflowException();
        }
        if (first < 0 && second > 0 && first < Integer.MIN_VALUE / second) {
            throw new OverflowException();
        }
        if (first < 0 && second < 0 && first < Integer.MAX_VALUE / second) {
            throw new OverflowException();
        }
        return super.multiply(first, second);
    }

    @Override
    public Integer divide(Integer first, Integer second) throws EvaluateException {
        if (second == 0) {
            throw new DBZException();
        }
        if (second == -1 && first == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return super.divide(first, second);
    }

    @Override
    public Integer negate(Integer integer) throws EvaluateException {
        if (integer == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return super.negate(integer);
    }
}
