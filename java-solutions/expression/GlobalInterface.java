package expression;

import expression.generic.calc.Calc;

public interface GlobalInterface extends Expression, TripleExpression, ListExpression {

    int evaluate(int x, int y, int z);

    <T> T evaluate(T x, T y, T z, Calc<T> calc) throws Exception;
}
