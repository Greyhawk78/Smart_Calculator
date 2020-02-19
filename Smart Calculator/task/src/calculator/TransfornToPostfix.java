package calculator;

import java.util.Stack;

import static java.lang.System.exit;

// Java program to convert an infix expression to a
// postfix expression using two precedence function
public class TransfornToPostfix {

    // to check if the input character
// is an operator or a '('
    static int isOperator(String input) {
        switch (input) {
            case "+":
                return 1;
            case "-":
                return 1;
            case "*":
                return 1;
            case "^":
                return 1;
            case "/":
                return 1;
            case "(":
                return 1;
        }
        return 0;
    }

    // to check if the input character is an operand
    static int isOperand(String input) {
        if (input.matches("\\d+")) {
            return 1;
        }
        return 0;
    }

    // function to return precedence value
// if operator is present in stack
    static int inPrec(String input) {
        switch (input) {
            case "+":
            case "-":
                return 2;
            case "*":
            case "/":
                return 4;
            case "^":
                return 5;
            case "(":
                return 0;
        }
        return 0;
    }

    // function to return precedence value
// if operator is present outside stack.
    static int outPrec(String input) {
        switch (input) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 3;
            case "^":
                return 6;
            case "(":
                return 100;
        }
        return 0;
    }

    // function to convert infix to postfix
    static String inToPost(String[] input) {
        Stack<String> s = new Stack<String>();
        StringBuilder sb = new StringBuilder();
        // while input is not NULL iterate
        int i = 0;
        while (input.length != i) {

            // if character an operand
            if (isOperand(input[i]) == 1) {
                sb.append(input[i]).append(" ");
            } // if input is an operator, push
            else if (isOperator(input[i]) == 1) {
                if (s.empty()
                        || outPrec(input[i]) > inPrec(s.peek())) {
                    s.push(input[i]);
                } else {
                    while (!s.empty()
                            && outPrec(input[i]) < inPrec(s.peek())) {
                        sb.append(s.peek()).append(" ");
                        s.pop();
                    }
                    s.push(input[i]);
                }
            } // condition for opening bracket
            else if (input[i].equals(")")) {
                while (!s.peek().equals("(")) {
                    sb.append(s.peek()).append(" ");
                    s.pop();

                    // if opening bracket not present
                    if (s.empty()) {
                        sb.append("Wrong input\n");
                        return "Invalid expression";
                    }
                }

                // pop the opening bracket.
                s.pop();
            }
            i++;
        }

        // pop the remaining operators
        while (!s.empty()) {
            if (s.peek() == "(") {
                sb.append("\n Wrong input\n");
                return "Invalid expression";
            }
            sb.append(s.peek()).append(" ");
            s.pop();
        }
        return sb.toString();
    }
}

