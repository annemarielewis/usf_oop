// AnnemarieLewis_hw2
// TextProcessor.java
import java.io.*;
import java.util.*;


public class TextProcessor {

    //functionality for accessing stop words:
    private static ArrayList<String> STOP_WORDS;
        static {
            try {
                STOP_WORDS = loadStopWords("stopwords.txt");
            } catch (IOException e) {
                System.err.println("Error loading stop words: " + e.getMessage());
                STOP_WORDS = new ArrayList<>();
            }
        }
//helper function for the above function accessing the stop words
        private static ArrayList<String> loadStopWords(String filePath) throws IOException {
            ArrayList<String> stopWords = new ArrayList<>();
            File file = new File(filePath);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String word = scanner.nextLine().trim().toLowerCase();
                    if (!word.isEmpty()) {
                        stopWords.add(word);
                    }
                }
            }
            return stopWords;
        }

    // Reading the review File to Parse! implementing below helper functions stripPunctuation & isStopWord:
    public static ArrayList<String> parseFile(File textfile) throws IOException {
        ArrayList<String> parsedReview = new ArrayList<>();
        Scanner scanner = new Scanner(textfile);
        while (scanner.hasNext()) {
            String word = scanner.next();
            String cleanedString = stripPunctuation(word).toLowerCase();
            if (!cleanedString.isEmpty() && !isStopWord(cleanedString)) {
                parsedReview.add(cleanedString);
            }
        }
        scanner.close();
        return parsedReview;
    }

    // stripPunctuation: remove punctuation from a word (e.g. "great!" -> "great")
    public static String stripPunctuation(String word) {
        if (word == null) return null;
        StringBuilder result = new StringBuilder(); //string builder to save stack + string pool space
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    // isStopWord: return true if word is in the wordlist of stop words
    public static boolean isStopWord(String word) {
        if (word == null) return false;

        String lowerWord = word.toLowerCase();
        for (String stopword : STOP_WORDS) {
            if (stopword != null && stopword.equalsIgnoreCase(lowerWord)) {
                return true;
            }
        }
        return false;
    }

//    public static ArrayList<String> parseFile(File f, ArrayList<String> stopwords) throws IOException {
//        ArrayList<String> words = new ArrayList<>();
//
//        // treat null stopwords as empty list
//        if (stopwords == null) {
//            stopwords = new ArrayList<>();
//        }
//        // Your code here
//        return words;
//    }
}
