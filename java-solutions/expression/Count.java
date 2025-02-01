package expression;

import expression.AbstractParametrs;
import expression.GlobalInterface;
import expression.exceptions.OverflowExeptions;
import expression.generic.calc.Calc;

import java.util.List;
import java.util.Objects;

public class Count extends AbstractParametrs {
    public GlobalInterface exp;
    public Count(GlobalInterface exp){
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
    public <T> T evaluate(T x, T y, T z, Calc<T> calc) throws Exception {
        return Calculate(exp.evaluate(x, y, z, calc), calc);
    }


    public <T> T Calculate(T n1, Calc<T> calc) throws Exception {
        return calc.count(n1);
    }
    public int Calculate(int n){
        return n;
    }

    @Override
    public String toString() {
        return "count(" + exp.toString() + ")";
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