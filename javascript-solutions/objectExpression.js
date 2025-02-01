"use strict"

let arrayVariables = ["x", "y", "z"]
const Constructor = (constructor, evaluate, toString, diff, prefix = toString, postfix = toString) => {
    constructor.prototype.evaluate = evaluate
    constructor.prototype.toString = toString
    constructor.prototype.diff = diff
    constructor.prototype.prefix = prefix
    constructor.prototype.postfix = postfix
    return constructor
}

const Const = Constructor(function (value) {
    this.value = value
}, function () {
    return this.value
}, function () {
    return this.value.toString()
}, function () {
    return new Const(0)
})
const ONE = new Const(1)
const ZERO = new Const(0)

const Variable = Constructor(function (variable) {
    this.variable = variable
}, function (...args) {
    return args[arrayVariables.indexOf(this.variable)]
}, function () {
    return this.variable
}, function (diffVariable) {
    return this.variable === diffVariable ? ONE : ZERO
})

const Operation = Constructor(function (...args) {
    this.args = args
}, function (x, y, z) {
    return this.func(...this.args.map(variable => variable.evaluate(x, y, z)))
}, function () {
    return this.args.join(" ") + " " + this.operation
}, function (diffVariable) {
    return this.diffOperation(diffVariable, ...this.args)
}, function () {
    return "(" + this.operation + " " + this.args.map(variable => variable.prefix()).join(" ") + ")"
}, function () {
    return "(" + this.args.map(variable => variable.postfix()).join(" ") + " " + this.operation + ")"
})

function AbstractOperations(operation, func, diffOperation) {
    const constructor = function (...args) {
        this.args = args;
    }
    constructor.prototype = Object.create(Operation.prototype)
    constructor.prototype.func = func
    constructor.prototype.operation = operation
    constructor.prototype.diffOperation = diffOperation
    constructor.prototype.lenght = func.length
    return constructor
}

function mapDiff(operation, ...args) {
    switch (operation) {
        case "+":
            return new Add(args[1].diff(args[0]), args[2].diff(args[0]))
        case "-":
            return new Subtract(args[1].diff(args[0]), args[2].diff(args[0]))
        case "*":
            return new Add(new Multiply(args[1].diff(args[0]), args[2]), new Multiply(args[1], args[2].diff(args[0])))
        case "/":
            return new Divide(new Subtract(new Multiply(args[1].diff(args[0]), args[2]), new Multiply(args[1], args[2].diff(args[0]))), new Multiply(args[2], args[2]))
        case "negate":
            return new Negate(args[1].diff(args[0]))
        case "atan":
            return new Multiply(new Divide(ONE, new Add(ONE, new Multiply(args[1], args[1]))), args[1].diff(args[0]))
        case "atan2":
            return new Divide(new Subtract(new Multiply(args[1].diff(args[0]), args[2]), new Multiply(args[1], args[2].diff(args[0]))), new Add(new Multiply(args[1], args[1]), new Multiply(args[2], args[2])))
        case "exp":
            return new Multiply(new Exp(args[1]), args[1].diff(args[0]))
        case "ln":
            return new Divide(args[1].diff(args[0]), args[1])
        case "cos":
            return new Negate(new Multiply(args[1].diff(args[0]), new Sin(args[1])))
        case "sin":
            return new Multiply(args[1].diff(args[0]), new Cos(args[1]))
        case "mean":
            return new Divide(args.slice(1).reduce((x, y) => new Add(x, y.diff(args[0])), ZERO), new Const(args.length - 1))
        case "var":
            return new Subtract(new Mean(...args.slice(1).map(x => new Multiply(x, x).diff(args[0]))), new Multiply(new Multiply(new Const(2), new Mean(...args.slice(1))), new Mean(...args.slice(1)).diff(args[0])))
        case "cosh":
            return new Multiply(args[1].diff(args[0]), new Sinh(args[1]))
        case "sinh":
            return new Multiply(args[1].diff(args[0]), new Cosh(args[1]))
        case "power":
            return new Multiply(new Power(args[1], args[2]), new Power(args[1], new Subtract(args[2], ONE)).diff(args[0]))
        case "log":
            return new Divide(args[1].diff(args[0]), new Log(args[1]))
        case "sum":
            return args.slice(1).reduce((x, y) => new Add(x, y.diff(args[0])), ZERO)
        case "avg":
            return new Divide(args.slice(1).reduce((x, y) => new Add(x, y.diff(args[0])), ZERO), new Const(args.length - 1))
        case "min3":
            return args.slice(1).reduce((x, y) => new Min3(x, y.diff(args[0])), args[0])
        case "max5":
            return args.slice(1).reduce((x, y) => new Max5(x, y.diff(args[0])), args[0])
    }
}


