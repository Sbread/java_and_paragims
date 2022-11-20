package expression.exceptions;

public class ScopeOfDefinitionException extends EvaluateException {
    public ScopeOfDefinitionException() {
        super("argument is not in scope of function definition");
    }
}
