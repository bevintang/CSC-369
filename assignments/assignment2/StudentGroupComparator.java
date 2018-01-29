public class StudentGroupComparator extends WritableComparator {
    public StudentGroupComparator() {
        super(StudentKey.class, true);
    }

    @Override
    public int compare(WritableComparable o1, WritableComparable o2) {
        StudentKey stu1 = (StudentKey) o1;
        StudentKey stu2 = (StudentKey) o2;

        return o1.getName().compareTo(o2.getName);
    }
}