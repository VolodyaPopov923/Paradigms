package expression.generic.calc;


import expression.exceptions.OverflowExeptions;

public class IntegerCalc implements Calc<Integer> {
    @Override
    public Integer add(Integer n1, Integer n2) {
        if (n1 > 0 && n2 > Integer.MAX_VALUE - n1 || n1 < 0 && n2 < Integer.MIN_VALUE - n1) {
            throw new ArithmeticException("+");
        }
        return n1 + n2;
    }

    @Override
    public Integer subtract(Integer n1, Integer n2) {
        if ((n2 < 0 && n1 > Integer.MAX_VALUE + n2) || (n2 > 0 && n1 < Integer.MIN_VALUE + n2)) {
            throw new ArithmeticException("-");
        }
        return n1 - n2;
    }

    @Override
    public Integer multiply(Integer n1, Integer n2) {
        long c = (long) n1 * n2;
        if ((c > Integer.MAX_VALUE) || (c < Integer.MIN_VALUE)) {
            throw new ArithmeticException("*");
        }
        return n1 * n2;
    }

    @Override
    public Integer divide(Integer n1, Integer n2) {
        if (((n1 == Integer.MIN_VALUE) && (n2 == -1)) || (n2 == 0)) throw new ArithmeticException("/");
        return n1 / n2;
    }

    @Override
    public Integer parseVariable(int n1) {
        return n1;
    }

    @Override
    public Integer unaryMinus(Integer n1) {
        if (n1 == Integer.MIN_VALUE) {
            throw new ArithmeticException("Unary Minus");
        }
        return -n1;
    }

    @Override
    public Integer count(Integer n1) {
        return Integer.bitCount(n1);
    }
}
