package com.freetymekiyan.algorithms.level.Hard;

import java.io.IOException;

/*
if the number is of odd length:-
let i = 1.
take the digit in the middle of the number.
let the position of this digit be n.
if (n-i) digit > (n+i) digit , then take all the numbers from 1 to  (n - i)th digit in the reverse order and place them from the (n+i)th digit to the last digit.
if (n-i) digit > (n+i) digit , increment the (n-i)+1 th digit by 1 and  then take all the numbers from 1 to  (n - i) + 1 th digit in the reverse order and place them from the (n+i) -1 th digit to the last digit.
if (n-i) digit == (n+i) digit then ,
increment i by 1 and
recursively check above conditions for (n-i)th digit and (n+i) digit.

condition 1:
1234004
1234321
condition 2:
1234567
1235321.
condition 3:
1234322
1334331

if the number is of even length:-
let i = 0.
take the digit in the middle of the number.
let the position of this digit be n = l / 2 where l is the length of the number.

if (n-i) digit > (n+i) + 1 th digit , then take all the numbers from 1 to  (n - i)th digit in the reverse order and place them from the (n+i) + 1 th digit to the last digit.

if (n-i) digit > (n+i) + 1 digit ,
 if i == 0:
increment the (n-i) th digit by 1 and  then take all the numbers from 1 to  (n - i)  th digit in the reverse order and place them from the (n+i) + 1 th digit to the last digit.
else if i != 1 then,
increment the (n-i) + 1 th digit by 1 and  then take all the numbers from 1 to  (n - i) + 1  th digit in the reverse order and place them from the (n+i) th digit to the last digit.

if (n-i) digit == (n+i) + 1 digit then ,
increment i by 1 and
recursively check above conditions for (n-i)th digit and (n+i) +1 th digit.

condition 1:
1234544321
1234554321
condition 2:
12345678
12355321.
condition 3:
12344322
13344331

 */



class NextPalindromeNumber {
    public static void main (String[] args) throws IOException {
        int p = 0;
        p = NextPalindromeNumber.getNextPalindrome(123);
        System.out.println("nearest palindrome of 123" + " is " + p);
        p = NextPalindromeNumber.getNextPalindrome(1992);
        System.out.println("nearest palindrome of 1992" + " is " + p);
        p = NextPalindromeNumber.getNextPalindrome(1999);
        System.out.println("nearest palindrome of 1999" + " is " + p);
        p = NextPalindromeNumber.getNextPalindrome(19999);
        System.out.println("nearest palindrome of 19999" + " is " + p);

    }

    public static int getNextPalindrome(int i) {
        int j = i;

        // 1 to 9 all are palindrome numbers
        if(i < 9)
            return i+1;

        else {
            int pal = 0;

            while(true) {
                i += 1;
                if(isPalindrome(i)) {
                    pal = i;
                    break;
                }

                j -= 1;
                if(isPalindrome(j)) {
                    pal = j;
                    break;
                }
            }
            return pal;
        }
    }

    public static boolean isPalindrome(int i) {
        String s = String.valueOf(i);
        return new StringBuilder(s).reverse().toString().equals(s);
    }


    /**
     * Corner cases:
     1. The number of digits is even or odd.
     2. All the digits are 9.

     Approach:
     1. Take two pointers low[i here] pointing to start & high[j here] pointing to last. Start comparing
        ith & jth elements. Continue until
        1.1 both are equal or
        1.2 jth element is greater than ith element.
     2. In case case 1.1 holds, add 1 to the ith element & reflect the changes in elements[due to carry]
        going downwards from i-1 to 0. Copy elements from i to 0 in the slots from j to n-1.
     3. Construct smallest palindrome from i+1 to j-1.

     Handle even length & odd length accordingly.
     */
    //if all the digits are 9,simply o/p 1 followed by n-1 0's followed by 1.
    void findNextPalindrome(int[] arr, int n) {
        int i,j,carry,low,high;

        i=0;
        j=n-1;
        while (i<j && arr[j]>=arr[i]) {
            i++;
            j--;
        }

        if (i>=j) {
            if (n%2 == 0) {    //handle even length palindrome
                i--;
                j++;
            }
            carry = 1;
            while (i>=0) {
                arr[i] += carry;
                carry = arr[i]/10;
                arr[i] %= 10;
                arr[j++] = arr[i--]; // copy i to j
            }
        }
        else {
            low = i+1;
            high = j-1;
            while (i >= 0) {
                arr[j++] = arr[i--]; // copy i to j
            }
            while (low < high)	{ // handle the case 8683
                arr[low] = arr[high] = (arr[low]<arr[high]) ? arr[low] : arr[high];
                low++;
                high--;
            }
        }
    }
}
