package expression;

import expression.generic.calc.Calc;

public class Add extends AbstractOperations {
    public Add(GlobalInterface exp1, GlobalInterface exp2) {
        super(exp1, exp2, "+");
    }

    @Override
    public int calculate(int n1, int n2) {
        return n1 + n2;
    }

    public <T> T calculate(T n1, T n2, Calc<T> calc) {
        return calc.add(n1, n2);
    }
}