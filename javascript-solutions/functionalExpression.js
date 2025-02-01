"use strict"

const variable = index => (...args) => args[["x", "y", "z"].indexOf(index)]
const cnst = x => () => x
const pi = cnst(Math.PI)
const e = cnst(Math.E)
const one = cnst(1)
const two = cnst(2)

const abstractOperation = f => (...args) => (...[x, y, z]) => f(...args.map(operate => operate(x, y, z)))
const add = abstractOperation((a, b) => a + b)
const subtract = abstractOperation((a, b) => a - b)
const multiply = abstractOperation((a, b) => a * b)
const divide = abstractOperation((a, b) => a / b)
const negate = abstractOperation(a => -a)

const min5 = abstractOperation(Math.min)
const max3 = abstractOperation(Math.max)
const sin = abstractOperation(Math.sin)
const cos = abstractOperation(Math.cos)
const sinh = abstractOperation(Math.sinh)
const cosh = abstractOperation(Math.cosh)
const floor = abstractOperation(Math.floor)
const ceil = abstractOperation(Math.ceil)
const sqrt = abstractOperation(Math.sqrt)
const square = abstractOperation(x => x * x)