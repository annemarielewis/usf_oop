// AnnemarieLewis_hw2
public class FreqDistTest {
    public static void main(String[] args) {
        FreqDist fd = new FreqDist();

        // Simulating counting some words
        fd.incrementCount("hello");
        fd.incrementCount("world");
        fd.incrementCount("hello");

        fd.printFrequencies();

        // Get total words
        System.out.println("Total words: " + fd.totalWords());  // Expected: 3
    }
}
