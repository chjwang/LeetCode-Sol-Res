package com.freetymekiyan.algorithms.Other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Minimum and Maximum values of an expression with * and +

 Given an expression which contains numbers and two operators ‘+’ and ‘*’, we need to find maximum
 and minimum value which can be obtained by evaluating this expression by different parenthesization.

 Examples:
 Input  : expr = “1+2*3+4*5”
 Output : Minimum Value = 27, Maximum Value = 105
 Explanation:
 Minimum evaluated value = 1 + (2*3) + (4*5) = 27
 Maximum evaluated value = (1 + 2)*(3 + 4)*5 = 105

 */
public class MinAndMaxValueOfExpression {
/**
 * We can solve this problem by dynamic programming method, we can see that this problem is similar
 * to matrix chain multiplication, here we are trying different parenthesization to maximize and minimize
 * expression value instead of number of matrix multiplication.
 *
 In below code first we have separated the operators and numbers from given expression
 then two 2D arrays are taken for storing the intermediate result which are updated similar to
 matrix chain multiplication and different parenthesization are tried among the numbers but according to
 operators occurring in between them.

 At the end last cell of first row will store the final result in both the 2D arrays.

 method that prints minimum and maximum value obtainable from an expression
 */
    public void printMinAndMaxValueOfExp(String exp) {
        List<Integer> operands = new ArrayList<>();
        List<Character> operators = new ArrayList<>();
        String tmp = "";

        char[] expArr = exp.toCharArray();

        //  store operator and numbers in different lists
        for (int i = 0; i < expArr.length; i++) {
            if (isOperator(expArr[i])) {
                operators.add(expArr[i]);
                operands.add(Integer.parseInt(tmp));
                tmp = "";
            } else {
                tmp += expArr[i];
            }
        }
        //  storing last number in operands
        operands.add(Integer.parseInt(tmp));

        int len = operands.size();
        int[][] minVal = new int[len][len];
        int[][] maxVal = new int[len][len];

        //  initializing minVal and maxVal 2D array
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                minVal[i][j] = Integer.MAX_VALUE;
                maxVal[i][j] = 0;

                //  initializing main diagonal by operands values
                if (i == j)
                    minVal[i][j] = maxVal[i][j] = operands.get(i);
            }
        }

        // looping similar to matrix chain multiplication and updating both 2D arrays
        for (int L = 2; L <= len; L++) {
            for (int i = 0; i < len - L + 1; i++) {
                int j = i + L - 1;
                for (int k = i; k < j; k++) {
                    int minTmp = 0, maxTmp = 0;

                    // if current operator is '+', updating tmp variable by addition
                    if (operators.get(k) == '+') {
                        minTmp = minVal[i][k] + minVal[k + 1][j];
                        maxTmp = maxVal[i][k] + maxVal[k + 1][j];
                    }

                    // if current operator is '*', updating tmp
                    // variable by multiplication
                    else if (operators.get(k) == '*') {
                        minTmp = minVal[i][k] * minVal[k + 1][j];
                        maxTmp = maxVal[i][k] * maxVal[k + 1][j];
                    }

                    //  updating array values by tmp variables
                    if (minTmp < minVal[i][j])
                        minVal[i][j] = minTmp;
                    if (maxTmp > maxVal[i][j])
                        maxVal[i][j] = maxTmp;
                }
            }
        }

        //  last element of first row will store the result
        System.out.println("Minimum value : " + minVal[0][len - 1] + ", Maximum value : " + maxVal[0][len - 1]);
    }

    // Utility method to check whether a character is operator or not
    boolean isOperator(char op) {
        return (op == '+' || op == '*');
    }

    //  Driver code to test above methods
//    public void main(String[] args) {
//        String expression = "1+2*3+4*5";
//        printMinAndMaxValueOfExp(expression);
//    }


    ////////////////////
    public static void main(String[] args) {
        parenthesisOuter("1+5*6-3");
        parenthesisOuter("15*5-12-9");
        parenthesisOuter("3*10*16-14-11*2");
        parenthesisOuter("8-5*18+5-13+0*2+14-6*6+1");
        parenthesisOuter("6+5-15*18+14-3-5-3-2-2*8-14+12");
        parenthesisOuter("13-4*6*18+12+8-5*8-4+2+11*6+9-7+6*12*18+8-7+3*1-15*1-12*1-12-14+7-14*9*6");
        parenthesisOuter("1/0");
        parenthesisOuter("1/1-1");
    }

    private static void parenthesisOuter(String expression) {
        Object[] results = parenthesis(expression);
        System.out.println(expression + " -> " + results[MINVAL] + "=" + results[MINEXPR] + " "
                + results[MAXVAL] + "=" + results[MAXEXPR]);
    }

    private static Map<String, Object[]> resultMap = new HashMap<>();

    private static final int MINVAL = 0;
    private static final int MAXVAL = 1;
    private static final int MINEXPR = 2;
    private static final int MAXEXPR = 3;

    public static Object[] parenthesis(String expression) {
        Object[] result = resultMap.get(expression);
        if (result != null) {
            return result;
        }

        try {
            Long parsedLong = new Long(expression);
            return new Object[] { parsedLong, parsedLong, expression, expression };
        } catch (NumberFormatException e) {
            // go on, it's not a number
        }

        result = new Object[] { Long.MAX_VALUE, Long.MIN_VALUE, null, null };
        for (int i = 1; i < expression.length() - 1; i++) {
            if (Character.isDigit(expression.charAt(i)))
                continue;
            Object[] left = parenthesis(expression.substring(0, i));
            Object[] right = parenthesis(expression.substring(i + 1, expression.length()));
            doOp(result, (Long) left[MINVAL], expression.charAt(i), (Long) right[MINVAL],
                    (String) left[MINEXPR], (String) right[MINEXPR]);
            doOp(result, (Long) left[MINVAL], expression.charAt(i), (Long) right[MAXVAL],
                    (String) left[MINEXPR], (String) right[MAXEXPR]);
            doOp(result, (Long) left[MAXVAL], expression.charAt(i), (Long) right[MINVAL],
                    (String) left[MAXEXPR], (String) right[MINEXPR]);
            doOp(result, (Long) left[MAXVAL], expression.charAt(i), (Long) right[MAXVAL],
                    (String) left[MAXEXPR], (String) right[MAXEXPR]);
        }

        resultMap.put(expression, result);
        return result;
    }

    private static void doOp(Object[] result, Long left, char op, Long right, String leftExpression,
                             String rightExpression) {
        switch (op) {
            case '+':
                setResult(result, left, op, right, left + right, leftExpression, rightExpression);
                break;
            case '-':
                setResult(result, left, op, right, left - right, leftExpression, rightExpression);
                break;
            case '*':
                setResult(result, left, op, right, left * right, leftExpression, rightExpression);
                break;
            case '/':
                if (right != 0) {
                    setResult(result, left, op, right, left / right, leftExpression, rightExpression);
                }
                break;
        }
    }

    private static void setResult(Object[] result, Long left, char op, Long right, Long leftOpRight,
                                  String leftExpression, String rightExpression) {
        if (result[MINEXPR] == null || leftOpRight < (Long) result[MINVAL]) {
            result[MINVAL] = leftOpRight;
            result[MINEXPR] = "(" + leftExpression + op + rightExpression + ")";
        }
        if (result[MAXEXPR] == null || leftOpRight > (Long) result[MAXVAL]) {
            result[MAXVAL] = leftOpRight;
            result[MAXEXPR] = "(" + leftExpression + op + rightExpression + ")";
        }
    }
}
