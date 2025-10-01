// AnnemarieLewis_hw2
import java.io.IOException;

public class SentimentAnalyzerTest {
    public static void main(String[] args) throws IOException {

        // Using file-based constructor
        SentimentAnalyzer analyzefile = new SentimentAnalyzer("positive-words.txt", "negative-words.txt");
        ArrayList<String> test2 = new ArrayList<>(Arrays.asList("good", "great"));
        System.out.println("Test 2 score, from file: " + analyzefile.computeScore(test2));
        //__________________________
        // Sample positive and negative word lists
        ArrayList<String> posWords = new ArrayList<>(Arrays.asList("good", "great", "amazing", "fun"));
        ArrayList<String> negWords = new ArrayList<>(Arrays.asList("bad", "terrible", "boring", "awful"));

        // Create analyzer with word lists
        SentimentAnalyzer analyzer = new SentimentAnalyzer(posWords, negWords);

        // Test 1: Positive words only
        ArrayList<String> test1 = new ArrayList<>(Arrays.asList("good", "great", "amazing"));
        System.out.println("Test 1 score: " + analyzer.computeScore(test1) + " (expected: 3)" + analyzer.classifyReview(test1));



        // Test 2: Negative words only
        ArrayList<String> test6 = new ArrayList<>(Arrays.asList("bad", "boring"));
        System.out.println("Test 6 score: " + analyzer.computeScore(test2) + " (expected: -2)" + analyzer.classifyReview(test6));

        // Test 3: Mixed words
        ArrayList<String> test3 = new ArrayList<>(Arrays.asList("fun", "boring", "great", "awful"));
        System.out.println("Test 3 score: " + analyzer.computeScore(test3) + " (expected: 0)" + analyzer.classifyReview(test3));

        // Test 4: Words not in either list
        ArrayList<String> test4 = new ArrayList<>(Arrays.asList("tree", "sky", "hello"));
        System.out.println("Test 4 score: " + analyzer.computeScore(test4) + " (expected: 0)" + analyzer.classifyReview(test4));

        // Test 5: Null or empty input
        ArrayList<String> test5 = new ArrayList<>();
        System.out.println("Test 5 score: " + analyzer.computeScore(test5) + " (expected: 0)" + analyzer.classifyReview(test5));
    }
}



