package expression.exceptions;

public class OverflowExeptions extends RuntimeException{
    public OverflowExeptions(String operations){
        super("an overflow was received in the operation" + operations);
    }
}
