package com.freetymekiyan.algorithms.Other;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class PostfixToInfix {

    public static void main(String[] args) {
        PostfixToInfix obj = new PostfixToInfix();
        Scanner sc = new Scanner(System.in);
        System.out.print("Postfix : ");
        String postfix = sc.next();
        System.out.println("Infix : " + obj.convertPostfix2Infix(postfix));

        System.out.print("Infix : \t");
        String infix = sc.next();
        System.out.print("Postfix : \t"+obj.convertInfix2Postfix(infix));
    }

    /**
     * Checks if the input is operator or not
     *
     * @param c input to be checked
     * @return true if operator
     */
    private boolean isOperator(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^')
            return true;
        return false;
    }

    /**
     * Converts any postfix to infix A number of parenthesis may be generated extra which were not
     * there in original infix expression. The parenthesis that may be generated extra will have no
     * impact on actual expression, they are just for better understanding. The process of postfix
     * to infix conversion is summarised below - 1.  Create an empty stack that can hold string
     * values. 2.  Scan the postfix expression from left to right 2a. If operand then push into
     * stack 2b. If operator then 1).  Pop first two elements 2).  Now make a string with "(" +
     * operand2 + operator + operand1 + ")" 3).  Push the new string onto stack 3. Pop the element
     * on stack.
     *
     * @param postfix String expression to be converted
     * @return String infix expression produced
     */
    public String convertPostfix2Infix(String postfix) {
        Stack<String> s = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if (isOperator(c)) {
                String b = s.pop();
                String a = s.pop();
                s.push("(" + a + c + b + ")");
            } else
                s.push("" + c);
        }

        return s.pop();
    }

    /**
     * Checks if c2 has same or higher precedence than c1
     *
     * @param c1 first operator
     * @param c2 second operator
     * @return true if c2 has same or higher precedence
     */
    private boolean checkPrecedence(char c1, char c2) {
        if ((c2 == '+' || c2 == '-') && (c1 == '+' || c1 == '-'))
            return true;
        else if ((c2 == '*' || c2 == '/') && (c1 == '+' || c1 == '-' || c1 == '*' || c1 == '/'))
            return true;
        else if ((c2 == '^') && (c1 == '+' || c1 == '-' || c1 == '*' || c1 == '/'))
            return true;
        else
            return false;
    }

    /**
     * Converts infix expression to postfix
     *
     * @param infix infix expression to be converted
     * @return postfix expression
     */
    public String convertInfix2Postfix(String infix) {
        System.out.printf("%-8s%-10s%-15s\n", "Input", "Stack", "Postfix");
        String postfix = "";  //equivalent postfix is empty initially
        Stack<Character> s = new Stack<>();  //stack to hold symbols
        s.push('#');  //symbol to denote end of stack

        System.out.printf("%-8s%-10s%-15s\n", "", format(s.toString()), postfix);

        for (int i = 0; i < infix.length(); i++) {
            char inputSymbol = infix.charAt(i);  //symbol to be processed
            if (isOperator(inputSymbol)) {  //if a operator
                //repeatedly pops if stack top has same or higher precedence
                while (checkPrecedence(inputSymbol, s.peek()))
                    postfix += s.pop();
                s.push(inputSymbol);
            } else if (inputSymbol == '(')
                s.push(inputSymbol);  //push if left parenthesis
            else if (inputSymbol == ')') {
                //repeatedly pops if right parenthesis until left parenthesis is found
                while (s.peek() != '(')
                    postfix += s.pop();
                s.pop();
            } else
                postfix += inputSymbol;
            System.out.printf("%-8s%-10s%-15s\n", "" + inputSymbol, format(s.toString()), postfix);
        }

        //pops all elements of stack left
        while (s.peek() != '#') {
            postfix += s.pop();
            System.out.printf("%-8s%-10s%-15s\n", "", format(s.toString()), postfix);

        }

        return postfix;
    }

    /**
     * Formats the input  stack string
     *
     * @param s It is a stack converted to string
     * @return formatted input
     */
    private String format(String s) {
        s = s.replaceAll(",", "");  //removes all , in stack string
        s = s.replaceAll(" ", "");  //removes all spaces in stack string
        s = s.substring(1, s.length() - 1);  //removes [] from stack string

        return s;
    }


    ///////////////////////////////////////////////////

    private static boolean isOperator2(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^'
                || c == '(' || c == ')';
    }

    private static boolean isLowerPrecedence(char op1, char op2) {
        switch (op1) {
            case '+':
            case '-':
                return !(op2 == '+' || op2 == '-');

            case '*':
            case '/':
                return op2 == '^' || op2 == '(';

            case '^':
                return op2 == '(';

            case '(':
                return true;

            default:
                return false;
        }
    }

    public static String convertInfix2Postfix2(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuffer postfix = new StringBuffer(infix.length());
        char c;

        for (int i = 0; i < infix.length(); i++) {
            c = infix.charAt(i);

            if (!isOperator2(c)) {
                postfix.append(c);
            } else {
                if (c == ')') {

                    while (!stack.isEmpty() && stack.peek() != '(') {
                        postfix.append(stack.pop());
                    }
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                } else {
                    if (!stack.isEmpty() && !isLowerPrecedence(c, stack.peek())) {
                        stack.push(c);
                    } else {
                        while (!stack.isEmpty() && isLowerPrecedence(c, stack.peek())) {
                            Character pop = stack.pop();
                            if (c != '(') {
                                postfix.append(pop);
                            } else {
                                c = pop;
                            }
                        }
                        stack.push(c);
                    }

                }
            }
        }
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }
        return postfix.toString();
    }

    public String convertToPostfix(String infix) {
        // Return a postfix representation of the expression in infix.

        Stack operatorStack = new Stack();  // the stack of operators

        char c;       // the first character of a token

        StringTokenizer parser = new StringTokenizer(infix,"+-*/^() ",true);
        // StringTokenizer for the input string

        StringBuffer postfix = new StringBuffer(infix.length());  // result

        // Process the tokens.
        while (parser.hasMoreTokens()) {

            String token = parser.nextToken();
            // get the next token and let c be
            c = token.charAt(0);         // the first character of this token

            if ( (token.length() == 1) && isOperator(c) ) {    // if token is an operator

                while (!operatorStack.empty() &&
                        !isLowerPrecedence(((String)operatorStack.peek()).charAt(0), c))
                    // (Operator on the stack does not have lower precedence, so it goes before this one.)

                    postfix.append(" ").append((String)operatorStack.pop());

                if (c==')') {
                    // Output the remaining operators in the parenthesized part.
                    String operator = (String)operatorStack.pop();
                    while (operator.charAt(0)!='(') {
                        postfix.append(" ").append(operator);
                        operator = (String)operatorStack.pop();
                    }
                }
                else
                    operatorStack.push(token);// Push this operator onto the stack.
            }
            else if ( (token.length() == 1) && c == ' ') {    // else if token was a space
                ;                                             // ignore it
            }
            else {  // (it is an operand)
                postfix.append(" ").append(token);  // output the operand
            }//end if
        }// end while for tokens

        // Output the remaining operators on the stack.
        while (!operatorStack.empty())
            postfix.append(" ").append((String)operatorStack.pop());

        // Return the result
        return postfix.toString();
    }//end convertToPostfix

}