package expression;

import expression.generic.calc.Calc;

import java.util.List;
import java.util.Objects;

public class T0 extends AbstractParametrs {
    public GlobalInterface exp;
    public T0(GlobalInterface exp){
        this.exp = exp;
    }
    @Override
    public int evaluate(int n) {
        return Calculate(exp.evaluate(n));
    }
    public int evaluate(int n1, int n2, int n3) {
        return Calculate(exp.evaluate(n1, n2, n3));
    }

    @Override
    public <T> T evaluate(T x, T y, T z, Calc<T> calc) {
        return null;
    }

    public int Calculate(int n){
        return Integer.numberOfTrailingZeros(n);
    }

    @Override
    public String toString() {
        return "t0(" + exp.toString() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return Calculate(exp.evaluate(variables));
    }
}