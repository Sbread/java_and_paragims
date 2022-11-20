"use strict";

const cnst = value => () => value;
const pi = cnst(Math.PI);
const e = cnst(Math.E);
const Constants = {"pi" : pi, "e" : e};

const Variables = {
    "x" : 0,
    "y" : 1,
    "z" : 2
}

const variable = name => (...args) => args[Variables[name]];

const operation = operationType => {
    let op = (...operands) => (...args) => operationType(...operands.map((operand) => operand(...args)));
    Object.defineProperty(op, "arity", {
        value: operationType.length,
    });
    //op.arity = operationType.length;
    return op;
}

const add = operation((a, b) => a + b);
const subtract = operation((a, b) => a - b);
const multiply = operation((a, b) => a * b);
const divide = operation((a, b) => a / b);
const negate = operation(a => -a);
const avg3 = operation((x1, x2, x3) => {
    let args = Array.from([x1, x2, x3]);
    return args.reduce((sum, a) => sum + a, 0) / args.length;
});
const med5 = operation((x1, x2, x3, x4, x5) => {
    let args = Array.from([x1, x2, x3, x4, x5]);
    return args.sort((a, b) => a - b)[2];
});

const Operations = {
    // "+": add
    "negate" : negate,
    "+" : add,
    "-" : subtract,
    "*" : multiply,
    "/" : divide,
    "avg3" : avg3,
    "med5" : med5
};

const parse = expression => {
    let stack = [];
    expression.split(" ").filter(part => part.length > 0).map(operand => {
        if (operand in Constants) {
            stack.push(Constants[operand]);
        } else if (operand in Variables) {
            stack.push(variable(operand));
        }  else if (operand in Operations) {
            stack.push(Operations[operand](...stack.splice(-Operations[operand]["arity"])));
        } else if (/^-?\d+$/.test(operand)) {
            stack.push(cnst(parseInt(operand)));
        }
    });
    return stack.pop();
}