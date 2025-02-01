package expression;

import expression.generic.calc.Calc;

import java.util.List;
import java.util.Objects;

public class Const extends AbstractParametrs {
    private final int n;

    public Const(int n) {
        this.n = n;
    }

    @Override
    public int evaluate(int number) {
        return this.n;
    }

    @Override
    public int evaluate(int number1, int number2, int number3) {
        return this.n;
    }

    @Override
    public <T> T evaluate(T x, T y, T z, Calc<T> calc) {
        return calc.parseVariable(this.n);
    }

    public String toString() {
        return Integer.toString(this.n);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return n == aConst.n;
    }

    @Override
    public int hashCode() {
        return Objects.hash(n);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return n;
    }
}