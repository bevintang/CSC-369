public class SalesKeySorter extends WritableComparator {
    protected SalesKeySorter () {
        super (SalesKey.class, true);
    }

    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
        SalesKey s1 = (SalesKey) w1;
        SalesKey s2 = (SalesKey) w2;

        return s1.compreTo(s2);
    }
}