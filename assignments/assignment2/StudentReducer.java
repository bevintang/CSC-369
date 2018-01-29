public class StudentReducer extends Reducer <StudentKey, Text, Text, Text> {

    @Override
    protected void reduce (StudentKey stukey, Iterable<Text> grades, 
            Context context) throws IOException, InterruptException {

        // Make key include both name and id
        String key = stukey.getName() + ", " + stukey.getId() + ", ";

        // Construct value associated with key
        String result = "";
        for (Text grade : grades) {
            result += grade + ", ";
        }
        // Remove ", " from the end
        result = result.substring(0, result.length-2);

        context.write(key, new Text(result));
    }
}