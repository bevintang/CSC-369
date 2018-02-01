import java.util.*;
import java.io.*;

public class SalesTime {
    /* Helper -- Check file name. Exit if DNE */
    private static String getFileName(String[] args) {
        if (args.length != 1){
            System.out.println("Usage: java SalesTime [filename]");
            System.exit(1); 
        }
        return args[0];
    }

    /* Helper -- Tries to initialize scanner. Exit upon failure */
    private static Scanner initScanner(File inputFile) throws FileNotFoundException {
        Scanner myScanner = new Scanner(System.in);
        // Try to make a Scanner for the file
        try {
            myScanner = new Scanner(inputFile);
            return myScanner;
        }
        catch (FileNotFoundException noFile) {
            System.out.printf("Cannot find file: '%s'\n", inputFile.toString());
            System.exit(1);
        }
        return myScanner;
    }

    /* Helper -- input a new entry into the ArrayList map */
    private static void addToMap(TreeMap<String, ArrayList<String>> map, String line) {
        String[] tokens = line.split(",");
        if (tokens.length != 3)
            return;

        String key = tokens[0];
        String value = tokens[1] + " " + tokens[2];
        ArrayList<String> values = map.get(key);

        if (values == null){
            values = new ArrayList<String>();
            map.put(key, values);
        }
        values.add(value);
    }

    /* Helper -- populates passed map with contents of passed scanner */
    private static void populateMap(TreeMap<String, ArrayList<String>> map, Scanner s){
        while (s.hasNextLine()){
            addToMap(map, s.nextLine());
        }
    }

    private static class TimeComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            // Grab only the time, not the ID (this is at spot 0)
            // Then split the time by colon (:)
            String[] tokens1 = (s1.split(" "))[0].split(":");
            String[] tokens2 = (s2.split(" "))[0].split(":");
            int result = 0;
            for (int i = 0; i < tokens1.length; i++){
                int num1 = Integer.parseInt(tokens1[i]);
                int num2 = Integer.parseInt(tokens2[i]);

                result = num1 - num2;

                if (result == 0)
                    continue;
            }
            return result;
        }
    }

    /* Helper -- prints the entries in the map */
    public static void printMap(TreeMap<String, ArrayList<String>> map) {
        for (Map.Entry e : map.entrySet()){
            String result = "";
            ArrayList<String> values = (ArrayList<String>) e.getValue();
            values.sort(new TimeComparator());

            // Begin Printing
            System.out.print(e.getKey() + " ");
            for (int i = 0; i < values.size(); i++){
                result += values.get(i) + ", ";
            }
            System.out.println(result.substring(0, result.length() - 2));
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = getFileName(args);
        File inputFile = new File(filename);
        Scanner fileScanner = initScanner(inputFile);
        TreeMap<String, ArrayList<String>> salesMap = new TreeMap<String, ArrayList<String>>();

        populateMap(salesMap, fileScanner);
        printMap(salesMap);
    }
}