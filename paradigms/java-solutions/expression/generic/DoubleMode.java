package expression.generic;

import expression.exceptions.EvaluateException;

public class DoubleMode implements CalculationMode<Double> {
    @Override
    public Double negate(Double arg) throws EvaluateException {
        return -arg;
    }

    @Override
    public Double add(Double first, Double second) throws EvaluateException {
        return first + second;
    }

    @Override
    public Double subtract(Double first, Double second) throws EvaluateException {
        return first - second;
    }

    @Override
    public Double multiply(Double first, Double second) throws EvaluateException {
        return first * second;
    }

    @Override
    public Double divide(Double first, Double second) throws EvaluateException {
        return first / second;
    }

    @Override
    public Double max(Double first, Double second) throws EvaluateException {
        return Double.max(first, second);
    }

    @Override
    public Double min(Double first, Double second) throws EvaluateException {
        return Double.min(first, second);
    }

    @Override
    public Double count(Double arg) throws EvaluateException {
        return (double) new LongMode().count(Double.doubleToLongBits(arg));
    }

    @Override
    public Double getValue(Number arg) throws EvaluateException {
        return arg.doubleValue();
    }
}
