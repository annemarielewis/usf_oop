import java.io.*;
import java.util.*;

/**
 * CS 514: OOP
 * Fall 2025
 * Name: <ENTER YOUR NAME HERE!>
 *
 * Decodes Huffman-encoded files back to original text.
 * Uses the Huffman tree to traverse and decode bit strings.
 */
public class HuffmanDecoder {
    
    /**
     * Decodes a Huffman-encoded bit string using the tree.
     * 
     * @param encoded string of '0's and '1's to decode
     * @param root root of the Huffman tree
     * @return decoded text
     */
    public static String decode(String encoded, HuffmanNode root) {
        // TODO: Create a StringBuilder for the result
        // TODO: Create a pointer starting at root
        // TODO: For each character ('0' or '1') in encoded string:
        //       - If '0', move pointer to left child
        //       - If '1', move pointer to right child
        //       - If pointer is now at a leaf:
        //           * Append the leaf's character to result
        //           * Reset pointer to root
        // TODO: Return the decoded text
        
        return null; // Replace with your implementation
    }
    
    /**
     * Main method - decodes a Huffman-encoded file.
     * Usage: java HuffmanDecoder encoded.huff frequencies.txt output.txt
     * 
     * @param args command line arguments: [encoded file] [frequency file] [output file]
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java HuffmanDecoder <encoded file> <frequency file> <output file>");
            return;
        }
        
        try {
            // Read encoded data and frequencies
            String encoded = FileIOHelper.readFile(args[0]);
            HashMap<Character, Integer> frequencies = FileIOHelper.readFrequencies(args[1]);
            
            // Rebuild tree
            HuffmanTree tree = new HuffmanTree();
            tree.buildTree(frequencies);
            
            // Decode
            String decoded = decode(encoded, tree.getRoot());
            
            // Write output
            FileIOHelper.writeFile(args[2], decoded);
            
            System.out.println("Decoding complete!");
            System.out.println("Decoded " + decoded.length() + " characters");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
