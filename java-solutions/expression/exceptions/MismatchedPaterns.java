package expression.exceptions;

public class MismatchedPaterns extends RuntimeException{
    public MismatchedPaterns(String operations){
        super(operations);
    }
}
