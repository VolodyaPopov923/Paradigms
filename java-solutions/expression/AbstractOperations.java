package expression;

import expression.generic.calc.Calc;

import java.util.List;
import java.util.Objects;

public abstract class AbstractOperations extends AbstractParametrs {
    protected GlobalInterface exp1;
    protected GlobalInterface exp2;
    protected String str;


    public AbstractOperations(GlobalInterface exp1, GlobalInterface exp2, String str) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.str = str;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return calculate(exp1.evaluate(variables), exp2.evaluate(variables));
    }

    public int evaluate(int n) {
        return calculate(exp1.evaluate(n), exp2.evaluate(n));
    }

    public int evaluate(int x, int y, int z) {
        return calculate(exp1.evaluate(x, y, z), exp2.evaluate(x, y, z));
    }
    public <T> T evaluate(T x, T y, T z, Calc<T> calc) throws Exception {
        return calculate(exp1.evaluate(x, y, z, calc), exp2.evaluate(x, y, z, calc), calc);
    }
    public abstract <T> T calculate(T n1, T n2, Calc<T> calc);
    public String toString() {
        return "(" + exp1.toString() + " " + str + " " + exp2.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractOperations abstract1 = (AbstractOperations) o;
        return Objects.equals(exp1, abstract1.exp1) && Objects.equals(exp2, abstract1.exp2) && Objects.equals(str, abstract1.str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp1, exp2, str);
    }

    public abstract int calculate(int n1, int n2);
}