const Add = AbstractOperations("+", (left, right) => left + right, (op, left, right) => mapDiff("+", op, left, right))
const Subtract = AbstractOperations("-", (left, right) => left - right, (op, left, right) => mapDiff("-", op, left, right))
const Multiply = AbstractOperations("*", (left, right) => left * right, (op, left, right) => mapDiff("*", op, left, right))
const Divide = AbstractOperations("/", (left, right) => left / right, (op, left, right) => mapDiff("/", op, left, right))
const Negate = AbstractOperations("negate", single => -single, (op, single) => mapDiff("negate", op, single))
const ArcTan = AbstractOperations("atan", (single) => Math.atan(single), (op, single) => mapDiff("atan", op, single))
const ArcTan2 = AbstractOperations("atan2", (left, right) => Math.atan2(left, right), (op, left, right) => mapDiff("atan2", op, left, right))
const Exp = AbstractOperations("exp", (single) => Math.exp(single), (op, single) => mapDiff("exp", op, single))
const Ln = AbstractOperations("ln", (single) => Math.log(single), (op, single) => mapDiff("ln", op, single))
const Sin = AbstractOperations("sin", (single) => Math.sin(single), (op, single) => mapDiff("sin", op, single))
const Cos = AbstractOperations("cos", (single) => Math.cos(single), (op, single) => mapDiff("cos", op, single))
const Mean = AbstractOperations("mean", (...args) => args.reduce((x, y) => x + y) / args.length, (op, ...args) => mapDiff("mean", op, ...args))
const Var = AbstractOperations("var", (...args) => (args.reduce((x, y) => x + y ** 2, 0) / args.length) - (args.reduce((x, y) => x + y, 0) / args.length) ** 2, (op, ...args) => mapDiff("var", op, ...args))
const Sinh = AbstractOperations("sinh", (single) => Math.sinh(single), (op, single) => mapDiff("sinh", op, single))
const Cosh = AbstractOperations("cosh", (single) => Math.cosh(single), (op, single) => mapDiff("cosh", op, single))
const Power = AbstractOperations("power", (left, right) => left ** right, (op, left, right) => mapDiff("power", op, left, right))
const Log = AbstractOperations("log", (left, right) => Math.log(left) / Math.log(right), (op, left, right) => mapDiff("log", op, left, right))
const Sum = AbstractOperations("sum", (...args) => args.reduce((x, y) => x + y), (op, ...args) => mapDiff("sum", op, ...args))
const Avg = AbstractOperations("avg", (...args) => args.reduce((x, y) => x + y) / args.length, (op, ...args) => mapDiff("avg", op, ...args))
const Min3 = AbstractOperations("min3", (...args) => Math.min(...args), (op, ...args) => mapDiff("min3", op, ...args))
const Max5 = AbstractOperations("max5", (...args) => Math.max(...args), (op, ...args) => mapDiff("max5", op, ...args))
const mapObj = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide,
    "negate": Negate,
    "atan": ArcTan,
    "atan2": ArcTan2,
    "exp": Exp,
    "ln": Ln,
    "sin": Sin,
    "cos": Cos,
    "mean": Mean,
    "var": Var,
    "sinh": Sinh,
    "cosh": Cosh,
    "power": Power,
    "log": Log,
    "sum": Sum,
    "avg": Avg,
    "min3": Min3,
    "max5": Max5
}
const mapNObj = {
    "mean": Mean,
    "var": Var
}

