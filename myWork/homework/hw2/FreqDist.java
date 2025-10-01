// AnnemarieLewis_hw2
// FreqDist.java
import java.util.*;

public class FreqDist {
    private ArrayList<String> words; //new array of strings
    private ArrayList<Integer> counts; // new array of ints

   //constructor : initializes the arrays as empty
    public FreqDist() {
        words = new ArrayList<>();
        counts = new ArrayList<>();
    }

    // TODO: increment word count :
    //  each word will be popped in to words array only once--and its corresponding count at same index of the counts array
    public void incrementCount(String word) {
        if (word == null) return;
        int index = 0;
        for (String w : words) {
            if (w.equals(word)) {
                // Word exists — increment count at the same index
                counts.set(index, counts.get(index) + 1); //counts[index] = counts[index] + 1;
                return;
            }
            index++;
        }
        // Word not found, add it with count 1
        words.add(word);
        counts.add(1);
    }

    // TODO: return count (0 if missing)
    //a word can be looked up for its count using the logic created above -->
    // the word at word[i] = shows up as many times as the integer at count[i]
    public int getCount(String word) {
        if (word == null) return 0;
        int index = 0;
        for (String w : words) {
            if (w.equals(word)) {
                return counts.get(index); // return the count value at the index
            }
            index++;
        }
        return 0; // word not found, return default 0
    }

    // TODO: return total number of words counted
    public int totalWords() {
        int total = 0;
        for (int count : counts) {
            total = total + count;
        }
        // After adding all values held in count array, return the total:
        return total;
    }

    //for testing in FreqDistTest:
    public void printFrequencies() {
        System.out.println("Words:  " + words);
        System.out.println("Counts: " + counts);
    }
}

