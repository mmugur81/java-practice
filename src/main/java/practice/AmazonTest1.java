package practice;

import java.util.*;

public class AmazonTest1 {

    private static List<Integer> list(int a, int b) {
        List<Integer> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        return list;
    }

    public static void main(String args[]) throws Exception {
        AmazonTest1 amazonTest = new AmazonTest1();

        List<List<Integer>> forwardRouteList = new ArrayList<>();
        forwardRouteList.add(list(1, 3000));
        forwardRouteList.add(list(2, 5000));
        forwardRouteList.add(list(3, 7000));
        forwardRouteList.add(list(4, 10000));


        List<List<Integer>> returnRouteList = new ArrayList<>();
        returnRouteList.add(list(1, 2000));
        returnRouteList.add(list(2, 3000));
        returnRouteList.add(list(3, 4000));
        returnRouteList.add(list(4, 5000));

        List<List<Integer>> result = amazonTest.optimalUtilization(10000, forwardRouteList, returnRouteList);
        System.out.println(result);
    }

    List<List<Integer>> optimalUtilization(int maxTravelDist,
                                           List<List<Integer>> forwardRouteList,
                                           List<List<Integer>> returnRouteList)
    {
        List<Flight> flights = new ArrayList<>();

        // do combinations
        int maxDistanceFound = 0;
        for (List<Integer> forwardTrip : forwardRouteList) {
            for (List<Integer> returnTrip : returnRouteList) {
                int forwardIndex = forwardTrip.get(0);
                int forwardDistance = forwardTrip.get(1);
                int returnIndex = returnTrip.get(0);
                int returnDistance = returnTrip.get(1);

                int currentDistance = forwardDistance + returnDistance;
                if (currentDistance <= maxTravelDist && currentDistance >= maxDistanceFound) {
                    if (currentDistance > maxDistanceFound) {
                        // clear the list
                        flights.clear();
                    }
                    flights.add(new Flight(forwardIndex, returnIndex));
                    maxDistanceFound = currentDistance;
                }
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        for (Flight flight : flights) {
            result.add(flight.getPair());
        }

        return result;
    }

    class Flight {
        int forwardIndex;
        int returnIndex;

        public Flight(int forwardIndex, int returnIndex) {
            this.forwardIndex = forwardIndex;
            this.returnIndex = returnIndex;
        }

        public List<Integer> getPair() {
            List<Integer> pair = new ArrayList<>();
            pair.add(forwardIndex);
            pair.add(returnIndex);
            return pair;
        }
    }
}