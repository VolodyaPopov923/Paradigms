package expression.generic.calc;

public class IntegerNoOverflowCalc implements Calc<Integer>{

    @Override
    public Integer add(Integer n1, Integer n2) {
        return n1 + n2;
    }

    @Override
    public Integer subtract(Integer n1, Integer n2) {
        return n1 - n2;
    }

    @Override
    public Integer multiply(Integer n1, Integer n2) {
        return n1 * n2;
    }

    @Override
    public Integer divide(Integer n1, Integer n2) {
        return n1 / n2;
    }

    @Override
    public Integer parseVariable(int n1) {
        return n1;
    }

    @Override
    public Integer unaryMinus(Integer n1) throws Exception {
        return -n1;
    }

    @Override
    public Integer count(Integer n1) {
        return Integer.bitCount(n1);
    }
}
