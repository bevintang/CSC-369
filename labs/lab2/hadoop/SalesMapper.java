import java.io.*;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class SalesMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    public void map (LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {

        // Split line of input into tokens
        String stringVal = value.toString().trim();
        String[] tokens = stringVal.split(",");

        // Check if input is valid
        if (tokens.length != 2)
            return;

        // Write to context if input is valid
        Text newKey = new Text(tokens[0]);
        IntWritable newVal = new IntWritable(Integer.parseInt(tokens[1]));
        context.write(newKey, newVal);
    }
}
