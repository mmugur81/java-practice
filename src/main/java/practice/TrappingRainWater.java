package practice;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import static java.lang.Math.min;

/**
 * Problem: https://practice.geeksforgeeks.org/problems/trapping-rain-water/0
 * Test case: 1 10 2 0 1 0 4 0 0 3 0 0
 */
public class TrappingRainWater {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String... args) {
        System.out.println("Trapping Rain Water\n");

        int testCases = scanner.nextInt();

        Map<Integer, Set<Integer>> occurrences = new TreeMap<>(new DescComparator());

        for (int i = 0; i < testCases; i++) {
            // Read
            int nBars = scanner.nextInt();
            int[] bars = new int[nBars];

            for (int index = 0; index < nBars; index++) {
                int value = scanner.nextInt();
                occurrences.putIfAbsent(value, new LinkedHashSet<>());
                Set<Integer> indices = occurrences.get(value);
                indices.add(index);

                bars[index] = value;
            }

            int area = countArea(nBars, bars, occurrences);
            System.out.println("\n" + area);
        }

    }

    private static int countArea(int nBars, int[] bars, Map<Integer, Set<Integer>> occurrences) {
        int totalArea = 0; //result

        int currentIndex = 0;
        int endIndex = nBars - 1;//until we find the highest one

        /// start cycle -----------------------------------------------
        while (currentIndex < endIndex) {
            int currentValue = bars[currentIndex];
            //remove the value's index from the map
            occurrences.get(currentValue).remove(currentIndex);
            if (occurrences.get(currentValue).isEmpty()) {
                //end of all cycles
                break;
            }

            // find the next value equal or higher than currentValue, with index > currentIndex, and if not found, get the highest there is
            Set<Integer> mapKeys = occurrences.keySet();

            int endValue = -1;
            for (Integer mapKey : mapKeys) {
                if (mapKey >= currentValue) {
                    //found
                    endValue = mapKey;
                    break;
                }
            }
            if (endValue == -1) {
                //not found higher than currentValue; get the highest there is (meaning the first element in mapKeys, with first index)
                endValue = mapKeys.iterator().next();
            }
            endIndex = occurrences.get(endValue).iterator().next();

            // remove everything from occurrences, where index <= endIndex
            for (Integer mapKey : mapKeys) {
                Set<Integer> indices = occurrences.get(mapKey);
                for (Integer index : indices) {
                    if (index <= endIndex) {
                        indices.remove(index);
                    }
                }
                if (indices.isEmpty()) {
                    occurrences.remove(mapKey);
                }
            }

            // process endIndex
            int maxHeight = min(bars[currentIndex], bars[endIndex]);
            int currentArea = 0;
            for (int i = 1; i < nBars - 1; i++) {
                currentArea += (bars[i] < maxHeight) ? maxHeight - bars[i] : 0;
            }
            totalArea += currentArea;

            // remove endIndex from set
            occurrences.get(endValue).remove(endIndex);
            if (occurrences.get(endValue).isEmpty()) {
                //remove the whole vale because all of its indices have been processed
                occurrences.remove(endValue);
            }

            //move to next index
            currentIndex = endIndex;
        }
        // end cycle ----------------------------------------------------------------------------------

        return totalArea;
    }

    private static class DescComparator implements Comparator<Integer> {
        public int compare(Integer o1, Integer o2) {
            return Integer.compare(o2, o1);
        }
    }

}
