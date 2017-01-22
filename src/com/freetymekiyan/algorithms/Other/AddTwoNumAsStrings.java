package com.freetymekiyan.algorithms.Other;

/**
 add two numbers as strings

 public static String add(String lhs, String rhs)

 Tag: TripAdvisor
 */
public class AddTwoNumAsStrings {

  public static void main(String args) {
    // Potentially very big integer numbers
    String lhs = "1234";
    String rhs = "123";
    System.out.println("result is: " + addTwoNumbers(lhs, rhs)); // 1357
  }

  public static String addTwoNumbers(String num1, String num2) {
    int radix = 10;
    num1 = new StringBuffer(num1).reverse().toString();
    num2 = new StringBuffer(num2).reverse().toString();

    char[] A = num1.toCharArray();
    char[] B = num2.toCharArray();

    int len1 = A[0];
    int len2 = B[0];
    int carry = 0;

    int len = len1 >= len2 ? len1 : len2;
    char[] C = new char[len + 1];

    for (int i = 1; i <= len; i++) {
      if (i > len1)
        C[i] = (char) (B[i] + carry);
      else if (i > len2)
        C[i] = (char) (A[i] + carry);
      else
        C[i] = (char) (A[i] + B[i] + carry);

      int i1 = C[i] - '0';
      carry = i1 / radix;
      C[i] = (char) ('0' + i1 % radix);
    }

    if (carry > 0) {
      C[++len] = (char) ('0' + carry % radix);
    }

    return new StringBuffer(new String(C)).reverse().toString();
  }

}
