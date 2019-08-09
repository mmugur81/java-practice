package practice;

import java.util.Arrays;
import java.util.Scanner;


public class Solution2 {
    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            String cardNumber = scanner.next();
            boolean isOk = checkChecksum(cardNumber);
            System.out.println(isOk ? "Yes" : "No");
        }
    }

    public static boolean checkChecksum(String ccNumber) {
        int len = ccNumber.length();
        char[] chars = ccNumber.toCharArray();
        int[] numbers = new int[16];

        // reverse
        for (int i = 0; i < len; i++) {
            numbers[len - i - 1] = chars[i] - '0';
        }

        int oddSum = 0;
        int evenSum = 0;
        for (int i = 0; i < 16; i++) {
            if ((i + 1) % 2 == 0) {
                //even
                int even = 2 * numbers[i];
                System.out.print("evn: " + even);
                if (even > 9) {
                    even = even / 10 + even % 10;
                }
                System.out.println(" - replaced with " + even);
                evenSum += even;
            } else {
                //odd
                oddSum += numbers[i];
            }
        }

        boolean result = (oddSum + evenSum) % 10 == 0;

        System.out.println(Arrays.toString(numbers));
        System.out.println("oddSum = " + oddSum + "; evenSum = " + evenSum + " - Result [" + result + "]");

        return result;
    }

}