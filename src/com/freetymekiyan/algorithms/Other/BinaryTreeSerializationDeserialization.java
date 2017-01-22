package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Serialize and Deserialize Binary Tree
 */
public class BinaryTreeSerializationDeserialization {

    /**
     * pre-order DFS
     *
     * @param root
     * @return
     */
    public String serialize(TreeNode root) {
        if (root == null)
            return null;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            TreeNode h = stack.pop();
            if (h != null) {
                sb.append(h.val + ",");
                stack.push(h.right);
                stack.push(h.left);
            } else {
                sb.append("#,");
            }
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * pre-order deserialization recursive
      */
    public TreeNode deserialize(String data) {
        if(data == null)
            return null;

        int[] index = {0}; // current node index, using array to capture return value
        String[] arr = data.split(",");

        return preOrderHelper(arr, index);
    }

    public TreeNode preOrderHelper(String[] arr, int[] index) {
        if (arr[index[0]].equals("#")) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(arr[index[0]]));

        index[0] = index[0] + 1; //move index to left child subtree
        root.left = preOrderHelper(arr, index);

        index[0] = index[0] + 1; //move index to right child subtree
        root.right = preOrderHelper(arr, index);

        return root;
    }

    /**
     * Level order traversal
     */
    public String serializeLevel(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        LinkedList<TreeNode> queue = new LinkedList<>();

        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                sb.append(String.valueOf(node.val) + ",");
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                sb.append("#,");
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * level order deserialization, iterative
     */
    public TreeNode deserializeLevel(String data) {
        if (data == null || data.length() == 0)
            return null;

        String[] arr = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(arr[0]));

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) continue;

            node.left = arr[i].equals("#") ? null: new TreeNode(Integer.parseInt(arr[i]));
            queue.offer(node.left);
            i++;

