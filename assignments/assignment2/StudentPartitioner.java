public class StudentPartitioner extends Partitioner<StudentKey, IntWritable> {
    @Override
    public int getPartition(StudentKey stuKey, Text stuGrade, int numPartitions) {
        return Math.abs(stuKey.getName().hashCode % numPartitions);
    }
}