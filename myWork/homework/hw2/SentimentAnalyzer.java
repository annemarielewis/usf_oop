// AnnemarieLewis_hw2
// SentimentAnalyzer.java

import java.io.*;
import java.util.*;

    public class SentimentAnalyzer {
        private ArrayList<String> positiveWords;
        private ArrayList<String> negativeWords;

       //constructor of array passed in
        public SentimentAnalyzer(ArrayList<String> pos, ArrayList<String> neg) {
            this.positiveWords = pos;
            this.negativeWords = neg;}

            //constructor of files passed in
        public SentimentAnalyzer(String posFilePath, String negFilePath) throws IOException {
            this.positiveWords = loadWordsFromFile(posFilePath);
            this.negativeWords = loadWordsFromFile(negFilePath);
        }

        //helper function for reading the file, ^ for constructor above
        private ArrayList<String> loadWordsFromFile(String filePath) throws IOException {
            ArrayList<String> words = new ArrayList<>();
            File file = new File(filePath);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String word = scanner.nextLine().trim().toLowerCase();
                    if (!word.isEmpty()) {
                        words.add(word);
                    }
                }
            }
            return words;
        }

//method for score
        public int computeScore(ArrayList<String> words) {
            int score = 0;
            for (String word : words) {
                if (positiveWords.contains(word)) {
                    score++;
                } else if (negativeWords.contains(word)) {
                    score--;
                }
            }
            return score;
        }

//method for review type
        // classify review
        public String classifyReview(ArrayList<String> words) {
            int score = computeScore(words);
            if (score > 0) {
                return "Positive";
            } else if (score < 0) {
                return "Negative";
            } else {
                return "Neutral";
            }
        }

    //Updated: This is the missing Link!
    // NOte to CS514: please see the inclusion of the "analyze" method
    // this will guide you to determine a positve/negative preview using
    //  word count. Please Note: this is implement for you! This is used in
    // ReviewManger only
    public String analyze(FreqDist fd) {
        int positiveCount = 0;
        for (String pw : positiveWords) {
            positiveCount += fd.getCount(pw);
        }
        int negativeCount = 0;
        for (String nw : negativeWords) {
            negativeCount += fd.getCount(nw);
        }
        if (positiveCount > negativeCount) {
            return "Positive";
        }
        if (negativeCount > positiveCount) {
            return "Negative";
        }
        return "Neutral";
    }
}
