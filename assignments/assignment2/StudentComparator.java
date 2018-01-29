public class StudentComparator extends WritableComparator {
    protected StudentComparator() {
        super(StudentKey.class, true);
    }

    @Override
    public int compare(WritableComparable o1, WritableComparable o2) {
        StudentKey stu1 = (StudentKey) o1;
        StudentKey stu2 = (StudentKey) o2;

        return o1.compareTo(o2);
    }
}