// SentimentAnalyzer.java
import java.util.*;

public class SentimentAnalyzer {
    private ArrayList<String> positiveWords;
    private ArrayList<String> negativeWords;

    public SentimentAnalyzer(ArrayList<String> pos, ArrayList<String> neg) {
        this.positiveWords = pos;
        this.negativeWords = neg;
    }

    // TODO: compute score (+1 for positive, -1 for negative)
    public int computeScore(ArrayList<String> words) {
        // Your code here
        return 0;
    }

    // TODO: classify review
    public String classifyReview(ArrayList<String> words) {
        // Your code here
        return "Neutral";
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
