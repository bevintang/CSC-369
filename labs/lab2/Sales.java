import java.util.*;
import java.io.*;

public class Sales {
    private int numSales = 0;
    private static TreeMap<String, Integer> map = new TreeMap<String, Integer>();
    private static File inputFile;
    private static Scanner fileScanner;

    /* Helper -- Check file name. Exit if DNE */
    private static String getFileName(String[] args) {
        if (args.length != 1){
            System.out.println("Usage: java Sales [filename]");
            System.exit(1); 
        }
        return args[0];
    }

    public static void main (String[] args) throws FileNotFoundException {
        inputFile = new File(getFileName(args));

        // Try to make a Scanner for the file
        try {
            fileScanner = new Scanner(inputFile);
        }
        catch (FileNotFoundException noFile) {
            System.out.printf("Cannot find file: '%s'\n", inputFile.toString());
            System.out.println("Usage: java Sales [filename]");
            System.exit(1);
        }

        // Insert each as an entry in a TreeMap
        while (fileScanner.hasNextLine()) {
            StringTokenizer tokenizer = new StringTokenizer(fileScanner.nextLine().trim(), ",");
            if (tokenizer.countTokens() != 2)
                continue;

            // increment entry
            String key = tokenizer.nextToken();
            int inc = Integer.parseInt(tokenizer.nextToken());

            if (map.get(key) == null){
                map.put(key, inc);
            }
            else {
                map.put(key, map.get(key) + inc);
            }
        }

        // Print Keys and Entries
        for (Map.Entry e : map.entrySet())
            System.out.println("" + e.getKey() + " " + e.getValue());


    }
}