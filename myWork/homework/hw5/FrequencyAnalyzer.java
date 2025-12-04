import java.io.*;
import java.util.*;

/**
 * CS 514: OOP
 * Fall 2025
 * Name: Annemarie Lewis
 *
 * Analyzes character frequencies in text files.
 * This is the first step in Huffman coding - understanding which
 * characters appear most frequently.
 */
public class FrequencyAnalyzer {
    
    /**
     * Counts the frequency of each character in the given text.
     * 
     * @param text the input text to analyze
     * @return HashMap mapping each character to its frequency count
     */

    //step 1!!!!!!! of Huffman Coding-->assess how many times each character is in a file via putting each character in a hashmap of <character, #oftimesitappears> and assess for frequency via looping through the map and updating each charater's key to its new count each time it appears!
    public static HashMap<Character, Integer> countFrequencies(String text) ///return type = HashMap<Character, Integer>
    {
        // TODO: Create a HashMap to store character frequencies
        HashMap<Character, Integer> freqMap = new HashMap<>();
        //character=letter | integer=number of times character appears in file
        // TODO: Loop through each character in text
        for (char c : text.toCharArray()) //text that comes in turn it into an array of the characters so that it can be iterable (string is not iterable).
        {
            // Increase count if exists, otherwise set to 1
            if (freqMap.containsKey(c)) {
                freqMap.put(c, freqMap.get(c) + 1); //puts new value of what's current at the character key to it's current value + 1
            } else {
                freqMap.put(c, 1);
            }
        }
        System.out.println(freqMap);
        return freqMap; //returns HashMap<Character, Integer>
    }

    /**
     * Main method - reads file, counts frequencies, saves to codebook.
     * Usage: java FrequencyAnalyzer input.txt codebook.txt
     * 
     * @param args command line arguments: [input file] [output codebook]
     */
    public static void main(String[] args) {
    if (args.length != 2) {
            System.out.println("Usage: java FrequencyAnalyzer <input file> <codebook file>");
            return;
        }
        
        try {
            // Read input file using provided helper
            String text = FileIOHelper.readFile(args[0]);
            
            // Count frequencies
            HashMap<Character, Integer> frequencies = countFrequencies(text);
            
            // Save to codebook using provided helper
            FileIOHelper.writeFrequencies(args[1], frequencies);
            
            System.out.println("Frequency analysis complete!");
            System.out.println("Unique characters: " + frequencies.size());
            System.out.println("Total characters: " + text.length());
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        //to stay organized in command line prompts:
        System.out.println("we just got frequency and input it in a frequency file, next step is to make a HuffmanTree!");
    }
}
