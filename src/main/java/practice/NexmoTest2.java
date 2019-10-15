package practice;

import java.util.*;


public class NexmoTest2 {
    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);

        NexmoTest2 test = new NexmoTest2();

        List<Integer> numbers = new ArrayList<>();

        while (scanner.hasNextInt()) {
            numbers.add(scanner.nextInt());
        }
        scanner.close();

        int[] x = new int[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            x[i] = numbers.get(i);
        }

        System.out.println(test.solution(x, null));
    }

    public int solution(int[] X, int[] Y) {
        // write your code in Java SE 8
        Set<Integer> xCoords = new TreeSet<>();
        for (int x : X) {
            xCoords.add(x);
        }

        Integer first = null;
        Integer second = null;
        int maxDistance = 0;
        for (Integer xVal : xCoords) {
            if (first == null) {
                first = xVal;
                continue;
            } else {
                second = xVal;
            }

            //compute distance
            int distance = second - first;
            maxDistance = Math.max(maxDistance, distance);

            //move next
            first = second;
        }

        return maxDistance;
    }

}