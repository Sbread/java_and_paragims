package expression.exceptions;

public class UnknownOperationException extends ParseException {
    public UnknownOperationException() {
        super("unknown operation");
    }
}
