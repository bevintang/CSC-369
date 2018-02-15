import java.util.*;
import java.io.*;

public class MostExpensive {

    public static int compareDouble(double d1, double d2) {
        if (d1 < d2)
            return -1;
        else if (d1 > d2)
            return 1;
        return 0;
    }

    private static class Product implements Comparable {
        private int id;
        private String name;
        private double price;

        public Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int compareTo(Object o) {
            Product other = (Product) o;
            int result = compareDouble(price, other.getPrice());
            if (result == 0) {
                result = id - other.getId();
            }
            return result;
        }
    }

    private static class ProductComparator implements Comparator<Product> {
        public int compare(Product p1, Product p2) {
            int result = compareDouble(p1.getPrice(), p2.getPrice());

            if (result == 0) {
                result = p1.getId() - p2.getId();
            }

            return result;
        }
    }

    /* Helper -- Check file name. Exit if DNE */
    private static String getFileName(String[] args) {
        if (args.length != 1){
            System.out.println("Usage: java MostExpensive [INPUT FILE]");
            System.exit(1); 
        }
        return args[0];
    }

    public static void main (String[] args) throws FileNotFoundException {
        TreeSet<Product> products = new TreeSet<Product>();
        String filename = getFileName(args);
        File inputFile = new File(filename);
        Scanner fileScanner = new Scanner(inputFile);

        // Add all elements to the tree set
        while (fileScanner.hasNextLine()) {
            String[] tokens = fileScanner.nextLine().split(",");
            if (tokens.length != 3)
                continue;

            int id = Integer.parseInt(tokens[0]);
            String name = tokens[1].trim();
            double price = Double.parseDouble(tokens[2]);
            Product newProduct = new Product(id, name, price);
            products.add(newProduct);
        }

        // Decipher number of entries
        int limit = 10;
        if (limit > products.size())
            limit = products.size();

        // Print results
        for (int i = 0; i < limit; i++) {
            Product p = products.pollLast();
            System.out.printf("%4d, %32s, %.2f\n", p.getId(), p.getName(), p.getPrice());
        }
    }
}






