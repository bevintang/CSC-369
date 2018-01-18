import java.io.*;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class SalesReducer extends Reducer<Text, 
    IntWritable, Text, IntWritable> {

    public void reduce(Text date, Iterable<IntWritable> totalSales, 
        Context context) throws IOException, InterruptedException {

        int sum = 0;
        for (IntWritable sales : totalSales)
            sum += sales.get();

        context.write(date, new IntWritable(sum));
    }
}
