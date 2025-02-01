package expression.exceptions;
import expression.*;

import java.util.*;

public class ExpressionParser implements TripleParser, ListParser {
    private final Deque<GlobalInterface> stackObj = new ArrayDeque<>();
    private final Deque<String> stackOperations = new ArrayDeque<>();
    private final Map<String, Integer> map = new HashMap<>(Map.of(
            "@", 100, "l0", 100, "t0", 100,
            "*", 10, "/", 10,
            "+", 5, "-", 5,
            "(", -1, "count", 100
    ));
    private List<String> var;
    @Override
    public ListExpression parse(String expression, List<String> variables) {
        var = variables;
        return parse(expression);
    }
    @Override
    public GlobalInterface parse(String string) {
        stackObj.clear();
        stackOperations.clear();
        StringBuilder sb = new StringBuilder();
        char lastToken = '-';
        boolean flag_start_var = false;
        int cntBracket = 0;
        int cnt_next = 0;
        StringBuilder assembly_var = new StringBuilder();
        for (int i = 0; i < string.length(); i++){
            if (cnt_next != 0){
                cnt_next--;
                continue;
            }
            Character ch = string.charAt(i);
            if (ch == '$'){
                flag_start_var = true;
                continue;
            }
            if (flag_start_var){
                if (Character.isDigit(ch)){
                    assembly_var.append(ch);
                    if (i != string.length() - 1 && !Character.isDigit(string.charAt(i + 1))){
                        AddObj(var.get(Integer.parseInt(assembly_var.toString())));
                        lastToken = '1';
                        assembly_var.setLength(0);
                        flag_start_var = false;
                    }
                } else {
                    if (!assembly_var.isEmpty()){
                        stackObj.push(new Variable(var.get(Integer.parseInt(assembly_var.toString()))));
                        assembly_var.setLength(0);
                    }
                    flag_start_var = false;
                }
                continue;
            }
            if (ch == 'c'){
                if (string.charAt(i + 1) == 'o' && string.charAt(i + 2) == 'u' && string.charAt(i + 3) == 'n' && string.charAt(i + 4) == 't'){
                    cnt_next = 4;
                    lastToken = '1';
                    if (stackOperations.isEmpty()){
                        stackOperations.push("count");
                        continue;
                    }
                    SiftStack("count");
                    continue;
                }

            }
            if (ch == 'l' || ch == 't'){
                if (string.charAt(i + 1) == '0'
                        && (Character.isWhitespace(string.charAt(i + 2)) || string.charAt(i + 2) == '(')){
                    cnt_next = 1;
                    if (stackOperations.isEmpty()){
                        stackOperations.push(ch + "0");
                        continue;
                    }
                    SiftStack(ch + "0");
                    continue;
                }
            }
            if (!Character.isDigit(ch) && (!map.containsKey(ch.toString()) || ch == '@') && ch != ')' && ch != 'x' &&
                    ch != 'y' && ch != 'z' && !Character.isWhitespace(ch)){
                throw new IllegalInException("IllegalIn");
            }
            if (ch == '(') {
                cntBracket++;
            }else if (ch == ')'){
                cntBracket--;
            }
            if (cntBracket < 0) throw new MismatchedPaterns("has not see open brackets");
            if (ch == '-' && Character.isDigit(string.charAt(i + 1)) && (i == 0 || map.containsKey(Character.toString(lastToken)))) {
                sb.append('-');
                lastToken = '-';
                continue;
            } else if (ch == '-' && (map.containsKey(Character.toString(lastToken)) || i == 0)){
                ch = '@';
                if (stackOperations.isEmpty()){
                    stackOperations.push(ch.toString());
                    continue;
                }
                SiftStack(ch.toString());
            }
            if (Character.isDigit(ch)) {
                sb.append(ch);
            } else if(!sb.isEmpty()){
                AddObj(sb.toString());
                sb.setLength(0);
            }
            if (map.containsKey(ch.toString()) && ch != '@'){
                if (stackOperations.isEmpty() || ch == '(' || map.get(ch.toString()) > map.get(stackOperations.peek())){
                    stackOperations.push(ch.toString());
                } else {
                    while (map.get(ch.toString()) <= map.get(stackOperations.peek())) {
                        AddObj(stackOperations.pop());
                        if (stackOperations.isEmpty()) break;
                    }
                    stackOperations.push(ch.toString());
                }
            } else if (ch == ')') {
                while (map.get(stackOperations.peek()) != -1) {
                    AddObj(stackOperations.pop());
                }
                stackOperations.pop();
            } else if (ch == 'x' || ch == 'y' || ch == 'z'){
                AddObj(Character.toString(ch));
            }
            if (!Character.isWhitespace(ch)) {
                lastToken = ch;
            }

        }
        if (flag_start_var) AddObj(var.get(Integer.parseInt(assembly_var.toString())));
        if (cntBracket != 0) throw new MismatchedPaterns("has not see close brackets");
        if (!sb.isEmpty()) AddObj(sb.toString());
        while (!stackOperations.isEmpty()) AddObj(stackOperations.pop());
        if (stackObj.size() != 1) throw new IllegalInException("IllegalIn");
        return stackObj.pop();
    }

    private void AddObj(String str){
        try {
            if (str.charAt(0) == '$'){
                stackObj.push(new Variable(str));
                return;
            }
            switch (str) {
                case "x", "y", "z" -> stackObj.push(new Variable(str));
                case "+", "-", "/", "*", "@", "l0", "t0", "count" ->{
                    GlobalInterface ell = stackObj.pop();
                    switch (str){
                        case "+" -> stackObj.push(new CheckedAdd(stackObj.pop(), ell));
                        case "-" -> stackObj.push(new CheckedSubtract(stackObj.pop(), ell));
                        case "/" -> stackObj.push(new CheckedDivide(stackObj.pop(), ell));
                        case "*" -> stackObj.push(new CheckedMultiply(stackObj.pop(), ell));
                        case "@" -> stackObj.push(new CheckedNegate(ell));
                        case "l0" -> stackObj.push(new l0(ell));
                        case "t0" -> stackObj.push(new T0(ell));
                        case "count" -> stackObj.push(new Count(ell));
                    }
                }
                default -> stackObj.push(new Const(Integer.parseInt(str)));
            }
        } catch (NoSuchElementException e){
            throw new IllegalNumbersVariables(str);
        }
    }

    private void SiftStack(String str){
        while (map.get(str) < map.get(stackOperations.peek())) {
            AddObj(stackOperations.pop());
            if (stackOperations.isEmpty()) break;
        }
        stackOperations.push(str);
    }
}