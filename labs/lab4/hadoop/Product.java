public class Product implements Comparable <Product> {
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

    public String toString() {
        return "" + id + "," + name + "," + price;
    }

    public int compareTo(Object o) {
        Product other = (Product) o;
        int result = compareDouble(price, other.getPrice());
        if (result == 0) {
            result = id - other.getId();
        }
        return result;
    }

    private int compareDouble(double d1, double d2) {
        if (d1 < d2)
            return -1;
        else if (d1 > d2)
            return 1;
        return 0;
    }
}