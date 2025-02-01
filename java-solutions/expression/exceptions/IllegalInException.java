package expression.exceptions;

public class IllegalInException extends RuntimeException{
    public IllegalInException(String operations){
        super(operations);
    }
}
