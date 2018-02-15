import org.apache.log4j.Logger;
import org.apache.hadoop.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class ProductDriver extends Configured implements Tool {
    private static final Logger THE_LOGGER = Logger.getLogger(SalesDriver.class);

    public int run (String[] args) throws Exception {
        Job job = Job.getInstance();
        job.getConfiguration().setInt("N", n);
        job.setJarByClass(ProductDriver.class);
        job.setJobName("MostExpensiveDriver");
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setMapperClass(ProductMapper.class);
        job.setReducerClass(ProductReducer.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        boolean status = job.waitForCompletion(true);
        THE_LOGGER.info("run(): status=" + status);
        return status ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2){
            throw new IllegalArgumentException("usage: <input> <output>");
        }

        THE_LOGGER.info("inputDir = " + args[0]);
        THE_LOGGER.info("outputDir = " + args[1]);
        int returnStatus = ToolRunner.run(new SalesDriver(), args);
        THE_LOGGER.info("returnStatus = " + returnStatus);

        System.exit(returnStatus);
    }
}