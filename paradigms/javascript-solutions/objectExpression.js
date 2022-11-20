const OperationPrototype = {
    toString: function () {
        let res = "";
        for (let operand of this.getOperands()) {
            res += operand.toString() + " ";
        }
        res += this.getTag;
        return res;
    },
    evaluate: function (x, y, z) {
        return this.getOp(...this.getOperands().map(func => func.evaluate(x, y, z)));
    },
    diff: function (x) {
        return this.getDiffMode(...this.getOperands().map(operand => [operand, operand.diff(x)]));
    },
    prefixPostfix : function (mode) {
        let result = mode === "prefix" ? "(" + this.getTag : "(";
        if (this.getOperands().length === 0) {
            result += " ";
        } else {
            for (let operand of this.getOperands()) {
                if (mode === "prefix") {
                    result += " " + operand.prefix();
                } else if (mode === "postfix") {
                    result += operand.postfix() + " ";
                }
            }
        }
        result += mode === "prefix" ? ")" : this.getTag + ")";
        return result;
    },
    prefix: function () {
        return this.prefixPostfix("prefix");
    },
    postfix : function () {
        return this.prefixPostfix("postfix");
    }
}

function Operation(...operands) {
    this.getOperands = function () {
        return operands;
    };
}

Operation.prototype = Object.create(OperationPrototype);

let FUNCTIONS = {};

function createOperation(tag, op, diffMode) {
    let temp = function (...operands) {
        Operation.call(this, ...operands);
    };
    temp.prototype = Object.create(Operation.prototype);
    temp.arity = op.length;
    temp.prototype.getTag = tag;
    temp.prototype.getOp = op;
    temp.prototype.getDiffMode = diffMode;
    FUNCTIONS[tag] = temp;
    return temp;
}

const Negate = createOperation("negate", (a) => -a,
    ([a, da]) => new Multiply(Const.MINUS_ONE, da));

const Add = createOperation("+", (a, b) => a + b,
    ([a, da], [b, db]) => new Add(da, db));

const Subtract = createOperation("-", (a, b) => a - b,
    ([a, da], [b, db]) => new Subtract(da, db));

const Divide = createOperation("/", (a, b) => a / b,
    ([a, da], [b, db]) => new Divide(new Subtract(new Multiply(da, b),
        new Multiply(a, db)), new Multiply(b, b)));

const Multiply = createOperation("*", (a, b) => a * b,
    ([a, da], [b, db]) => new Add(new Multiply(da, b), new Multiply(a, db)));

const Exp = createOperation("exp", a => Math.pow(Math.E, a),
    ([a, da]) => new Multiply(da, new Exp(a)));

const Square = createOperation("square", a => a * a,
    ([a, da]) => new Multiply(new Multiply(Const.TWO, a), da));

const Gauss = createOperation("gauss",
    (a, b, c, x) => a * Math.pow(Math.E, -1 * Math.pow(x - b, 2) / (2 * Math.pow(c, 2))),
    ([a, da], [b, db], [c, dc], [x, dx]) => new Multiply(new Exp(new Divide(
        new Negate(
            new Square(
                new Subtract(x, b),
            )
        ),
        new Multiply(Const.TWO,
            new Square(c))
    )), new Subtract(da, new Multiply(a,
        new Divide(
            new Subtract(
                new Multiply(
                    new Multiply(Const.FOUR, new Multiply(new Subtract(dx, db),
                        new Subtract(x, b))), new Square(c)),
                new Multiply(
                    new Square(new Subtract(x, b)),
                    new Multiply(Const.FOUR, new Multiply(c, dc))
                )
            ),
            new Multiply(Const.FOUR, new Square(new Square(c)))
        )))));

const Sum = createOperation("sum", (...args) => args.reduce((res, x) => res + x, 0),
    (...args) => {
        let res = [];
        for (let i = 0; i < args.length; i++) {
            res[i] = args[i][1];
        }
        return new Sum(res);
    })

function sumexpDiff(...args) {
    let res = [];
    for (let i = 0; i < args.length; i++) {
        res.push(new Multiply(new Exp(args[i][0]), args[i][1]));
    }
    return new Sum(...res);
}

const Sumexp = createOperation("sumexp", (...args) => args.reduce((res, x) => res + Math.exp(x), 0),
    (...args) => sumexpDiff(...args))

const Softmax = createOperation("softmax",
    (...args) => Math.exp(args[0]) / args.reduce((res, x) => res + Math.exp(x), 0),
    (...args) => {
    let exps = [];
    for (let i = 0; i < args.length; i++) {
        exps.push(args[i][0]);
    }
    return new Divide(
        new Subtract(
            new Multiply(
                new Multiply(new Exp(args[0][0]), args[0][1]),
                new Sumexp(...exps)
            ),
            new Multiply(new Exp(args[0][0]), sumexpDiff(...args))
        ),
        new Square(new Sumexp(...exps))
    )
})