const parse = function (expression) {
    let expression_array = expression.split(" ").filter(Boolean);
    let stack = []
    for (let stackEll of expression_array) {
        if (stackEll in mapObj) {
            stack.push(new mapObj[stackEll](...stack.splice(stack.length - (new mapObj[stackEll]).lenght)));
        } else {
            if (arrayVariables.includes(stackEll)) {
                stack.push(new Variable(stackEll));
            } else {
                stack.push(new Const(+stackEll));
            }
        }
    }
    return stack.pop();
}

function AbstractParsingError(message) {
    this.message = message
}

AbstractParsingError.prototype = Object.create(Error.prototype)

function createErrorFactory(BaseError) {
    function CustomError(message) {
        BaseError.call(this, message);
    }

    CustomError.prototype = Object.create(BaseError.prototype);
    return CustomError;
}

const EmptyInputError = createErrorFactory(AbstractParsingError);
const IllegalBracketsSubsequence = createErrorFactory(AbstractParsingError);
const UnnecessaryArguments = createErrorFactory(AbstractParsingError);
const IllegalArgumentsError = createErrorFactory(AbstractParsingError);

const abstractParse = (expression, postfixMode = false) => {
    const exprArray = expression.split(/(\(|\)|\s+)/).filter(word => word.trim() !== "")
    let stack = [], stackCnt = [0]
    let currentIndexStackCnt = 0, brackets_balance = 0
    if (exprArray.length === 0) {
        throw new EmptyInputError("Empty input")
    }
    const handleOpenBracket = () => {
        brackets_balance++;
        currentIndexStackCnt++;
        stackCnt[currentIndexStackCnt] = 0;
    };
    const handleCloseBracket = () => {
        if (brackets_balance === 0) {
            throw new IllegalBracketsSubsequence("Mismatched opened brackets");
        }
        if (stackCnt[currentIndexStackCnt] === 0) {
            throw new UnnecessaryArguments("Not enough arguments");
        }
        const operationIndex = postfixMode ? stack.length - 1 : stack.length - stackCnt[currentIndexStackCnt] - 1;
        const operation = stack[operationIndex];
        if (!(operation in mapObj)) {
            throw new IllegalArgumentsError("Illegal argument: ");
        }
        if (!(operation in mapNObj) && (stackCnt[currentIndexStackCnt] !== (new mapObj[operation]).lenght)){
                throw new UnnecessaryArguments("Not enough arguments");
        }
        stack.splice(operationIndex, 1);
        stack.push(new mapObj[operation](...stack.splice(stack.length - stackCnt[currentIndexStackCnt])));
        stackCnt[--currentIndexStackCnt]++;
        brackets_balance--;
    };
    const handleOperation = (stackEll) => {
        if (stackEll in mapObj) {
            stack.push(stackEll);
        } else if (arrayVariables.includes(stackEll)) {
            stackCnt[currentIndexStackCnt]++;
            stack.push(new Variable(stackEll));
        } else if (!isNaN(+stackEll)) {
            stackCnt[currentIndexStackCnt]++;
            stack.push(new Const(+stackEll));
        } else {
            throw new IllegalArgumentsError("Illegal argument: " + stackEll);
        }
    };
    exprArray.map(stackEll =>
        stackEll === "(" ? handleOpenBracket() :
            stackEll === ")" ? handleCloseBracket() :
                handleOperation(stackEll)
    );
    if (brackets_balance !== 0) {
        throw new IllegalBracketsSubsequence("Mismatched closed brackets")
    }
    if (stack.length !== 1) {
        throw new UnnecessaryArguments("Unnecessary arguments")
    }
    return stack.pop();
}
const parsePrefix = expression => abstractParse(expression)
const parsePostfix = expression => abstractParse(expression, true)