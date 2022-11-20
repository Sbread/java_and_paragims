package expression.exceptions;

public class InvalidExpressionException extends ParseException {
    public InvalidExpressionException(String message) {
        super(message);
    }
}
