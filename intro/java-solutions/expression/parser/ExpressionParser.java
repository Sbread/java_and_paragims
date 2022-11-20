package expression.parser;

import expression.*;

public final class ExpressionParser extends BaseParser implements Parser {
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
    public TripleExpression parse(final String source) {
        return parse(new StringSource(source));
    }

    public TripleExpression parse(final CharSource source) {
        return new ExpressionParser(source).parseExpression();
    }

    public Operand parseExpression() {
        final Operand expression = parseOperationWithPriority(null, 4);
        skipWhitespace();
        if (eof()) {
            return expression;
        }
        throw error("End of Expession expected");
    }

    /* levels of priority
     * 4 - shifts
     * 3 - add subtract
     * 2 - multiply divide
     * 1 - unaryMinus l0 t0
     */
    private Operand parseOperationWithPriority(Operand firstOperand, int priority) {
        skipWhitespace();
        // firstOperand == null <==> UnaryOperation || parsing second operand
        char tag = '\0';
        if (firstOperand != null) {
            tag = switch (priority) {
                case 2 -> {
                    if (test('/') || test('*')){
                        yield take();
                    } else {
                        yield '\0';
                    }
                }
                case 3 -> {
                    if (test('-') || test('+')){
                        yield take();
                    } else {
                        yield '\0';
                    }
                }
                case 4 -> {
                    if (take('<')) {
                        if (take('<')) {
                            yield 'l'; // l - LeftShift
                        } else {
                            throw error("Operation < is invalid");
                        }
                    } else {
                        if (take('>')) {
                            if (take('>')) {
                                if (take('>')) {
                                    yield 'a'; // a - ArithmeticRightShift
                                } else {
                                    yield 'r'; // r - RightShift
                                }
                            } else {
                                throw error("Operation > is invalid");
                            }
                        }
                        yield '\0';
                    }
                }
                default -> '\0';
            };
        }
        skipWhitespace();
        Operand operand = switch (priority) {
            case 4 -> parseOperationWithPriority(null, 3);
            case 3 -> parseOperationWithPriority(null, 2);
            case 2 -> {
                if (firstOperand == null || tag != '\0') {
                    yield parseOperationWith1Priority();
                } else {
                    yield null;
                }
            }
            default -> null;
        };
        if (firstOperand == null && operand != null) {
            return parseOperationWithPriority(operand, priority);
        } else {
            if (priority == 2) {
                return switch (tag) {
                    case '*' -> parseOperationWithPriority(new Multiply(firstOperand, operand), 2);
                    case '/' -> parseOperationWithPriority(new Divide(firstOperand, operand), 2);
                    default -> firstOperand;
                };
            } else if (priority == 3) {
                return switch (tag) {
                    case '+' -> parseOperationWithPriority(new Add(firstOperand, operand), 3);
                    case '-' -> parseOperationWithPriority(new Subtract(firstOperand, operand), 3);
                    default -> firstOperand;
                };
            } else if (priority == 4) {
                return switch (tag) {
                    case 'l' -> parseOperationWithPriority(new LeftShift(firstOperand, operand), 4);
                    case 'r' -> parseOperationWithPriority(new RightShift(firstOperand, operand), 4);
                    case 'a' -> parseOperationWithPriority(new ArithmeticRightShift(firstOperand, operand), 4);
                    default -> {
                        take();
                        yield firstOperand;
                    }
                };
            }
            return null;
        }
    }

    private Operand parseOperationWith1Priority() {
        skipWhitespace();
        char minus = test('-') ? take() : '\0';
        if (between('0', '9')) {
            return parseConst(minus);
        } else if (minus != '\0') {
            return new UnaryMinus(parseOperationWith1Priority());
        } else if (take('l') && take('0')) {
            return new LeftZeroes(parseOperationWith1Priority());
        } else if (take('t') && take('0')) {
            return new RightZeroes(parseOperationWith1Priority());
        } else if (test('x') || test('y') || test('z')) {
            return new Variable(Character.toString(take()));
        } else if(take('(')) {
            return parseOperationWithPriority(null, 4);
        } else {
            return null;
        }
    }

    private Operand parseConst(char minus) {
        final StringBuilder sb = new StringBuilder();
        if (minus != '\0') {
            sb.append(minus);
        }
        takeInteger(sb);
        try {
            return new Const(Integer.parseInt(sb.toString()));
        } catch (NumberFormatException e) {
            throw error("Cannot parseInt, this is not number" + sb);
        }
    }

    private void takeInteger(final StringBuilder sb) {
        while (between('0', '9')) {
            sb.append(take());
        }
    }
}
