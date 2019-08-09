package practice;

import java.util.*;

public class AmazonTest {

    public static void main(String args[]) throws Exception {
        AmazonTest amazonTest = new AmazonTest();

        List<String> list = new ArrayList<>();
//        list.add("zld 9312");
//        list.add("fp kindle book");
//        list.add("10a echo show");
//        list.add("17g 12 25 6");
//        list.add("abl kindle book");
//        list.add("125 echo dot second generation");

        list.add("t2 13 121");
        list.add("r1 box ape");
        list.add("b4 xi me");
        list.add("br8 eat nim");
        list.add("w1 has uni");
        list.add("f3 52 54");

        List<String> result = amazonTest.prioritizedOrders(6, list);
        System.out.println(result);
    }

    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    public List<String> prioritizedOrders(int numOrders, List<String> orderList)
    {
        Map<String, String> primeOrders = new LinkedHashMap<>();
        Map<String, String> sortedPrimeOrders = new TreeMap<>(new CustomComparator(primeOrders));
        Map<String, String> nonPrimeOrders = new LinkedHashMap<>();

        orderList.forEach(item -> {
            int firstDelimiter = item.indexOf(' ');
            String id = item.substring(0, firstDelimiter);
            String metaData = item.substring(firstDelimiter + 1);

            if (isPrimeMetadata(metaData)) {
                primeOrders.put(id, metaData);
            } else {
                nonPrimeOrders.put(id, metaData);
            }
        });

        //sort prime orders
        sortedPrimeOrders.putAll(primeOrders);

        List<String> result = new ArrayList<>();
        sortedPrimeOrders.forEach((id, metaData) -> result.add(id +" "+metaData));
        nonPrimeOrders.forEach((id, metaData) -> result.add(id +" "+metaData));

        return result;
    }

    class CustomComparator implements Comparator<String> {
        Map<String, String> base;

        public CustomComparator(Map<String, String> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            int compareTo = base.get(a).compareTo(base.get(b));
            if (compareTo == 0) {
                return a.compareTo(b);
            } else {
                return compareTo;
            }
        }
    }

    private boolean isPrimeMetadata(String metaData) {
        String[] tokens = metaData.split(" ");

        for (String token : tokens) {
            try {
                Integer.parseInt(token);
            } catch (NumberFormatException ignored) {
                continue;
            }
            return false;
        }

        return true;
    }

}