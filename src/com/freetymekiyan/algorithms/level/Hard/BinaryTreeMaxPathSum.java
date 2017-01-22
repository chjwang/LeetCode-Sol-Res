package com.freetymekiyan.algorithms.level.Hard;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

/**
 * Given a binary tree, find the maximum getPath sum.
 *
 * The getPath may start and end at any node in the tree.
 *
 * For example:
 * Given the below binary tree,
 *
 *        1
 *       / \
 *      2   3
 * Return 6.
 *
 * Tags: Tree, DFS
 *
 */
class BinaryTreeMaxPathSum {
    private int maxSum;

    public static void main(String[] args) {
    }

    /**
     *  思路：
     1. 与常规path sum不同，这题的path sum可以不起始于root，也可以不终止于leaf。

     2. 这样的path可以归纳为两种情况：
     (1) root->leaf path中的一段：即题目例子中的1-2或1-3。
     (2) 两个节点之间经过它们lowest common ancestor (LCA)的path：即题目中的2-1-3。

     3. 显然top-down的递归并不适用于这题，因为对于类型(2)的path，它的path sum同时取决于LCA左右sub path的最大值。
     而这样的path sum是无法通过top-down传递来获取的。

     4. 所以这里可以采用bottom-up的递归方法:
     对于节点x:
         定义PS1(x)为从x出发向leaf方向的第一类path中最大的path sum。
         定义PS2(x)为以x为LCA的第二类path中的最大path sum。
     显然如果我们求得所有节点x的PS1 & PS2，其中的最大值必然就是要求的max getPath sum。

     5. 如何求PS1(x)、PS2(x)?
     (1) PS1(x) 、PS2(x)至少应该不小于x.val，因为x自己就可以作为一个单节点path。
     (2) PS1(x) 、 PS2(x)可以从PS1(x.left)和PS1(x.right)来求得：
     PS1(x) = max [ max(PS1(x.left), 0), max(PS1(x.right), 0) ] + x.val
     PS2(x) = max(PS1(x.left), 0) + max(PS1(x.right), 0) + x.val
     注意这里并不需要PS2(x.left) 以及 PS2(x.right) 因为子节点的2型path无法和父节点构成新的path。

     6. 需要返回PS1(x)以供上层的节点计算其PS1 & PS2, PS2 will participate globally updating getPath maxSum.

     7. 对于leaf节点：PS1(x) = PS2(x) = x.val.

     * @param root
     * @return
     */
    public int maxPathSum(TreeNode root) {
        int maxSum = Integer.MIN_VALUE;
        int ps1_root = findMaxPathSum(root);
        return maxSum;
    }

    /**
     * 总结：

     1. 解题的关键在于对path的分类以及应用top-down recursion。

     2. 注意的细节是当x.left或x.right的第一类path sum为负数时，则这类path不应该传递到x。

     3. 代码并没有特殊处理leaf节点的情况。因为当root.left和root.right都不存在时，ps1_left和ps1_right均为初始值0。
     于是ps1_root = ps2_root = root.val。

     * @param root
     * @return
     */
    int findMaxPathSum(TreeNode root) {
        if (root == null) return 0;

        int ps1_left = 0, ps1_right = 0;

        if (root.left != null)
            ps1_left = Math.max(findMaxPathSum(root.left),0);
        if (root.right != null)
            ps1_right = Math.max(findMaxPathSum(root.right), 0);

        int ps1_root = Math.max(ps1_left, ps1_right) + root.val;

        int ps2_root = ps1_left + ps1_right + root.val;

        maxSum = Math.max(maxSum, Math.max(ps1_root, ps2_root));

        return ps1_root;
    }

    /**
     * recursion
     *
     复杂度
     时间 O(b^(h+1)-1) 空间 O(h) 递归栈空间 对于二叉树b=2

     思路
     首先我们分析一下对于指定某个节点为根时，最大的路径和有可能是哪些情况。
         第一种是左子树的路径加上当前节点，
         第二种是右子树的路径加上当前节点，
         第三种是左右子树的路径加上当前节点（相当于一条横跨当前节点的路径），
         第四种是self only的路径。

     乍一看似乎以此为条件进行自下而上递归就行了，然而这四种情况只是用来计算以当前节点根的最大路径，
     如果当前节点上面还有节点，那它的父节点是不能累加第三种情况的。

     所以我们要计算两个最大值:
        一个是当前节点下最大路径和，
        另一个是如果要连接父节点时最大的路径和。
     我们用二者中的较大者更新全局最大量，用后者返回递归值就行了。
     */
    int findMaxPathSum2(TreeNode root) {
        if(root == null) return 0;

        int left = findMaxPathSum2(root.left);
        int right = findMaxPathSum2(root.right);

        //连接父节点的最大路径是一、二、四这三种情况的最大值
        int currSum = Math.max(Math.max(left + root.val, right + root.val), root.val);

        //当前节点的最大路径是一、二、三、四这四种情况的最大值
        int currMax = Math.max(currSum, left + right + root.val);

        //用当前最大来更新全局最大
        maxSum = Math.max(currMax, maxSum);

        return currSum;
    }

    /**
     * follow up: Binary Tree Maximum Path Without Consecutive Nodes
     *
     * 有点像House Robber III, 但是House Robber规定Path只能从root开始，大大简化了题目难度。
     * 这道题的path可以是任意的，上下左右都可以，唯一的限制就是不能使用连续node。

     [Solution]
     思路其实就是dfs，和leetcode 124是一样的，只不过在递归回parent的时候return两个值:
        一个是包含当前节点的最大值，另一个是不包含当前节点的最大值。
     因为这道题的状态非常复杂，既要考虑包不包含，还需要考虑正负数，外加这类dfs递归题向来想不清楚，所以是道非常有价值的题，值得反复做。

     [Note]
     1. return两个值需要用个class来wrap
     2. 注意和0的比较，在extend current node的时候，只有当left.without或right.without大于0的情况下才能extend,
     否则即使当前节点为负数，继续extend只会让path sum更小。
     3. 而当不考虑当前节点的时候，最大path sum也不一定就是left.with或者right.with. left.without和right.without
     一样也可以是不考虑当前节点情况下的最大path sum.
     *
     */
    int result = 0;

    public int maxPathSum_followup(TreeNode root) {
        if (root == null)
            return 0;
        dfs(root);
        return result;
    }


    private Pair dfs(TreeNode root) {
        if (root == null)
            return null;
        if (root.left == null && root.right == null)
            return new Pair(root.val, 0);

        Pair left = dfs(root.left);
        Pair right = dfs(root.right);

        // with curr node
        int curr = root.val;
        if (left.without > 0) curr += left.without;
        if (right.without > 0) curr += right.without;
        result = Math.max(result, curr);

        // without curr node
        curr = 0;
        curr += Math.max(0, Math.max(left.with, left.without));
        curr += Math.max(0, Math.max(right.with, right.without));
        result = Math.max(result, curr);

        // generate return pair
        int withCurr = root.val;
        withCurr += Math.max(0, Math.max(left.without, right.without));
        int withoutCurr = 0;
        withoutCurr = Math.max(withoutCurr, Math.max(left.with, left.without));
        withoutCurr = Math.max(withoutCurr, Math.max(right.with, right.without));

        return new Pair(withCurr, withoutCurr);
    }

    private class Pair {
        int with;
        int without;
        Pair(int with, int without) {
            this.with = with;
            this.without = without;
        }
    }
}