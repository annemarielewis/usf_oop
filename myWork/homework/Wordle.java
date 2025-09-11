import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Wordle {

    public static Scanner getFileHandle(String filename) {
        Scanner sc = null;
        try {
            File inputFile = new File(filename); // creates a Java object that represents the path to a file
            sc = new Scanner(inputFile);// This actually tries to open and read the file. --> If the file doesn’t exist,
                                        // this line will throw a FileNotFoundException.

        } catch (FileNotFoundException e) {
            System.out.println("No such file: " + e.getMessage());
        }
        return sc; // Returns the Scanner (or null)

    }
    // ^getFileHandle=a method on how to find + read files w/in Wordle class
    // ______________________________________________________________________________

    public static void testGetFileHandle() {
        // TEST, with a file inknown existent: searched for the file we want
        Scanner s1 = getFileHandle("wordleWords.txt");
        if (s1 != null) {
            System.out.println("Contents of wordleWords.txt:");

            while (s1.hasNextLine()) {
                System.out.println(s1.nextLine());
            }
        } else {
            System.out.println("Could not open wordleWords.txt");
        }

        // TEST, with a known non-existent file: searches for "bogus" file
        Scanner s2 = getFileHandle("bogus");
        if (s2 == null) {
            System.out.println("As expected, file 'bogus' was not found.");
        } else {
            System.out.println("Program not working as expected, or someone since made a file with title bogus.");
        }

        // //-->functionality: using user-input into console for testing:
        System.out.print("Enter the filename to open: ");
        Scanner userInput = new Scanner(System.in); // path to console input
        String filename = userInput.nextLine(); // what user typed into console

        Scanner fileScanner = getFileHandle(filename); // path to file
        if (fileScanner != null) {
            System.out.println("Successfully opened: " + filename);

            // print contents of file
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
                userInput.close();
            }
        } else {
            System.out.println("This file does not exist:" + filename);
        }
    }
    // _________________
    /*
     * return a string representing the user's guess. If a letter is in the secret
     * word, and it's in the right place,
     * put a ! at that position. If a letter is in the secret word, but it's in the
     * wrong place, put a 0. And if
     * if's not present, put a X.
     */

    public static String compareWords(String secretWord, String guess) {
        String result = new String();
        // you do this part
        return result;
    }

    /*
     * this method should call compareWords with multiple different inputs (same
     * word,
     * same letters but different position, completely different) to make aure it
     * works.
     * Return true if compareTwoWords does everything right, and false otherwise.
     */
    public static boolean testCompareWords() {

    }

    /*
     * Select a random word from the file of legal words.
     * Use the ArrayList to store the words, and then Random to
     * select one.
     */
    public static String selectRandomWord(String wordfile) {
        ArrayList<String> words = new ArrayList<String>(); // makes an array named words
        Scanner fileScanner = getFileHandle(wordfile); // executes getFileHandle method on wordfile
        // passing wordfile as a parameter into getFileHandle
    }

    /*
     * testSelectRandomWord. This should call selectRandomWord and display the word
     * that was selected.
     */

    public static void testSelectRandomWord() {

    }

    /*
     * Our primary method.
     */
    public static void playGame() {
        // Read a word from the file.
        // set a counter for the number of guesses so far.
        // loop:
        // prompt the user for a word.
        // Display the result.
        // Show the letters guessed so far

    }

    public static void main(String args[]) {

        testGetFileHandle();

    }

}
