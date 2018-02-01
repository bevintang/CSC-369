import java.io.*;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class SalesMapper extends Mapper<LongWritable, Text, SalesKey, Text> {
    public void map (LongWritable key, Text value, Context context) throws
            IOException, InterruptedException {

        String line = value.toString();
        String[] tokens = line.split(",");
        if (tokens.length != 3)
            return;

        SalesKey compositeKey = new SalesKey(tokens[0], tokens[1], tokens[2]);
        context.write(compositeKey, new Text(compositeKey.getTimeId()));            
    }
}