package expression.generic;


import expression.GlobalInterface;
import expression.exceptions.ExpressionParser;
import expression.exceptions.OverflowExeptions;
import expression.generic.calc.*;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

public class GenericTabulator implements Tabulator {
    public static void main(String[] args) throws Exception {
        switch (args[0]) {
            case "-i":
                new GenericTabulator().tabulate("i", args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]),
                        Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
            case "-d":
                new GenericTabulator().tabulate("d", args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]),
                        Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
            case "-bi":
                new GenericTabulator().tabulate("bi", args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]),
                        Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
            default:
                throw new IllegalArgumentException("Unknown mode");
        }
    }

    private final Map<String, Calc<?>> mapCalc = new HashMap<>(Map.of(
            "i", new IntegerCalc(),
            "d", new DoubleCalc(),
            "bi", new BigIntegerCalc(),
            "b", new ByteCalc(),
            "l", new LongCalc(),
            "u", new IntegerNoOverflowCalc(),
            "bool", new BoolCalc()
    ));

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Calc<?> calc = mapCalc.get(mode);

        return tabulate(calc, expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] tabulate(Calc<T> calc, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        ExpressionParser parser = new ExpressionParser();
        GlobalInterface parseStr = parser.parse(expression);
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        result[i - x1][j - y1][k - z1] = parseStr.evaluate(calc.parseVariable(i), calc.parseVariable(j), calc.parseVariable(k), calc);
                    } catch (ArithmeticException e) {
                        result[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return result;
    }
}


