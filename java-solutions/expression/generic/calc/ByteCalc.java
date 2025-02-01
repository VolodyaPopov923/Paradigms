package expression.generic.calc;

public class ByteCalc implements Calc<Byte>{

    @Override
    public Byte add(Byte n1, Byte n2) {
        return (byte) (n1 + n2);
    }

    @Override
    public Byte subtract(Byte n1, Byte n2) {
        return (byte) (n1 - n2);
    }

    @Override
    public Byte multiply(Byte n1, Byte n2) {
        return (byte) (n1 * n2);
    }

    @Override
    public Byte divide(Byte n1, Byte n2) {
        return (byte) (n1 / n2);
    }

    @Override
    public Byte parseVariable(int n1) {
        return (byte) n1;
    }

    @Override
    public Byte unaryMinus(Byte n1) throws Exception {
        return (byte) -n1;
    }

    @Override
    public Byte count(Byte n1) {
        int cnt = 0;
        while (n1 != 0){
            n1 = (byte) (n1 & (n1 - 1));
            cnt++;
        }
        return (byte) cnt;
    }
}
