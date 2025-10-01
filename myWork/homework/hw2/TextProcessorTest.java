// AnnemarieLewis_hw2
// TextProcessorTest.java
import java.io.*;
import java.util.*;

public class TextProcessorTest {
    public static void main(String[] args) throws IOException {
        System.out.println("Test stripPunctuation:");
        System.out.println(TextProcessor.stripPunctuation("hello!") + " (expected: hello)");
        System.out.println(TextProcessor.stripPunctuation("hi.") + " (expected: hi)");
        System.out.println(TextProcessor.stripPunctuation("!!!!") + " (expected: )");
        System.out.println(TextProcessor.stripPunctuation("hiya!!!") + " (expected: hiya)");
        System.out.println(TextProcessor.stripPunctuation("") + " (expected: )");
        System.out.println(TextProcessor.stripPunctuation(null) + " (expected: null)");

        System.out.println("Test isStopWord:");
        System.out.println(TextProcessor.isStopWord("the") + " (expected: true)");
        System.out.println(TextProcessor.isStopWord("The") + " (expected: true)");
        System.out.println(TextProcessor.isStopWord("fun") + " (expected: false)");
        System.out.println(TextProcessor.isStopWord("hello") + " (expected: false)");
        System.out.println(TextProcessor.isStopWord("but") + " (expected: true)");
        System.out.println(TextProcessor.isStopWord(null) + " (expected: false)");

        // Create a file in Java + write content to a file on disc.
        File testFile = new File("sample.txt");
        PrintWriter pw = new PrintWriter(testFile);
        pw.println("This is a great movie, love it!");
        pw.close();

        // TO DO: Review this block of code; this will test the parseFile method
        // this shows how to delete the temporary file created within this method
        ArrayList<String> words = TextProcessor.parseFile(testFile);
        System.out.println("parseFile() Test: Words from file: " + words + " (expected: [great, movie, love])"); // Producing expected output!
        testFile.delete();
    }
    //tests for FreqDist in sepaarte test file
}
