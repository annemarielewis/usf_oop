// ReviewManager.java
import java.io.*;
import java.util.*;

public class ReviewManager {

    private ArrayList<String> stopwords;
    private ArrayList<String> positiveWords;
    private ArrayList<String> negativeWords;
    // Updated: including the SentimentAnalyzer
    private SentimentAnalyzer analyzer;

    public ReviewManager() {
        stopwords = new ArrayList<>();
        positiveWords = new ArrayList<>();
        negativeWords = new ArrayList<>();
    }

    /**
     * Load words from a file into an ArrayList.
     */
    private ArrayList<String> loadWords(String filename) {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim().toLowerCase();
                if (!line.isEmpty()) {
                    words.add(line);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
        return words;
    }

    /**
     * Loads stopwords, positive words, and negative words.
     */
    public void loadWordLists() {
        //file path may differ depending on your code structure
        stopwords = loadWords("src/stopwords.txt");
        positiveWords = loadWords("src/positive-words.txt");
        negativeWords = loadWords("src/negative-words.txt");
        System.out.println("Loaded word lists.");

        // Updated: instantiate SentimentAnalyzer here
        analyzer = new SentimentAnalyzer(positiveWords, negativeWords);
    }

    //MAJOR UPDATE
    public String classifyReview(File reviewFile) {
        /* Note for CS514:  Use the FreqDist class to build a frequency distribution
         * Our goal here is to get a set of cleaned words from this review
         *   We will use the classes that we created: FreqDist and TextProcessor
         * For TextProcessor, we will use the parseFile method -it's static type,
         *  so we don't need to instantiate an object of type TextProcessor to use the
         *
         * Note: we will use the FreqDist class object to help us get the counts for neg/pos
         *  words. No further implementation from your end is required;
         */
        int positiveCount = 0;
        int negativeCount = 0;
        int score = 0;

        FreqDist fd = new FreqDist();
        ArrayList<String> words;

        try {
            words = TextProcessor.parseFile(reviewFile, stopwords);
        } catch (IOException e) {
            System.out.println("Error reading file: " + reviewFile.getName());
            // we couldn't read the file, so report it as unclassified
            return "UNCLASSIFIED";
        }

        // build the freq distribution
        for (String w : words) {
            fd.incrementCount(w);
        }
        return analyzer.analyze(fd);
    }

    //UPDATED:
    /**
     * Test all reviews in a directory (pos/ or neg/).
     */
    public void testReviews(String folderName, String actualLabel) {
        File folder = new File(folderName);
        File[] files = folder.listFiles();

        int correct = 0;
        int total = 0;

        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    String prediction = classifyReview(f);
                    System.out.println("Review: " + f.getName());
                    System.out.println("Prediction: " + prediction +
                            " | Actual: " + actualLabel);

                    if (prediction.toLowerCase().equals(actualLabel.toLowerCase())) {
                        System.out.println("Correct\n");
                        correct++;
                    } else {
                        System.out.println("Incorrect\n");
                    }
                    total++;
                }
            }
            //Updated: Adding else condition if directory is empty
        } else {
            System.out.println("No files in folder: " + folderName);
        }
        
        System.out.println("Folder: " + folderName);
        System.out.println("Correct: " + correct + "/" + total);

        if (total > 0) {
            double accuracy = (100.0 * correct) / total;
            System.out.println("Accuracy: " + accuracy + "%");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        ReviewManager rm = new ReviewManager();
        rm.loadWordLists();

        // Note for CS514: This is already implemented - please review
        // and see if your understanding
        // is correct
        rm.testReviews("src/reviews/pos", "POSITIVE");
        rm.testReviews("src/reviews/neg", "NEGATIVE");
    }
}
