package expression.exceptions;

public class DBZException extends EvaluateException {
    public DBZException() {
        super("Division by zero");
    }
}
