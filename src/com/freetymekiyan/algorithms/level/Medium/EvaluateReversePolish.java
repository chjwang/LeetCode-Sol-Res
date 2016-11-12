package com.freetymekiyan.algorithms.level.Medium;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 *
 * Valid operators are +, -, *, /. Each operand may be an integer or another
 * expression.
 *
 * Some examples:
 * ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
 * ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 *
 * Tags: Stack
 */
class EvaluateReversePolish {

    public static void main(String[] args) {
        // String[] tokens = {"2", "1", "+", "3", "*"};
        // String[] tokens = {"4", "13", "5", "/", "+"};
        String[] tokens = {"3", "-4", "+"};
        EvaluateReversePolish e = new EvaluateReversePolish();
        System.out.println(e.evaluateRPN(tokens));
    }

    /**
     * assign a priority for each operators
     * use a stack to store them
     * note the numbers can be negative
     *
     * We evaluate the expression left-to-right and push operands onto the
     * stack until we encounter an operator, which we pop the top two values
     * from the stack. We then evaluate the operator, with the values as
     * arguments and push the result back onto the stack.
     *
     * @param tokens
     * @return
     */
    public int evaluateRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0)
            return 0;
        Deque<Integer> stack = new ArrayDeque<>();

        int len = tokens.length;
        for (int i = 0; i < len; i++) {
            String cur = tokens[i];
            try {
                if (isOperator(cur)) {
                    int t2 = stack.pop();
                    int t1 = stack.pop();
                    int res = calculate(t1, t2, cur);
                    stack.push(res);
                } else {
                    stack.push(Integer.parseInt(cur));
                }
            } catch (NoSuchElementException | InvalidOperatorException ex) {
                throw new IllegalArgumentException("invalid reverse polish exp input");
            }
        }
        return stack.peek();
    }

    /**
     * Helper function to check whether a token is operator or not
     */
    private boolean isOperator(String c) {
        return "+".equals(c) || "-".equals(c) || "*".equals(c) || "/".equals(c);
    }

    /**
     * Helper function to do calculation
     */
    private int calculate(int t1, int t2, String operator) throws InvalidOperatorException {
        int res = 0;
        if ("+".equals(operator))
            res = t1 + t2;
        else if ("-".equals(operator))
            res = t1 - t2;
        else if ("*".equals(operator))
            res = t1 * t2;
        else if ("/".equals(operator))
            res = t1 / t2;
        else
            throw new InvalidOperatorException();
        return res;
    }

    class InvalidOperatorException extends Exception {
    }
}
