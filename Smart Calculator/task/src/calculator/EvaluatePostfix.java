package calculator;

import java.math.BigInteger;
import java.util.Stack;

class EvaluatePostfix

{
    // Method to evaluate value of a postfix expression
    static BigInteger evaluatePostfix(String exp)
    {
        //create a stack
        Stack<BigInteger> stack = new Stack<>();

        // Scan all characters one by one
        for(int i = 0; i < exp.length(); i++)
        {
            char c = exp.charAt(i);

            if(c == ' ')
                continue;

                // If the scanned character is an operand
                // (number here),extract the number
                // Push it to the stack.
            else if(Character.isDigit(c))
            {
                BigInteger n = BigInteger.ZERO;

                //extract the characters and store it in num
                while(Character.isDigit(c))
                {
                    n = n.multiply(BigInteger.valueOf(10)).add(BigInteger.valueOf((int)(c-'0')));
                    i++;
                    c = exp.charAt(i);
                }
                i--;

                //push the number in stack
                stack.push(n);
            }

            // If the scanned character is an operator, pop two
            // elements from stack apply the operator
            else
            {
                BigInteger val1 = stack.pop();
                BigInteger val2 = stack.pop();

                switch(c)
                {
                    case '+':
                        stack.push(val2.add(val1));
                        break;

                    case '-':
                        stack.push(val2.subtract(val1));
                        break;

                    case '/':
                        stack.push(val2.divide(val1));
                        break;

                    case '*':
                        stack.push(val2.multiply(val1));
                        break;
                }
            }
        }
        return stack.pop();
    }
}

// This code is contributed by Arnab Kundu

