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
                nodeList.left = lastNode;
                lastNode = nodeList;
                System.out.print(" " + nodeList.data);
                nodeList = nodeList.right;
            }
            System.out.println("");

            // Right view print
            while (lastNode != null) {
                System.out.print(" " + lastNode.data);
                lastNode = lastNode.left;
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
                node.left = secondNode;
            } else {
                node.right = secondNode;
            }
        }

        return firstNode;
    }

    public static Node convertToDoubleLinkedList(Node rootNode) {
        Node leftNode = null;
        Node centralNode = null;
        Node rightNode = null;

        //add the central node
        centralNode = new Node(rootNode.data);

        //add the left node
        if (rootNode.left != null) {

            // Check left side in depth
            Node leftTree = convertToDoubleLinkedList(rootNode.left);
            if (leftTree != null) {
                // replace leftNode with leftTree
                leftNode = leftTree;

                //set the correct right link for the last element from leftTree
                Node lastNode = leftNode;
                while (lastNode.right != null) {
                    lastNode = lastNode.right;
                }
                lastNode.right = centralNode;

            } else {
                // leftNode does not have more levels on left side, so it's alone
                leftNode = new Node(rootNode.left.data);
                leftNode.right = centralNode;
            }
            //left side will be set after the first traversal from left to right
        }

        //add the right node
        if (rootNode.right != null) {

            // check right side in depth
            Node rightTree = convertToDoubleLinkedList(rootNode.right);
            if (rightTree != null) {
                //replace rightNode with rightTree
                rightNode = rightTree;
            } else {
                // rightNode does not have more levels on right side, so it's alone
                rightNode = new Node(rootNode.right.data);

            }
            //left side will be set after the first traversal from left to right
            centralNode.right = rightNode;
        }

        //return the leftmost node
        return leftNode;
    }

    public static class Node {
        Node left;
        Node right;
        int data;

        Node(int d) {
            data = d;
            left = right = null;
        }
    }
}
