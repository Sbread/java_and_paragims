package expression.exceptions;

public class WBSException extends ParseException {
    public WBSException() {
        super("wrong brackets sequence");
    }
}
