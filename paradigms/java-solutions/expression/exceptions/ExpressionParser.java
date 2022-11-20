package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.CharSource;
import expression.parser.StringSource;

import static expression.Operand.operationsPriority;

public final class ExpressionParser extends BaseParser implements TripleParser {

    private int BSbalance = 0;

    public ExpressionParser(CharSource source) {
        super(source);
    }

    public ExpressionParser() {
        super();
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(current())) {
            take();
        }
    }

    @Override
    public Operand parse(final String source) throws ParseException {
        Operand result;
        result = parse(new StringSource(source));
//        try {
//            result = parse(new StringSource(source));
//        }
//        catch (ParseException e) {
//            throw error("Parse error: " + e.getMessage());
//        }
        return result;
    }

    public Operand parse(final CharSource source) throws ParseException {
        return new ExpressionParser(source).parseExpression();
    }

    public Operand parseExpression() throws ParseException {
        final Operand expression = parseOperationWithPriority(null, 4);
        skipWhitespace();
        if (BSbalance != 0) {
            throw new WBSException();
        }
        if (expression == null) {
            throw new InvalidExpressionException("This is not an expression");
        }
        if (eof()) {
            return expression;
        }
        throw error("End of Expession expected");
    }

    private Operand parseOperationWithPriority(Operand firstOperand, int priority) throws ParseException{
        skipWhitespace();
        // firstOperand == null <==> UnaryOperation || parsing second operand
        String operation = "";
        if (firstOperand != null) {
            if (priority == 4) {
                if (take('>')) {
                    if (take('>')) {
                        if (take('>')) {
                            operation = ">>>";
                        } else {
                            operation = ">>";
                        }
                    } else {
                        throw new InvalidOperationException("rights shifts");
                    }
                } else if (take('<')) {
                    if (take('<')) {
                        operation = "<<";
                    } else {
                        throw new InvalidOperationException("left shift");
                    }
                } else if (take('m')) {
                    if (take('i') && take('n')) {
                        operation = "min";
                    }
                    else if (take('a') && take('x')) {
                        operation = "max";
                    }
                    else {
                        throw new InvalidOperationException("min/max");
                    }
                }
            } else if (priority == 3) {
                if (take('+')) {
                    operation = "+";
                } else if (take('-')) {
                    operation = "-";
                }
            } else if (priority <= 2) {
                if (take('*')) {
                    if (take('*')) {
                        operation = "**";
                    } else if (priority == 2){
                        operation = "*";
                    } else {
                        takePrev();
                    }
                } else if (take('/')) {
                    if (take('/')) {
                        operation = "//";
                    } else if (priority == 2){
                        operation = "/";
                    } else {
                        takePrev();
                    }
                }
            }
        }
        priority = operationsPriority.getOrDefault(operation, priority);
        skipWhitespace();
        Operand operand;
        if (priority > 1 && (firstOperand == null || !operation.isEmpty())) {
            operand = parseOperationWithPriority(null, priority - 1);
        } else {
            if (firstOperand == null || !operation.isEmpty()) {
                operand = parseUnaryOperationWith1Priority();
            } else {
                operand = null;
            }
        }
        if (firstOperand == null && operand != null) {
            return parseOperationWithPriority(operand, priority);
        } else if (operand != null) {
            return switch (operation) {
                case "**" -> parseOperationWithPriority(new CheckedPow(firstOperand, operand), priority);
                case "//" -> parseOperationWithPriority(new CheckedLog(firstOperand, operand), priority);
                case "*" -> parseOperationWithPriority(new CheckedMultiply(firstOperand, operand), priority);
                case "/" -> parseOperationWithPriority(new CheckedDivide(firstOperand, operand), priority);
                case "+" -> parseOperationWithPriority(new CheckedAdd(firstOperand, operand), priority);
                case "-" -> parseOperationWithPriority(new CheckedSubtract(firstOperand, operand), priority);
                case "<<" -> parseOperationWithPriority(new CheckedLeftShift(firstOperand, operand), priority);
                case ">>" -> parseOperationWithPriority(new CheckedRightShift(firstOperand, operand), priority);
                case ">>>" -> parseOperationWithPriority(new CheckedArithmeticRightShift(firstOperand, operand), priority);
                case "min" -> parseOperationWithPriority(new Min(firstOperand, operand), priority);
                case "max" -> parseOperationWithPriority(new Max(firstOperand, operand), priority);
                default -> throw new UnknownOperationException();
            };
        } else if (!operation.isEmpty()) {
            throw new InvalidExpressionException("Unexpected end of expression");
        } else {
            if (priority == 4 && take(')')) {
                BSbalance--;
            }
            return firstOperand;
        }
    }

    private Operand parseUnaryOperationWith1Priority() throws ParseException{
        skipWhitespace();
        char minus = test('-') ? take() : '\0';
        if (between('0', '9')) {
            return parseConst(minus);
        } else if (minus != '\0') {
            return new CheckedNegate(parseUnaryOperationWith1Priority());
        } else if (test('x') || test('y') || test('z')) {
            return new Variable(Character.toString(take()));
        } else if(take('c') && take('o')
                && take('u') && take('n') &&take('t')) {
            return new Count(parseUnaryOperationWith1Priority());
        } else if(take('(')) {
            BSbalance++;
            return parseOperationWithPriority(null, 4);
        } else if (take('a')) {
            if (take('b') && take('s') && !Character.isLetterOrDigit(current())) {
                return new CheckedAbs(parseUnaryOperationWith1Priority());
            } else {
                throw new InvalidOperationException("abs");
            }
        } else if (eof() || test(')')) {
            return null;
        } else {
            throw new UnknownOperationException();
        }
    }

    private Operand parseConst(char minus) throws ParseException {
        final StringBuilder sb = new StringBuilder();
        if (minus != '\0') {
            sb.append(minus);
        }
        takeInteger(sb);
        try {
            return new Const(Integer.parseInt(sb.toString()));
        } catch (NumberFormatException e) {
            throw new InvalidConstException("Cannot parseInt, this is not int number" + sb);
        }
    }

    private void takeInteger(final StringBuilder sb) {
        while (between('0', '9')) {
            sb.append(take());
        }
    }
}
