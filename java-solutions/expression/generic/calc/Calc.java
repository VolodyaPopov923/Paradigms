package expression.generic.calc;

public interface Calc<T> {
    T add(T n1, T n2);

    T subtract(T n1, T n2);

    T multiply(T n1, T n2);

    T divide(T n1, T n2);

    T parseVariable(int n1);

    T unaryMinus(T n1) throws Exception;
    T count(T n1);
}