ConstVarPrototype = {
    allString : function () {
        return this.getValue().toString();
    },

    toString: function () {
        return this.getValue().toString();
    },
    evaluate: function (...args) {
        let val = this.getValue();
        return val in Variables ? args[Variables[val]] : val;
    },
    diff: function (x) {
        return this.getValue() === x ? Const.ONE : Const.ZERO;
    },
    prefix : function () {
        return this.getValue().toString();
    },
    postfix : function () {
        return this.getValue().toString();
    }
}

function Const(value) {
    this.value = value;
    this.getValue = function () {
        return this.value;
    }
}

Const.prototype = Object.create(ConstVarPrototype);

Const.MINUS_ONE = new Const(-1);
Const.ONE = new Const(1);
Const.ZERO = new Const(0);
Const.TWO = new Const(2);
Const.FOUR = new Const(4);

const Variables = {
    "x": 0,
    "y": 1,
    "z": 2
}

function Variable(name) {
    this.value = name;
    this.getValue = function () {
        return this.value;
    }
}

Variable.prototype = Object.create(ConstVarPrototype);

const testConst = arg => /^-?\d+$/.test(arg);

const parse = expression => {
    let stack = [];
    expression.split(/\s/).filter(part => part.length > 0).map(operand => {
        if (operand in Variables) {
            stack.push(new Variable(operand));
        } else if (operand in FUNCTIONS) {
            stack.push(new FUNCTIONS[operand](...stack.splice(-FUNCTIONS[operand].arity)));
        } else if (testConst(operand)) {
            stack.push(new Const(parseInt(operand)));
        }
    });
    return stack.pop();
}

function createException(name, message) {
    let myException = function (...args) {
        this.message = message(...args);
    };
    myException.prototype = Object.create(Error.prototype);
    myException.prototype.name = name;
    return myException;
}

const WBSException = createException("WBSException",
    (pos) => "Wrong bracket sequence at pos:" + (pos + 1));

const UnknownTokenException = createException("UnknownTokenException",
    (token, pos) => "Unknown token: " + token + " ar pos: " + (pos + 1));

const WrongEndException = createException("WrongEndException",
    (pos) => "Expected end of expression at pos: " + (pos + 1));

const ArityException = createException("ArityException",
    (op, arity, pos) => "For operation: " + op + " expected arity: " + FUNCTIONS[op].arity
        + " real: " + arity + " at: " + (pos + 1));

const InvalidOperationException = createException("InvalidOperationException",
    (pos, op) => "Invalid operation: " + op + " at pos: " + (pos + 1));

function parsePostfixPrefix (source, begin, end, next, reverse, open, close) {
    let pos = begin;

    function skipWhitespaces() {
        while (pos !== end && /\s/.test(source.charAt(pos))) {
            pos = next(pos);
        }
    }

    let currOp = undefined;

    function nextToken() {
        if (currOp !== undefined) {
            let resOp = currOp;
            currOp = undefined;
            return resOp;
        }
        skipWhitespaces();
        let start = pos;
        let char = source.charAt(pos);
        if ("()".includes(char)) {
            pos = next(pos);
            return char;
        }
        while (pos !== end && !/[\s()]/.test(source.charAt(pos))) {
            pos = next(pos);
        }
        return reverse ? source.substring(pos + 1, start + 1) : source.substring(start, pos);
    }

    function getOperation() {
        let func = nextToken();
        if (!(func in FUNCTIONS)) {
            throw new InvalidOperationException(pos - func.length, func);
        }
        return func;
    }

    function getArguments() {
        let arguments = [];
        while (pos !== end) {
            let got = convertToken(nextToken());
            if (got === close) {
                break;
            }
            arguments.push(got);
        }
        if (reverse) {
            arguments.reverse();
        }
        return arguments;
    }

    function convertToken(token) {
        if (token === open) {
            let op = getOperation();
            let arguments = getArguments();
            let start = pos;
            let arity = FUNCTIONS[op].arity;

            if (arguments.length !== arity && arity > 0) {
                throw new ArityException(op, arguments.length, start);
            }
            skipWhitespaces();
            if (nextToken() !== close) {
                throw new WBSException(pos, close);
            }
            return new FUNCTIONS[op](...arguments);
        } else if (token === close) {
            currOp = close;
            return close;
        } else if (testConst(token)) {
            return new Const(parseInt(token));
        } else if (token in Variables) {
            return new Variable(token);
        }
        throw new UnknownTokenException(pos - token.length, token);
    }

    let result = convertToken(nextToken());
    skipWhitespaces();
    if (pos !== end) {
        throw new WrongEndException(pos);
    }
    return result;
}

const parsePrefix = prefix =>
    parsePostfixPrefix(prefix, 0, prefix.length, pos => ++pos, false, '(', ')');

const parsePostfix = postfix =>
    parsePostfixPrefix(postfix, postfix.length - 1, -1, pos => --pos, true, ')', '(');
