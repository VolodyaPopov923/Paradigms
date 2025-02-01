package expression.generic.calc;

public class BoolCalc implements Calc<Boolean> {

    @Override
    public Boolean add(Boolean n1, Boolean n2) {
        return n1 || n2;
    }

    @Override
    public Boolean subtract(Boolean n1, Boolean n2) {
        return n1 && !n2 || !n1 && n2;
    }

    @Override
    public Boolean multiply(Boolean n1, Boolean n2) {
        return n1 && n2;
    }

    @Override
    public Boolean divide(Boolean n1, Boolean n2) {
        if (!n2) throw new ArithmeticException("divide by zero (/)");
        return n1 && n2;
    }

    @Override
    public Boolean parseVariable(int n1) {
        return n1 != 0;
    }

    @Override
    public Boolean unaryMinus(Boolean n1) throws Exception {
        return n1;
    }

    @Override
    public Boolean count(Boolean n1) {
        return n1;
    }

}
