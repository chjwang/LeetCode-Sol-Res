package com.freetymekiyan.algorithms.level.Hard;

import java.util.Stack;

/**
 * Implement a basic calculator to evaluate a simple expression string.
 *
 * The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers
 * and empty spaces.
 *
 * You may assume that the given expression is always valid.
 *
 * Some examples:
 *
 * "1 + 1" = 2
 *
 * "2 - 1 + 2" = 3
 *
 * "(1+(4+5+2)-3)+(6+8)" = 23
 *
 * Note: Do not use the eval built-in library function.
 *
 * Tags: Stack, Math
 *
 * Similar Problems: (M) Evaluate Reverse Polish Notation, (M) Basic Calculator II, (M) Different Ways to add
 * Parentheses, (H) Expression add Operators
 */
public class BasicCalculator {

    public int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int result = 0;
        int sign = 1; // sign of the current context
        int num = 0;

        Stack<Integer> stack = new Stack<>();
        stack.push(sign); // start with +1
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') { // current number
                num = num * 10 + (c - '0');
            } else if (c == '+' || c == '-') { // number finishes
                result += sign * num;
                sign = stack.peek() * (c == '+' ? 1 : -1);
                num = 0;
            } else if (c == '(') { // sign outside of parantheses
                stack.push(sign);
            } else if (c == ')') {
                stack.pop();
            }
        }
        result += sign * num; // last number
        return result;
    }
/**
 * Only 5 possible input we need to pay attention:
 *
 * digit: it should be one digit from the current number
 * '+': number is over, we can add the previous number and start a new number
 * '-': same as above
 * '(': push the previous result and the sign into the stack, set result to 0, just calculate the new
 *      result within the parenthesis.
 * ')': pop out the top two numbers from stack, first one is the sign before this pair of parenthesis,
 *      second is the temporary result before this pair of parenthesis. We add them together.
 *
 * Finally if there is only one number, from the above solution, we haven't add the number to the result, so we do a check see if the number is zero.
*/
    public int calculate2(String s) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        int number = 0;
        int sign = 1;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                number = 10 * number + (int)(c - '0');
            }else if(c == '+'){
                result += sign * number;
                number = 0;
                sign = 1;
            }else if(c == '-'){
                result += sign * number;
                number = 0;
                sign = -1;
            }else if(c == '('){
                //we push the result first, then sign;
                stack.push(result);
                stack.push(sign);
                //reset the sign and result for the value in the parenthesis
                sign = 1;
                result = 0;
            }else if(c == ')'){
                result += sign * number;
                number = 0;
                result *= stack.pop();    //stack.pop() is the sign before the parenthesis
                result += stack.pop();   //stack.pop() now is the result calculated before the parenthesis

            }
        }
        if(number != 0) result += sign * number;
        return result;
    }
}
