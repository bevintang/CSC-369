public class StudentKey implements Writable, WritableComparable<StudentKey> {
    private final Text name = new Text();
    private final IntWritable id = new IntWritable();
    private final Text grade = new Text();

    public StudentKey() {}

    public StudentKey(String name, int id, String grade, String className) {
        this.name.set(name);
        this.id.set(id);
        String gradeClass = "(" + grade + ", " + className + ")";
        this.grade.set(grade);
    }

    public Text getName(){
        return name;
    }

    public IntWritable getId(){
        return id;
    }

    public Text getGrade(){
        return grade;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        name.write(out);
        id.write(out);
        grade.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        name.readFields(in);
        id.readFields(in);
        grade.readFields(in);
    }

    @Override
    public int compareTo(StudentKey other) {
        int nameComp = name.compareTo(other.name);
        if (nameComp == 0)
            return grade.compareTo(other.grade);
        return nameComp;
    }
}