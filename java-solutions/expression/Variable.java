package expression;

import expression.generic.calc.Calc;

import java.util.List;
import java.util.Objects;

public class Variable extends AbstractParametrs {
    private final String string;


    public Variable(String str) {
        this.string = str;
    }

    public Variable(int num){
        this.string = String.valueOf('$' + num);

    }

    public String toString() {
        return this.string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(string, variable.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (this.string) {
            case "x" -> x;
            case "y" -> y;
            default -> z;
        };
    }

    @Override
    public <T> T evaluate(T x, T y, T z, Calc<T> calc) {
        return switch (this.string) {
            case "x" -> x;
            case "y" -> y;
            default -> z;
        };
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return variables.get(Integer.parseInt(this.string.substring(1)));


    }
}
