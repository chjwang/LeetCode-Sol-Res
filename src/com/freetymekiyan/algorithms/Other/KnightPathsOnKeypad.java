package com.freetymekiyan.algorithms.Other;

/**
 "She escaped? This can't be happening! Get me the security team!"

 Professor Boolean, a notorious mad scientist, just found out his precious rabbit specimen has escaped!
 He rushes to call his security minions on the lab phone. However, the rabbit escapee hacked the phone
 to speed her escape! She left a sign with the following clues:

    Each digit that is dialed has to be a number that can be reached by a knight chess piece from
    the last digit dialed - that is, you must move precisely 2 spaces in one direction, and then 1
    space in a perpendicular direction to dial the next correct number. You can dial any number you
    want to start with, but subsequent numbers must obey the knight's move rule.

 The lab phone has the numbers arranged in the following way: 1, 2, 3 in the first row;
 4, 5, 6 in the second row; 7, 8, 9 in the third row; and blank, 0, blank in the fourth row.

 123
 456
 789
 0

 For example, if you just dialed a 1, the next number you dial has to be either a 6 or an 8.
 If you just dialed a 6, the next number must be a 1 or 7.

 Professor Boolean wants you to find out how many different phone numbers he can dial under these
 conditions. Write a function called answer(x, y, z) that computes the number of phone numbers one
 can dial that start with the digit x, end in the digit y, and consist of z digits in total.
 Output this number as a string representing the number in base-10.

 x and y will be digits from 0 to 9. z will be between 1 and 100, inclusive.

 Let E be the adjacency list that you described, such that E[x] gives us the list of vertices adjacent to x.
 Let DP be an array of dimenstions [0..9][0..9][1..z], with the bounds inclusive.

 For 0<=a<=9, 0<=b<=9 and 1<=d<=z, the value DP[a][b][d] represents the amount of phone numbers of
 length d that can be dialed starting from a and ending in b, while respecting the knight move
 dialing constraints.

 The idea is to fill this array up, counting the phone numbers of size 1, then size 2, and so on,
 until the numbers of size d have been computed. The final answer will be given by the value DP[x][y][z].

 Formally:

 DP[a][b][1] = 1   , if b is contained in E[a].
 DP[a][b][1] = 0   , otherwise.

 DP[a][b][d] = sum_{0<=f<=9 and b in E[f]} DP[a][f][d-1]
 return the value DP[x][y][z] as the answer.

 moves = [[4,6],[6,8],[7,9],[4,8],[0,3,9],[],[0,1,7],[2,6],[1,3],[2,4]]
 def knight_moves(length, start = 1):
     dp = [[[0 for i in range(length + 1)] for j in range(10)] for k in range(10)]

     for a in range(10):
        for b in moves[a]:
            dp[a][b][1] = 1

     for k in range(2, length + 1):
        for c in range(10):
            for a in moves[c]:
                for b in moves[a]:
                    dp[c][b][k] += dp[c][a][k - 1]

     for end in moves[start]:
        if dp[start][end][length] != 0:
            return dp[start][end][length]

     return 0


 moves = [[4, 6], [8, 6], [7, 9], [4, 8], [3, 0, 9], [], [1, 7, 0], [2, 6], [1, 3], [2, 4]]

 def solve(n, pos):
    row = [1 for p in range(10)]
    for l in range(n - 1):
        for p in range(10):
            new_row = [0 for p in range(10)]
            for np in moves[p]:
                new_row[p] += row[np]
            row = new_row
    return row[pos]


 */
public class KnightPathsOnKeypad {


}
