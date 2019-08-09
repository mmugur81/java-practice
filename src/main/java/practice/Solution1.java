package practice;

import java.util.Scanner;


public class Solution1 {
    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        fizzBuzz(n);
    }

    public static void fizzBuzz(int n) {
        for (int i = 1; i <= n; i++) {
            boolean divided3 = i % 3 == 0;
            boolean divided5 = i % 5 == 0;

            if (divided3 && divided5) {
                System.out.println("FizzBuzz");
            } else if (divided3) {
                System.out.println("Fizz");
            } else if (divided5) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }
}