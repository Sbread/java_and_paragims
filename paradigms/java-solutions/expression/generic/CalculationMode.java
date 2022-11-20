package expression.generic;

import expression.exceptions.EvaluateException;

public interface CalculationMode<T extends Number> {
    T negate(T arg) throws EvaluateException;
    T add(T first, T second) throws EvaluateException;
    T subtract(T first, T second) throws EvaluateException;
    T multiply(T first, T second) throws EvaluateException;
    T divide(T first, T second) throws EvaluateException;
    T max(T first, T second) throws EvaluateException;
    T min(T first, T second) throws EvaluateException;
    T count(T arg) throws EvaluateException;
    T getValue(Number arg) throws EvaluateException;
}
