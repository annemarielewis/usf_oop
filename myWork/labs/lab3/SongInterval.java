public class SongInterval {
    //instance variable
    long length;

    //constructor
    public SongInterval(long length) {

        this.length = length;
    }

    //getter
    public long getLength() {
        return length;
    }

    //setter (added condition to make the concept make more sense to me)
    public void setLength(long length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must be non-negative");
        }
        this.length = length;
    }

    //method for time to string
    public String toString() {
        long min = length / 60;
        long sec = length % 60;
        return String.format("%d:%d", min, sec);
    }
}
