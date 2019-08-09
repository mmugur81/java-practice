package practice;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Given a Binary Tree (BT), convert it to a Doubly Linked List(DLL)
 */
public class BinaryTreeToDll {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

    }

    /**
     * Reads and builds the tree from standard input
     *
     * @param nNodes no of nodes to read
     * @return the tree (root node)
     */
    public static Node readNode(int nNodes) {
        Map<Integer, Node> nodeMap = new HashMap<>();

        Node node = new Node();

        //todo continue
        return null;
    }

    public static class Node {
        private Node left;
        private Node right;
        private Integer data;

        public Node(Integer data) {
            this.data = data;
        }

        public Node() {

        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Integer getData() {
            return data;
        }

        public void setData(Integer data) {
            this.data = data;
        }
    }
}
