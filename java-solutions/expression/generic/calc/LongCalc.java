package expression.generic.calc;

public class LongCalc implements Calc<Long> {
    @Override
    public Long add(Long n1, Long n2) {
        return n1 + n2;
    }

    @Override
    public Long subtract(Long n1, Long n2) {
        return n1 - n2;
    }

    @Override
    public Long multiply(Long n1, Long n2) {
        return n1 * n2;
    }

    @Override
    public Long divide(Long n1, Long n2) {
        return n1 / n2;
    }

    @Override
    public Long parseVariable(int n1) {
        return (long) n1;
    }

    @Override
    public Long unaryMinus(Long n1) throws Exception {
        return -n1;
    }

    @Override
    public Long count(Long n1) {
        long cnt = 0;
        while (n1 != 0){
            n1 /= 10;
            cnt++;
        }
        return cnt;
    }
}
