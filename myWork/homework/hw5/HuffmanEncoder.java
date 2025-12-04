import java.io.*;
import java.util.*;

/**
 * CS 514: OOP
 * Fall 2025
 * Name: Annemarie Lewis
 *
 * Encodes text files using Huffman coding.
 * Reads plain text and outputs compressed bit strings.
 */
public class HuffmanEncoder {

    /**
     * Encodes text using the provided Huffman codes.
     *
     * @param text the text to encode
     * @param codes map of characters to their Huffman codes
     * @return encoded string of '0's and '1's
     */
    public static String encode(String text, HashMap<Character, String> codes) {
        // TODO: Create a StringBuilder for the result
        // TODO: For each character in text:
        //       - Look up its code in the HashMap
        //       - Append the code to result
        // TODO: Return the encoded string

        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            String code = codes.get(c);
            if (code == null) {
                throw new IllegalArgumentException("No Huffman code found for character: " + c);
            }
            result.append(code);
        }

        return result.toString();
    }

    /**
     * Main method - encodes a file using Huffman codes.
     * Usage: java HuffmanEncoder input.txt codes.txt output.huff
     *
     * @param args command line arguments: [input file] [codes file] [output file]
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java HuffmanEncoder <input file> <codes file> <output file>");
            return;
        }

        try {
            // Read input and codes
            String text = FileIOHelper.readFile(args[0]);
            HashMap<Character, String> codes = FileIOHelper.readCodes(args[1]);

            // Encode
            String encoded = encode(text, codes);

            // Write output
            FileIOHelper.writeFile(args[2], encoded);

            // Report compression
            int originalBits = text.length() * 8;
            int compressedBits = encoded.length();
            double ratio = (double) originalBits / compressedBits;

            System.out.println("Encoding complete!");
            System.out.println("Original: " + originalBits + " bits");
            System.out.println("Compressed: " + compressedBits + " bits");
            System.out.println("Compression ratio: " + String.format("%.2f", ratio) + ":1");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println("Now time to decode the encoded doc!");
    }
}
