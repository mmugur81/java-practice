package practice;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Given a Binary Tree (BT), convert it to a Doubly Linked List(DLL)
 * <p>
 * Test case: 1 4 10 20 L 10 30 R 20 40 L 20 60 R
 */
public class BinaryTreeToDll {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int testCases = scanner.nextInt();
        for (int i = 0; i < testCases; i++) {
            int nNodes = scanner.nextInt();
            Node node = readTree(nNodes);

            Node dll = convertToDoubleLinkedList(node);

            while (dll != null) {
                System.out.print(" " + dll.getData());
                dll = dll.getRight();
            }

            System.out.println("\n");
        }
    }

    /**
     * Reads and builds the tree from standard input
     *
     * @param nNodes no of nodes to read
     * @return the tree (root node)
     */
    public static Node readTree(int nNodes) {
        Map<Integer, Node> nodeMap = new HashMap<>();
        Node firstNode = null;

        for (int i = 0; i < nNodes; i++) {
            int first = scanner.nextInt();
            int second = scanner.nextInt();
            boolean isLeft = scanner.next().equals("L");

            nodeMap.putIfAbsent(first, new Node(first));
            Node node = nodeMap.get(first);

            if (i == 0) {
                firstNode = node;
            }

            Node secondNode = new Node(second);
            nodeMap.put(second, secondNode);
            if (isLeft) {
                node.setLeft(secondNode);
            } else {
                node.setRight(secondNode);
            }
        }

        return firstNode;
    }

    public static Node convertToDoubleLinkedList(Node rootNode) {
        Node leftNode = null;
        Node centralNode = null;
        Node rightNode = null;

        //add the central node
        centralNode = new Node(rootNode.getData());

        //add the left node
        if (rootNode.getLeft() != null) {
            leftNode = new Node();
            leftNode.setData(centralNode.getLeft().getData());
            leftNode.setRight(centralNode);
            centralNode.setLeft(leftNode);
            convertToDoubleLinkedList(rootNode.getLeft());
        }

        //add the right node
        if (rootNode.getRight() != null) {
            rightNode = new Node();
            rightNode.setData(rootNode.getRight().getData());
            rightNode.setLeft(rootNode);
            centralNode.setRight(rightNode);
            convertToDoubleLinkedList(rootNode.getRight());
        }

        //return the leftmost node
        return leftNode;
    }

    public static Node getOuterLeft(Node tree) {
        Node node = tree;
        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
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
