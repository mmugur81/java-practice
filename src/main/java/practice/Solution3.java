package practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Solution3 {
    public static void main(String[] args) throws Exception {
        //read input
        StringBuilder paragraph = new StringBuilder(256);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            paragraph.append(line);

            if (line.equals("[x]")) {
                break;
            }
        }

        String scrubbed = scrubText(paragraph.toString());
        System.out.println(scrubbed);
    }

    private static String scrubText(String text) {
        //System.out.println(text);

        //find all matches for '<script>'
        List<Integer> start = new ArrayList<>();
        int index = text.indexOf("<script", 0);

        while (index >= 0) {
            start.add(index);
            index = text.indexOf("<script", index + 1);
        }

        //find all matches for '</script>'
        List<Integer> end = new ArrayList<>();
        index = text.indexOf("</script>", 0);
        while (index >= 0) {
            end.add(index + 9);
            index = text.indexOf("</script>", index + 1);
        }

        //System.out.println(start);
        //System.out.println(end);

        for (int i = start.size() - 1; i >= 0; i--) {
            int iStart = start.get(i);
            int iEnd = (i >= end.size()) ? text.length() - 1 : end.get(i);

            String substring = text.substring(iStart, iEnd);
            //System.out.println("Substring [" + substring + "]");

            text = text.replace(substring, "");
        }

        return text;
    }

}