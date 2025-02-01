package expression.generic.calc;

import java.math.BigInteger;

public class BigIntegerCalc implements Calc<BigInteger> {
    @Override
    public BigInteger add(BigInteger n1, BigInteger n2) {
        return n1.add(n2);
    }

    @Override
    public BigInteger subtract(BigInteger n1, BigInteger n2) {
        return n1.subtract(n2);
    }

    @Override
    public BigInteger multiply(BigInteger n1, BigInteger n2) {
        return n1.multiply(n2);
    }

    @Override
    public BigInteger divide(BigInteger n1, BigInteger n2) {
        return n1.divide(n2);
    }

    @Override
    public BigInteger parseVariable(int n1) {
        return BigInteger.valueOf(n1);
    }

    @Override
    public BigInteger unaryMinus(BigInteger n1) {
        return n1.negate();
    }

    @Override
    public BigInteger count(BigInteger n1) {
        return BigInteger.valueOf(n1.bitCount());
    }
}
