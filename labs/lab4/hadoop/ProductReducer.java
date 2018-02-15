import java.io.*;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class ProductReducer extends
        org.apache.hadoop.mapreduce.Reducer<NullWritable, Text, NullWritable, Text> {

    private int n = ProductMapper.DEFAULT_N;
    private TreeSet<Product> topN = new TreeSet<Product>();

    @Override
    public void reduce (NullWritable key, Iterable<Text> values, Context context) throws
            IOException, InterruptedException {

        // Add a Product to the TreeSet
        for (Text value : values) {
            String[] tokens = value.toString().split(",");
            int id = Integer.parseInt(tokens[0]);
            String name = tokens[1];
            double price = Double.parseDouble(tokens[2]);
            Product p = new Product(id, name, price);
            topN.add(p);

            // Only keep the top n Products
            if (topN.size() > n) {
                topN.pollFirst();
            }
        }

        // Write the Products in the TreeSet to the disk
        for (int i = 0; i < n; i++) {
            Product curP = topN.pollLast();
            context.write(NullWritable.get(), new Text(curP.toString()));
        }

    }

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.n = context.getConfiguration().getInt("N", ProductMapper.DEFAULT_N);
    }
}
