package expression.exceptions;

import expression.AbstractOperations;
import expression.GlobalInterface;
import expression.generic.calc.Calc;

public class CheckedDivide extends AbstractOperations {
    public CheckedDivide(GlobalInterface exp1, GlobalInterface exp2){
        super(exp1, exp2, "/");
    }

    @Override
    public <T> T calculate(T n1, T n2, Calc<T> calc) {
        return calc.divide(n1, n2);
    }

    @Override
    public int calculate(int n1, int n2) {
        if (((n1 == Integer.MIN_VALUE) && (n2 == -1))) throw new OverflowExeptions("/");
        return n1 / n2;
    }


}