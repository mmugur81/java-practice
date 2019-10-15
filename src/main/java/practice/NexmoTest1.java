package practice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class NexmoTest1 {
    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);

        NexmoTest1 test = new NexmoTest1();

        System.out.println(test.solution(scanner.nextInt()));
    }

    public int solution(int N) {
        // write your code in Java SE 8
        List<Integer> digits = new ArrayList<>();

        int number = N;
        while (number >= 10) {
            int digit = number % 10;
            digits.add(digit);
            number = number / 10;
        }
        digits.add(number);

        digits.sort(new DescComparator());

        int result = 0;
        for (int i = 0; i < digits.size(); i++) {
            result += digits.get(i) * Math.pow(10, digits.size() - i - 1);
        }

        return result;
    }

    class DescComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return -a.compareTo(b);
        }
    }

}