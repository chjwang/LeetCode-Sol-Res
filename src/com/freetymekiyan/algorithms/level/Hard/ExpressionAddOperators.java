package com.freetymekiyan.algorithms.level.Hard;

import java.util.List;

/**
 * Given a string that contains only digits 0-9 and a target value, return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.

 Examples:

 "123", 6 -> ["1+2+3", "1*2*3"]
 "232", 8 -> ["2*3+2", "2+3*2"]
 "105", 5 -> ["1*0+5","10-5"]
 "00", 0 -> ["0+0", "0-0", "0*0"]
 "3456237490", 9191 -> []


 深度优先搜索
 复杂度 时间 O(N^2) 空间 O(N)

 思路
 因为要输出所有可能的情况，必定是用深度优先搜索。问题在于如何将问题拆分成多次搜索。

 加减法很好处理，每当我们截出一段数字时，将之前计算的结果加上或者减去这个数，就可以将剩余的数字字符串和新的计算结果
 代入下一次搜索中了，直到我们的计算结果和目标一样，就完成了一次搜索。

 然而，乘法如何处理呢？这里我们需要用一个变量记录乘法当前累乘的值，直到累乘完了，遇到下一个加号或减号再将其算入
 计算结果中。这里有两种情况:

 - 乘号之前是加号或减号，例如2+3*4，我们在2那里算出来的结果，到3的时候会加上3，计算结果变为5。在到4的时候，
 因为4之前我们选择的是乘号，这里3就应该和4相乘，而不是和2相加，所以在计算结果时，要将5先减去刚才加的3得到2，
 然后再加上3乘以4，得到2+12=14，这样14就是到4为止时的计算结果。

 - 另外一种情况是乘号之前也是乘号，如果2+3*4*5，这里我们到4为止计算的结果是14了，然后我们到5的时候又是乘号，
 这时候我们要把刚才加的3*4给去掉，然后再加上3*4*5，也就是14-3*4+3*4*5=62。这样5的计算结果就是62。

 因为要解决上述几种情况，我们需要这么几个变量:
  一个是记录上次的计算结果currRes，
  一个是记录上次被加或者被减的数lastOp，
  一个是当前准备处理的数currNum。

 当下一轮搜索是加减法时，prevNum就是简单换成currNum，当下一轮搜索是乘法时，prevNum是prevNum乘以currNum。

 注意
 第一次搜索不添加运算符，只添加数字，就不会出现+1+2这种表达式了。
 我们截出的数字不能包含0001这种前面有0的数字，但是一个0是可以的。这里一旦截出的数字前导为0，就可以return了，
 因为说明前面就截的不对，从这之后都是开始为0的，后面也不可能了。


 题解：
 注意的问题：

 1. 有可能string很长，所以要用长整数。

 2.需要记录last operand，为的是处理乘号的情况。
 乘号时的计算公式为: currRes - lastOP + lastOP * curVaule，
 如 2 + 3 * 5, 当计算到要乘5时，result = 2 + 3 = 5, lastOp = 3, curValue = 5, 则最终结果为： 5 - 3 + 3 * 5 = 17 = 2 + 3 * 5。

 3. 记录当前结果，用于在num长度为零时， 判断是否与target相等，并加入最终的res中。

 4. 需要跳过长于一个零的操作数，如“00”， “000”， etc.

 5. 按左操作数从长度为1 到长度为len(num)循环， 并递归。
 */
public class ExpressionAddOperators {

  List<String> res;

  public List<String> addOperators(String num, int target) {
    helper(num, target, "", 0, 0);
    return res;
  }

  /**
   *
   * @param num string containing digits
   * @param target target integer value
   * @param exp expression with operators partial result
   * @param currRes 记录上次的计算结果 long
   * @param lastOp last operand for add/minus, long
   */
  private void helper(String num, int target, String exp, long currRes, long lastOp) {
    // 如果计算结果等于目标值，且所有数都用完了，则是有效结果
    if (currRes == target && num.length() == 0) {
      res.add(exp);
      return;
    }
    // 搜索所有可能的拆分情况
    for (int i = 1; i <= num.length(); i++) {
      String currStr = num.substring(0, i);
      // 对于前导为0的数予以排除
      if (currStr.length() > 1 && currStr.charAt(0) == '0') {
        // 这里是return不是continue
        return;
      }
      // 得到当前截出的数
      long currNum = Long.parseLong(currStr);
      // 去掉当前的数，得到下一轮搜索用的字符串
      String next = num.substring(i);
      // 如果不是第一个字母时，可以加运算符，否则只加数字
      if (exp.length() != 0) {
        // 乘法
        helper(next, target, exp + "*" + currNum, (currRes - lastOp) + lastOp * currNum, lastOp * currNum);
        // 加法
        helper(next, target, exp + "+" + currNum, currRes + currNum, currNum);
        // 减法
        helper(next, target, exp + "-" + currNum, currRes - currNum, -currNum);
      } else {
        // 第一个数
        helper(next, target, currStr, currNum, currNum);
      }

    }
  }
}