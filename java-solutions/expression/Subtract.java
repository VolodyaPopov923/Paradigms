package expression;

import expression.generic.calc.Calc;

public class Subtract extends AbstractOperations {
    public Subtract(GlobalInterface exp1, GlobalInterface exp2) {
        super(exp1, exp2, "-");
    }

    @Override
    public int calculate(int n1, int n2) {
        return n1 - n2;
    }
    public <T> T calculate(T n1, T n2, Calc<T> calc) {
        return calc.subtract(n1, n2);
    }
}
