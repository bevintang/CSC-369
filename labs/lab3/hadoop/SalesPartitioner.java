public class SalesPartitioner extends Partitioner<SalesKey, Text> {
    @Override
    public int getPartition(SalesKey key, Text value, int numPartitions) {
        int hash = key.getDate().hashCode();
        
        return hash % numPartitions;
    }
}