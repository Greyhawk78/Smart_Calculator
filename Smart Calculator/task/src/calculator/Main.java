package calculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> variables = new HashMap<>();

        while (true) {
            String incoming = scanner.nextLine();

            switch (incoming) {
                case "/help":
                    help();
                    break;
                case "/exit":
                    exit();
                    return;
                case "":
                    break;
                default:
                    String input = incoming.replaceAll("--", "+").
                            replaceAll("\\++", "+").replaceAll("-\\+", "-").
                            replaceAll("\\+-", "-").replaceAll("-", " - ").
                            replaceAll("\\+", " + ").replaceAll("\\*", " * ").
                            replaceAll("\\/", " / ").replaceAll("\\(", " ( ").
                            replaceAll("\\)", " ) ").replaceAll("\\s+", " ").trim();
                    if (input.matches("\\/.*")) {
                        System.out.println("Unknown command");
                    } else if (variables.containsKey(input)) {
                        System.out.println(variables.get(input));
                    }    else if (input.matches("[-]? \\d*")) {
                        System.out.println(input);
                    } else if (verifySum(input, variables)) {
                        int result=(calculate(input, variables));
                        if (result!=Integer.MAX_VALUE) System.out.println(result);
                    } else {

                        if (verifyVar(input)) {
                            varAssign(input, variables);
                        } else if (input.matches("[a-zA-Z]+")) {
                            System.out.println("Unknown variable");
                        } else if (input.matches("\\s*[a-zA-Z]+\\s*=.*")) {
                            System.out.println("Invalid assignment");
                        } else if (input.matches(".*=\\s*[-]?\\s*\\d*\\s*")) {
                            System.out.println("Invalid identifier");
                        } else {
                            System.out.println("Invalid expression");
                        }
                    }
                    break;
            }
        }
    }


    public static void help() {
        System.out.println("The program supports the addition + and subtraction - operators. " +
                "It supports unary and binary minus also. Works with variables. " +
                "Variables assignment syntax: var = digit. " +
                "Variable request syntax: var");
    }

    public static void exit() {
        System.out.println("Bye!");
    }

    public static String replaceVar(String input, Map<String, Integer> variables) {
        String[] temp = input.split(" ");
        for (int i = 0; i < temp.length; i++) {
            if (variables.containsKey(temp[i])) {
                temp[i] = variables.get(temp[i]).toString();
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < temp.length; i++) {
            sb.append(temp[i]);
            sb.append(" ");
        }
        String str = sb.toString().trim();
        return str;
    }

    public static boolean verifySum(String input, Map<String, Integer> variables) {
        String str = replaceVar(input, variables);
        Pattern pattern = Pattern.compile("([\\(\\+-]*\\s*\\d+\\s*){1}([\\+-\\/\\*\\(\\)]+\\s*\\d+\\s*)*");
        Matcher matcher = pattern.matcher(str);
        if (str.matches("(\\* \\*)|(\\* \\/)|(\\/ \\*)|(\\/ \\/)")||str.contains("=")) {
            return false;
        } else
            return matcher.find();
    }


    public static boolean verifyVar(String input) {

        Pattern pattern = Pattern.compile("\\s*[a-zA-Z]+\\s*=\\s*[-]?\\s*[a-zA-Z]*\\d*\\s*");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches() && matcher.group().length() == input.length()) {
            return true;
        }
        return false;
    }

    public static void varAssign(String input, Map<String, Integer> variables) {
        String[] temparr = input.split("( = )|( =)|(= )|(=)");
        if (variables.containsKey(temparr[1])) {
            temparr[1] = String.valueOf(variables.get(temparr[1]));
        }
        try {
            variables.put(temparr[0], Integer.parseInt(temparr[1]));
        } catch (NumberFormatException e) {
            System.out.println("Invalid assignment");
        }
    }

    public static int calculate(String input, Map<String, Integer> variables) {

        input = replaceVar(input, variables);
        Pattern digit = Pattern.compile("\\d+");
        String[] expression = input.split(" ");
        String output = TransfornToPostfix.inToPost(expression);
     /*   StringBuilder number = new StringBuilder();
        int sum = 0;
        for (String part : expression) {
            number.append(part);
            Matcher matcher = digit.matcher(part);
            if (matcher.matches()) {
                sum += Integer.parseInt(number.toString());
                number = new StringBuilder();
            }
        } */
        try {
            return EvaluatePostfix.evaluatePostfix(output);
        } catch (Exception e) {
        System.out.println("Invalid expression");
        }
        return Integer.MAX_VALUE;
    }
}


