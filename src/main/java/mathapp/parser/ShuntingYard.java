package mathapp.parser;
import java.util.*;
import java.util.function.Function;

import static mathapp.parser.Token.Type.OPERATOR;

public class ShuntingYard {

    private static final Set<String> FUNCTIONS = Set.of("sin","cos","tan","log","sqrt");
    private static final Map<String, Integer> PRECEDENCE = Map.of(
            "+", 2,
            "-", 2,
            "*", 3,
            "/", 3,
            "^", 4
    );

    private static final Map<String, Boolean> RIGHT_ASSOC = Map.of(
            "^", true
    );

    public static Function<Double, Double> parse(String input) {
        List<Token> tokens = tokenize(input);
        List<Token> output = toPostfix(tokens);

        return x -> evaluatePostfix(output, x);
    }

    public static String toLatex(String input) {
        List<Token> tokens = tokenize(input);
        List<Token> output = toPostfix(tokens);
        return postfixToLatex(output);
    }

    // Tokenizes input
    private static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isWhitespace(c)) continue;

            if (Character.isDigit(c) || c=='.') {
                sb.setLength(0);
                while (i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i)=='.')) {
                    sb.append(input.charAt(i));
                    i++;
                }
                i--;
                tokens.add(new Token(Token.Type.NUMBER, sb.toString()));
            }
            else if (Character.isLetter(c)) {
                sb.setLength(0);
                while (i < input.length() && Character.isLetter(input.charAt(i))) {
                    sb.append(input.charAt(i));
                    i++;
                }
                i--;
                String word = sb.toString();
                if (word.equals("x")) tokens.add(new Token(Token.Type.VARIABLE, "x"));
                else if (FUNCTIONS.contains(word)) tokens.add(new Token(Token.Type.FUNCTION, word));
                else throw new RuntimeException("Unknown function: " + word);
            }
            else if ("+-*/^".indexOf(c) >= 0) {
                tokens.add(new Token(Token.Type.OPERATOR, String.valueOf(c)));
            }
            else if (c=='(') tokens.add(new Token(Token.Type.LEFT_PAREN,"("));
            else if (c==')') tokens.add(new Token(Token.Type.RIGHT_PAREN,")"));
            else throw new RuntimeException("Unknown character: " + c);
        }
        return tokens;
    }

    // Convert infix to postfix using shunting yard
    private static List<Token> toPostfix(List<Token> tokens) {
        List<Token> output = new ArrayList<>();
        Stack<Token> stack = new Stack<>();

        for (Token token : tokens) {
            switch(token.type) {
                case NUMBER, VARIABLE -> output.add(token);
                case FUNCTION, LEFT_PAREN -> stack.push(token);
                case OPERATOR -> {
                    while(!stack.isEmpty()) {
                        Token top = stack.peek();
                        if ((top.type==Token.Type.OPERATOR &&
                                ((RIGHT_ASSOC.getOrDefault(token.value,false) && PRECEDENCE.get(token.value) < PRECEDENCE.get(top.value)) ||
                                        (!RIGHT_ASSOC.getOrDefault(token.value,false) && PRECEDENCE.get(token.value) <= PRECEDENCE.get(top.value))))
                                || top.type==Token.Type.FUNCTION) {
                            output.add(stack.pop());
                        } else break;
                    }
                    stack.push(token);
                }
                case RIGHT_PAREN -> {
                    while(!stack.isEmpty() && stack.peek().type != Token.Type.LEFT_PAREN) {
                        output.add(stack.pop());
                    }
                    if(stack.isEmpty()) throw new RuntimeException("Mismatched parentheses");
                    stack.pop(); // remove '('
                    if(!stack.isEmpty() && stack.peek().type==Token.Type.FUNCTION) {
                        output.add(stack.pop());
                    }
                }
            }
        }

        while(!stack.isEmpty()) {
            Token t = stack.pop();
            if(t.type==Token.Type.LEFT_PAREN || t.type==Token.Type.RIGHT_PAREN)
                throw new RuntimeException("Mismatched parentheses");
            output.add(t);
        }
        return output;
    }

    // --- Evaluate postfix expression for a given x ---
    private static double evaluatePostfix(List<Token> postfix, double xVal) {
        Stack<Double> stack = new Stack<>();
        for(Token token : postfix) {
            switch(token.type) {
                case NUMBER -> stack.push(Double.parseDouble(token.value));
                case VARIABLE -> stack.push(xVal);
                case OPERATOR -> {
                    double b = stack.pop();
                    double a = stack.pop();
                    switch(token.value) {
                        case "+" -> stack.push(a+b);
                        case "-" -> stack.push(a-b);
                        case "*" -> stack.push(a*b);
                        case "/" -> stack.push(a/b);
                        case "^" -> stack.push(Math.pow(a,b));
                    }
                }
                case FUNCTION -> {
                    double a = stack.pop();
                    switch(token.value) {
                        case "sin" -> stack.push(Math.sin(a));
                        case "cos" -> stack.push(Math.cos(a));
                        case "tan" -> stack.push(Math.tan(a));
                        case "log" -> stack.push(Math.log(a));
                        case "sqrt" -> stack.push(Math.sqrt(a));
                    }
                }
            }
        }
        return stack.pop();
    }

    private static String postfixToLatex(List<Token> postfix) {
        Stack<String> stack = new Stack<>();
        for (Token token : postfix) {
            switch (token.type) {
                case NUMBER, VARIABLE -> stack.push(token.value);
                case FUNCTION -> {
                    String arg = stack.pop();
                    switch (token.value) {
                        case "sin" -> stack.push("\\sin(" + arg + ")");
                        case "cos" -> stack.push("\\cos(" + arg + ")");
                        case "tan" -> stack.push("\\tan(" + arg + ")");
                        case "log" -> stack.push("\\log(" + arg + ")");
                        case "sqrt" -> stack.push("\\sqrt{" + arg + "}");
                    }
                }
                case OPERATOR -> {
                    String b = stack.pop();
                    String a = stack.pop();
                    switch (token.value) {
                        case "+" -> stack.push(a + "+" + b);
                        case "-" -> stack.push(a + "-" + b);
                        case "*" -> {
                            boolean aIsNum = a.matches("\\d+(\\.\\d+)?");
                            boolean bIsNum = b.matches("\\d+(\\.\\d+)?");

                            if (aIsNum && bIsNum) {
                                stack.push(a + "\\cdot " + b);
                            } else {
                                stack.push(a + b);
                            }
                        }
                        case "/" -> stack.push("\\frac{" + a + "}{" + b + "}");
                        case "^" -> stack.push(a + "^{" + b + "}");
                    }
                }
            }
        }
        return stack.pop();
    }
}
