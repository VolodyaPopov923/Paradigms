package expression.exceptions;

import expression.AbstractOperations;
import expression.GlobalInterface;
import expression.generic.calc.Calc;

public class CheckedSubtract extends AbstractOperations {
    public CheckedSubtract(GlobalInterface exp1, GlobalInterface exp2){
        super(exp1, exp2, "-");
    }

    public <T> T calculate(T n1, T n2, Calc<T> calc) {
        return calc.subtract(n1, n2);
    }

    @Override
    public int calculate(int n1, int n2) {
        if ((n2 < 0 && n1 > Integer.MAX_VALUE + n2) || (n2 > 0 && n1 < Integer.MIN_VALUE + n2)){
            throw new OverflowExeptions("-");
        }
        return n1 - n2;
    }
}