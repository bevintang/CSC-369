public class SalesKey implements Writable, WritableComarable<SalesKey> {
    private final Text date = new Text();
    private final Text timeId = new Text(); // combination of time and id

    public SalesKey() {}

    public SalesKey(String date, String time, String id) {
        this.date.set(date);
        this.timdId.set(time + " " + id);
    }

    public String getDate() {
        return date.toString();
    }

    public String getTimeId() {
        return timeId.toString();
    }

    @Override
    public void write (DataOutput out) throws IOException {
        date.write(out);
        timeId.write(out);
    }

    @Override
    public void readFields(DataIntput in) throws IOException {
        date.readFields(in);
        timeId.readFields(in);
    }

    private int compareTimes(String s1, String s2){
        // Grab only the time, not the ID (this is at spot 0)
        // Then split the time by colon (:)
        String[] tokens1 = (s1.split(" "))[0].split(":");
        String[] tokens2 = (s2.split(" "))[0].split(":");
        int result = 0;
        for (int i = 0; i < tokens1.length; i++){
            int num1 = Integer.parseInt(tokens1[i]);
            int num2 = Integer.parseInt(tokens2[i]);

            result = num1 - num2;

            if (result == 0)
                continue;
        }
        return result;
    }

    @Override
    public int compareTo(SalesKey other) {
        int result = this.date.compareTo(other.date);
        if (result == 0)
            result = compareTimes(this.timeId, other.timeId);
        
        return result;
    }
}