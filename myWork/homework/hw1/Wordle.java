import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet; //implemented hash functionality in removeRepeatedLetters without
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

//--> Picked #8 (color) for the bonus

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
            System.out.println("wordleWords.txt exists! And here are the words it contains: ");
            while (s1.hasNextLine()) {
                String line = s1.nextLine();
                System.out.println(line);
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

        // // //-->functionality: using user-input into console for testing:
        // System.out.print(
        // "Sorry bogus was not found! That's bogus! ;( But to confirm: please enter the
        // filename you'd like to open and have a random word selected from! :) ");
        // Scanner userInput = new Scanner(System.in); // path to console input
        // String filename = userInput.nextLine(); // what user typed into console

        // Scanner fileScanner = getFileHandle(filename); // path to file
        // if (fileScanner != null) {
        // System.out.println("Successfully opened: " + filename + " here are the
        // contents of " + filename);

        // // print contents of file
        // while (fileScanner.hasNextLine()) {
        // System.out.println(fileScanner.nextLine());
        // // userInput.close();
        // }
        // } else {
        // System.out.println("This file does not exist:" + filename);
        // }

    }

    // _________________

    public static String compareWords(String secretWord, String guess) {
        String result = "";

        final String RESET = "\u001B[0m"; // resetting color
        final String GREEN = "\u001B[32m"; // For correct letter and position
        final String YELLOW = "\u001B[33m"; // For letter in word but wrong position
        final String RED = "\u001B[31m"; // For letter not in word

        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i); // Extracting the character at position i from the guess. Stores it in
                                              // variable guessChar.

            if (i < secretWord.length() && guessChar == secretWord.charAt(i)) {
                // Correct letter, correct position (checking characters at same index (i) of
                // each word)
                result += GREEN + guessChar + RESET; // result = result + guessChar, and guessChar=green before being
                                                     // reset;
                // prefer seeing actual character instead of "!" (if that's okay!)
            } else if (secretWord.indexOf(guessChar) != -1) {
                // Letter exists somewhere else in secret word^googled indexOf functionality
                result += YELLOW + "O" + RESET; // result = result + !;
            } else {
                // Letter not in secret word
                result += RED + "X" + RESET;
            }
        }
        // System.out.println(result);
        return result;
    }

    public static void testCompareWords() {
        System.out.println("Running compareWords tests...");

        // Test 1: exact match
        System.out.println("Test 1: " + compareWords("apple", "apple")); // Expected: apple

        // Test 2: some correct, some wrong position
        System.out.println("Test 2: " + compareWords("apple", "algae")); // Example: a!-!e

        // Test 3: no matching letters
        System.out.println("Test 3: " + compareWords("apple", "truck")); // Expected: -----

        // Test 7: guess longer than secret
        System.out.println("Test 7: " + compareWords("cat", "catch")); // Expected: cat--

        System.out.println("Tests complete.");
    }

    public static String selectRandomWord(String wordfile) {
        ArrayList<String> words = new ArrayList<String>(); // makes an array named words
        Scanner fileScanner = getFileHandle(wordfile); // executes getFileHandle method on wordfile-->wordfile is passed
                                                       // as a parameter in getFileHandle (as the filename argument),
                                                       // AND SAVES IT AS "FILESCANNER" SCANNER VARIABLE
        if (fileScanner == null) {
            return "Error: File not found.";
        }
        while (fileScanner.hasNextLine()) {
            String eachword = fileScanner.nextLine(); // Reads the next full line from the file and stores it in the
                                                      // variable eachword.
            words.add(eachword); // eachword 's value changes every cyclye through the while loop, going through
                                 // every word and storing it to teh array'
        }
        System.out.println(words);

        // Random word generation:
        Random random = new Random();
        String randomWord = words.get(random.nextInt(words.size()));
        System.out.println("random word: " + randomWord);
        return randomWord;
    }

    public static void testSelectRandomWord() {
        // Valid filename test
        String validFile = "wordleWords.txt";
        String validResultTest = selectRandomWord(validFile);
        System.out.println("Valid file test--Random word: " + validResultTest);
        // why does test give same ranodm word as selectRandomWord?
        // Isn't it re-executing selectRandomWord anew?

        // Invalid filename test
        String invalidFile = "nonexistentFile.txt";
        String invalidResultTest = selectRandomWord(invalidFile);
        System.out.println("\nInvalid file test, should show 'file not found':" + invalidResultTest);
    }

    /*
     * Our primary method.
     */

    public static void playGame() {
        String wordfile = "wordleWords.txt";
        String secretWord = selectRandomWord(wordfile);
        Scanner scanner = new Scanner(System.in);

        int maxGuesses = 6;
        int guessCount = 1;
        String guessedLetters = "";

        System.out.println("Welcome to the game! Try to guess the word.");
        System.out.println("You have " + maxGuesses + " guesses.");
        while (guessCount <= maxGuesses) {
            System.out.print("\nEnter your guess #" + (guessCount) + ": ");
            String guess = scanner.nextLine().toLowerCase();// puts guess to lowercase
            String result = compareWords(secretWord, guess);
            System.out.println("Result: " + result);
            guessedLetters += guess; // guessedLetters = guessedLetters + guess
            System.out.println("Letters guessed so far: " + removeRepeatedLetters(guessedLetters));
            guessCount = guessCount + 1;
            if (guess.equals(secretWord)) {
                System.out.println("\n🎉 You guessed it! You win!");
                return;
            }
        }
        System.out.println("\nYou've used all your guesses. The word was: " + secretWord);
    }

    public static String removeRepeatedLetters(String guessedLetters) {

        String uniqueLetters = "";

        for (int i = 0; i < guessedLetters.length(); i++) {
            char c = guessedLetters.charAt(i);
            if (c != ' ' && !uniqueLetters.contains(c + "")) {
                uniqueLetters += c;
            }
        }

        return uniqueLetters;
    }

    public static void main(String args[]) {

        testGetFileHandle();
        selectRandomWord("wordleWords.txt");
        testSelectRandomWord();
        testCompareWords();
        System.out.println("compareWords method tested directly from main method:");
        System.out.println(compareWords("cat", "hat"));
        playGame();
    }
}