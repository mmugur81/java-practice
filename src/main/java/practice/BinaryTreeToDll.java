package practice;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Given a Binary Tree (BT), convert it to a Doubly Linked List(DLL)
 * <p>
 * Test case: 1 4 10 20 L 10 30 R 20 40 L 20 60 R
 * Test case: 1 14 0 1 L 0 2 R 1 3 L 1 4 R 2 5 L 2 6 R 3 7 L 3 8 R 4 9 L 4 10 R 5 11 L 5 12 R 6 13 L 6 14 R
 */
public class BinaryTreeToDll {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int testCases = scanner.nextInt();
        for (int i = 0; i < testCases; i++) {
            int nNodes = scanner.nextInt();
            Node node = readTree(nNodes);

            Node nodeList = convertToDoubleLinkedList(node);

            // Left view print - and also setting the left link
            Node lastNode = null;
            while (nodeList != null) {
                nodeList.setLeft(lastNode);
                lastNode = nodeList;
                System.out.print(" " + nodeList.getData());
                nodeList = nodeList.getRight();
            }
            System.out.println("");

            // Right view print
            while (lastNode != null) {
                System.out.print(" " + lastNode.getData());
                lastNode = lastNode.getLeft();
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

            // Check left side in depth
            Node leftTree = convertToDoubleLinkedList(rootNode.getLeft());
            if (leftTree != null) {
                // replace leftNode with leftTree
                leftNode = leftTree;

                //set the correct right link for the last element from leftTree
                Node lastNode = leftNode;
                while (lastNode.getRight() != null) {
                    lastNode = lastNode.getRight();
                }
                lastNode.setRight(centralNode);

            } else {
                // leftNode does not have more levels on left side, so it's alone
                leftNode = new Node();
                leftNode.setData(rootNode.getLeft().getData());
                leftNode.setRight(centralNode);
            }
            //left side will be set after the first traversal from left to right
        }

        //add the right node
        if (rootNode.getRight() != null) {

            // check right side in depth
            Node rightTree = convertToDoubleLinkedList(rootNode.getRight());
            if (rightTree != null) {
                //replace rightNode with rightTree
                rightNode = rightTree;
            } else {
                // rightNode does not have more levels on right side, so it's alone
                rightNode = new Node();
                rightNode.setData(rootNode.getRight().getData());

            }
            //left side will be set after the first traversal from left to right
            centralNode.setRight(rightNode);
        }

        //return the leftmost node
        return leftNode;
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
