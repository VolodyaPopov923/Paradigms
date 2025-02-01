package expression.exceptions;

public class IllegalNumbersVariables extends RuntimeException{
    public IllegalNumbersVariables(String operations){
        super(" missing varible of operations " + operations);
    }
}
