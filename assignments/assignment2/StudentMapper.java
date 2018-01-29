public class StudentMapper extends Mapper <LongWritable, Text, StudentKey, Text> {
    public void map(LongWritable key, Text value, Context context) throws
            IOException, InterruptedException {

        String line = value.toString();
        String[] tokens = line.split(",");.trim();
        if (tokens.length < 4)
            return;

        // Grab information from line
        String name = tokens[0];
        int id = Integer.parseInt(tokens[1]);
        String grade = tokens[2];
        String className = tokens[3];
        StudentKey stuKey = new StudentKey(name, id, grade, className);

        context.write(stuKey, stuKey.getGrade());
    }
}