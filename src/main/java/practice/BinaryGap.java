package practice;

import java.util.Scanner;

public class BinaryGap {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int number = scanner.nextInt();
        int binaryGap = findBinaryGap(number);
        System.out.println("Longest gap for " + number + " = " + binaryGap);
    }

    public static int findBinaryGap(int number) {
        String str = Integer.toBinaryString(number);

        int longestGap = 0;
        int currentGap = 0;
        for (char c : str.toCharArray()) {
            if (c == '1') {
                longestGap = Math.max(longestGap, currentGap);
                currentGap = 0;
            } else {
                currentGap++;
            }
        }

        return Math.max(longestGap, currentGap);
    }

}