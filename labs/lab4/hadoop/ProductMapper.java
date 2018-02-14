import java.io.*;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class ProductMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
    public static final int DEFAULT_N = 10;
    private int n = DEFAULT_N;
    private TreeSet<Product> topN = new TreeSet<Product>();

    @Override
    public void map (LongWritable key, Text value, Context context) throws
            IOException, InterruptedException {

        String line = value.toString();
        String[] tokens = line.split(",");
        if (tokens.length != 3)
            return;

        // Construct a new product            
        int id = Integer.parseInt(tokens[0]);
        String name = tokens[1].trim();
        double price = Double.parseDouble(tokens[2]);
        Product p = new Product(id, name, price);
        topN.add(p);

        // Keep only the top N elements
        if (topN.size() > n) {
            pollFirst();
        }
    }

    @Override
    protected void setup (Context context) throws IOException, InterruptedException {
        this.n = context.getConfiguration().getInt("N", DEFAULT_N);
    }

    @Override
    protected void cleanup (Context context) throws IOException, InterruptedException {
        for (Product p: topN) {
            context.write(NullWritable, new Text(p.toString()));
        }
    }

}