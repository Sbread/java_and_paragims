package expression.exceptions;

public class OverflowException extends EvaluateException {
    public OverflowException() {
        super("overflow");
    }
}
