public class SalesGrouper extends WritableComparator {
    protected SalesGrouper () {
        super (SalesKey.class, true);
    }

    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
        SalesKey s1 = (SalesKey) w1;
        SalesKey s2 = (SalesKey) w2;

        return s1.getDate().compareTo(s2.getDate());
    }
}