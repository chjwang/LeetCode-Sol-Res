package com.freetymekiyan.algorithms.Other;


import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

public class Amplitude {
    int getAmplitude(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return dfs(root, root.val, root.val);
        }
    }

    int dfs(TreeNode root, int minn, int maxx) {
        minn = Math.min(minn, root.val);
        maxx = Math.max(maxx, root.val);
        if (root.left != null && root.right != null) {
            return Math.max(dfs(root.left, minn, maxx), dfs(root.right, minn, maxx));
        }
        if (root.left != null) {
            return dfs(root.left, minn, maxx);
        }
        if (root.right != null) {
            return dfs(root.right, minn, maxx);
        }
        return maxx - minn;
    }
}
