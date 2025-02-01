package expression.generic.calc;


import expression.generic.calc.Calc;

public class DoubleCalc implements Calc<Double> {

    @Override
    public Double add(Double n1, Double n2) {
        return n1 + n2;
    }

    @Override
    public Double subtract(Double n1, Double n2) {
        return n1 - n2;
    }

    @Override
    public Double multiply(Double n1, Double n2) {
        return n1 * n2;
    }

    @Override
    public Double divide(Double n1, Double n2) {
        return n1 / n2;
    }

    @Override
    public Double parseVariable(int n1) {
        return (double) n1;
    }

    @Override
    public Double unaryMinus(Double n1) {
        return -n1;
    }

    @Override
    public Double count(Double n1) {
        if (Double.isNaN(n1)) return 12.0;
        long longValue = Double.doubleToRawLongBits(n1);
        int count = Long.bitCount(longValue);
        return (double) count;
    }
}
