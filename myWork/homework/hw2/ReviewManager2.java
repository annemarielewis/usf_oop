// AnnemarieLewis_hw2
import java.io.IOException;
import java.util.ArrayList;

public class ReviewManager2 {
    public static void main(String[] args) throws IOException {

        // create object with pos and neg words passed into sentiment analyzer
        SentimentAnalyzer analyzer = new SentimentAnalyzer("positive-words.txt", "negative-words.txt");

        // Directories of labeled reviews
        File posDir = new File("reviews/pos");
        File negDir = new File("reviews/neg");

        int totalReviews = 0;
        int correctPredictions = 0;

        // Process positive reviews : loop through files, put the contents into arrays, classify the review
        for (File review : posDir.listFiles()) {
            ArrayList<String> words = TextProcessor.parseFile(review);
            String predicted = analyzer.classifyReview(words);

            boolean correct = predicted.equalsIgnoreCase("positive");
            System.out.println("File: " + review.getName() + " | Predicted: " + predicted + " | Actual: Positive | Correct: " + correct);

            totalReviews++;
            if (correct) correctPredictions++;
        }

        // Process negative reviews
        for (File review : negDir.listFiles()) {
            ArrayList<String> words = TextProcessor.parseFile(review);
            String predicted = analyzer.classifyReview(words);

            boolean correct = predicted.equalsIgnoreCase("negative");
            System.out.println("File: " + review.getName() + " | Predicted: " + predicted + " | Actual: Negative | Correct: " + correct);

            totalReviews++;
            if (correct) correctPredictions++;
        }

        // Report final accuracy
        double accuracy = 100.0 * correctPredictions / totalReviews;
        System.out.println("\nTotal Reviews: " + totalReviews);
        System.out.println("Correct Predictions: " + correctPredictions);
        System.out.printf("Accuracy: %.2f%%\n", accuracy);
    }
}
