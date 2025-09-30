// TextProcessor.java
import java.io.*;
import java.util.*;


public class TextProcessor {
    // stop words to remove for review processing
    private static final ArrayList<String> STOP_WORDS = new ArrayList<>(
            Arrays.asList(
                    "a", "an", "the", "and", "or", "but", "of", "to", "in", "on", "about", "above", "after", "again",
                    "against", "all", "am", "any", "are", "aren't", "as", "at", "be", "because", "been", "before",
                    "being", "below", "between", "both", "by", "can't", "cannot", "could", "couldn't", "did", "didn't",
                    "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few", "for", "from", "further",
                    "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her",
                    "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll",
                    "i'm", "i've", "if", "into", "is", "isn't", "it", "it's", "its", "itself", "let's", "me", "more",
                    "most", "mustn't", "my", "myself", "no", "nor", "not", "off", "once", "only", "other", "ought",
                    "our", "ours", "ourselves", "out", "over", "own", "same", "shan't", "she", "she'd", "she'll",
                    "she's", "should", "shouldn't", "so", "some", "such", "than", "that", "that's", "their", "theirs",
                    "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're",
                    "they've", "this", "those", "through", "too", "under", "until", "up", "very", "was", "wasn't", "we",
                    "we'd", "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when", "when's", "where",
                    "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "won't", "would",
                    "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves"
            )
    );

    // Reading the File to Parse, implementing below helper functions stripPunctuation & isStopWord:
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

    // stripPunctuatio: remove punctuation from a word (e.g. "great!" -> "great")
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
