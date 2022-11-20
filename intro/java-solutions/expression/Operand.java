package expression;

public interface Operand extends Expression, TripleExpression, BigDecimalExpression {
    int getPriority();
}
