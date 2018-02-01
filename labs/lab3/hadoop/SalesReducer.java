import java.io.*;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.*;

public class SalesReducer extends 
        org.apache.hadoop.mapreduce.Reducer<SalesKey, Text, Text, Text> {

    @Override
    protected void reduce (SalesKey key, Iterable<Text> values, Context context) 
            throws IOException, InterruptedException {

        String result = "";
        for (Text value : values)
            result += value.toString() + ", ";

        result = result.substring(0, result.length()-2);

        context.write(new Text(key.getDate()), new Text(result));
    }
}