            node.right = arr[i].equals("#") ? null: new TreeNode(Integer.parseInt(arr[i]));
            queue.offer(node.right);
            i++;
        }
        return root;
    }

    // Iterative DFS.
    public String serializeDfs(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                sb.append(String.valueOf(node.val));
                sb.append(',');
                stack.push(node);
                node = node.left;
            } else {
                sb.append("#,");
                node = stack.pop();
                node = node.right;
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    // Iterative DFS.
    public TreeNode deserializeDfs(String data) {
        if (data.length() == 0) return null;
        String[] arr = data.split(",");
        int n = arr.length;
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.valueOf(arr[0]));
        TreeNode x = root;
        stack.push(x);

        int i = 1;
        while (i < n) {
            while (i < n && !arr[i].equals("#")) {
                x.left = new TreeNode(Integer.valueOf(arr[i++]));
                x = x.left;
                stack.push(x);
            }
            while (i < n && arr[i].equals("#")) {
                x = stack.pop();
                i++;
            }
            if (i < n) {
                x.right = new TreeNode(Integer.valueOf(arr[i++]));
                x = x.right;
                stack.push(x);
            }
        }
        return root;
    }

    /**
     * DFS recursive
     */
    public String serializeDfs2(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        dfs(root, sb);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void dfs(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("#,");
            return;
        }
        sb.append(String.valueOf(node.val)).append(",");
        dfs(node.left, sb);
        dfs(node.right, sb);
    }

    /**
     * DFS recursive
     */
    public TreeNode deserializeDfs2(String data) {
        String[] node = data.split(",");
        int[] d = {0};
        return dfs(node, d);
    }

    private TreeNode dfs(String[] arr, int[] d) {
        TreeNode node = null;
        if (arr[d[0]].equals("#")) {
            d[0]++;
        } else {
            node = new TreeNode(Integer.valueOf(arr[d[0]]));
            d[0]++;
            node.left = dfs(arr, d);
            node.right = dfs(arr, d);
        }
        return node;
    }

    /**
     * BFS
     */
    public String serializeLevel2(TreeNode root) {
        if (root == null) return "";
        Queue<TreeNode> q = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        q.offer(root);
        sb.append(String.valueOf(root.val));
        sb.append(',');

        while (!q.isEmpty()) {
            TreeNode node = q.poll();

            if (node.left == null)
                sb.append("#,");
            else {
                q.offer(node.left);
                sb.append(String.valueOf(node.left.val));
                sb.append(',');
            }

            if (node.right == null)
                sb.append("#,");
            else {
                q.offer(node.right);
                sb.append(String.valueOf(node.right.val));
                sb.append(',');
            }
        }
        return sb.toString();
    }

    public TreeNode deserializeLevel2(String data) {
        if (data.length() == 0) return null;

        String[] arr = data.split(",");
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.valueOf(arr[0]));
        q.offer(root);

        int i = 1;
        while (!q.isEmpty()) {
            Queue<TreeNode> nextQ = new LinkedList<>();
            while (!q.isEmpty()) {
                TreeNode node = q.poll();
                if (arr[i].equals("#"))
                    node.left = null;
                else {
                    node.left = new TreeNode(Integer.valueOf(arr[i]));
                    nextQ.offer(node.left);
                }
                i++;

                if (arr[i].equals("#"))
                    node.right = null;
                else {
                    node.right = new TreeNode(Integer.valueOf(arr[i]));
                    nextQ.offer(node.right);
                }
                i++;
            }
            q = nextQ;
        }
        return root;
    }

    public String serializeRecursive(TreeNode root) {
        // If current node is NULL, store marker
        if (root == null) return "#";

        StringBuffer sb = new StringBuffer(root.val + ",");

        // Else, store current node and recur for its children
        String s1 = serializeRecursive(root.left);
        sb.append(s1);

        String s2 = serializeRecursive(root.right);
        sb.append(s2);

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public TreeNode deserializeRecursive(String data) {
        if (data.length() == 0) return null;

        String[] arr = data.split(",");
        int[] index = {0};
        return deserializeRecursiveHelper(arr, index);
    }

    public TreeNode deserializeRecursiveHelper(String[] arr, int[] index) {
        if (arr[index[0]].equals("#"))
            return null;

        int val = Integer.parseInt(arr[index[0]]);

        // Else create node with this item and recur for children
        TreeNode root = new TreeNode(val);

        index[0] = index[0] + 1;
        root.left = deserializeRecursiveHelper(arr, index);

        index[0] = index[0] + 1;
        root.right = deserializeRecursiveHelper(arr, index);

        return root;
    }

    /**
     * Construct BST from pre-order traversal
     *
     * @param pre
     * @return
     */
    public TreeNode constrcutBST(int pre[]) {
        // The first element of pre[] is always root
        TreeNode root = new TreeNode(pre[0]);
        Stack<TreeNode> s = new Stack<>();

        // Push root
        s.push(root);

        int size = pre.length;
        // Iterate through rest of the size-1 items of given preorder array
        for (int i = 1; i < size; ++i) {
            TreeNode temp = null;

            /* Keep on popping while the next value is greater than stack's top value. */
            while (!s.isEmpty() && pre[i] > s.peek().val) {
                temp = s.pop();
            }

            // Make this greater value as the right child and push it to the stack
            if (temp != null) {
                temp.right = new TreeNode(pre[i]);
                s.push(temp.right);
            }

            // If the next value is less than the stack's top value, make this value
            // as the left child of the stack's top node. Push the new node to stack
            else {
                temp = s.peek();
                temp.left = new TreeNode(pre[i]);
                s.push(temp.left);
            }
        }

        return root;
    }

    // A recursive function to construct BST from pre[]. preIndex is used
    // to keep track of index in pre[].
    public TreeNode constrcutBST2(int pre[]) {
        int preIndex = 0;
        return constructTreeUtil(pre, index, pre[0], Integer.MIN_VALUE,
                Integer.MAX_VALUE, pre.length);
    }
    TreeNode constructTreeUtil(int pre[], Index preIndex, int key,
                           int min, int max, int size) {
        // Base case
        if (preIndex.index >= size) {
            return null;
        }

        TreeNode root = null;

        // If current element of pre[] is in range, then only it is part of current subtree
        if (key > min && key < max) {

            // Allocate memory for root of this subtree and increment *preIndex
            root = new TreeNode(key);
            preIndex.index = preIndex.index + 1;

            if (preIndex.index < size) {

                // Contruct the subtree under root
                // All nodes which are in range {min .. key} will go in left
                // subtree, and first such node will be root of left subtree.
                root.left = constructTreeUtil(pre, preIndex, pre[preIndex.index],
                        min, key, size);

                // All nodes which are in range {key..max} will go in right
                // subtree, and first such node will be root of right subtree.
                root.right = constructTreeUtil(pre, preIndex, pre[preIndex.index],
                        key, max, size);
            }
        }

        return root;
    }

    class Index {

        int index = 0;
    }
    Index index = new Index();

}
