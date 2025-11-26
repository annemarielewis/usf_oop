import java.util.*;

/**
 * CS 514: OOP
 * Fall 2025
 * Name: Annemarie Lewis
 *
 * Builds and manages a Huffman tree for text compression.
 * The tree is constructed using a priority queue algorithm.
 */
public class HuffmanTree {
    
    private HuffmanNode root;
    private HashMap<Character, String> codes;
    
    /**
     * Builds the Huffman tree from character frequencies.
     * Uses a priority queue to repeatedly combine the two lowest-frequency nodes.
     * 
     * @param frequencies map of characters to their frequencies
     */
    public void buildTree(HashMap<Character, Integer> frequencies) {
        // TODO: Create a PriorityQueue of HuffmanNodes
        //takes huffmannode class/object in from being able to access it from the other file (where its class is defined publically)
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        // TODO: For each character/frequency pair, create a leaf node and add to queue
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet())
        //Map.Entry is a built-in Java type-->Map.Entry<Character, Integer> is the type that the entry variable is: Every variable must have a declared type
        //entrySet() is a built-in Java Map method
        {
            char c = entry.getKey(); //getting key
            int freq = entry.getValue(); //getting value
            HuffmanNode leaf = new HuffmanNode(c, freq);
            pq.add(leaf); //all letters will be leafs in the tree, by design for it to work! Non-leafs (internal nodes) will be percentage values only of the added up frequencies of their children leafs!
        }
        // Edge case: if there are no characters, tree is empty
        if (pq.isEmpty()) {
            root = null;
            return;
        }
        //> why isn't it just:
        //         if (pq.isEmpty()) {
        //            return;
        //        } -->roots is a necessary instance variable for this huffmantree class because the encoding/decoding depends on it and without it we will get thrown run time errors

        // TODO: While queue has more than 1 node:
        //       - Remove two lowest-frequency nodes
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();   // smallest frequency --> “Take the smallest-frequency HuffmanNode out of the priority queue and store it in the variable left.”
            HuffmanNode right = pq.poll();  // next smallest
            //       - Create new parent node with combined frequency
            int combinedFreq = left.getFrequency() + right.getFrequency();
            HuffmanNode parent = new HuffmanNode(combinedFreq, left, right); //// <-- RIGHT HERE: left and right nodes are attached as children to a new parent node
            //       - Add parent back to queue
            pq.add(parent); //add and offer behave same here, so used add just because!
            // TODO: The last remaining node is the root
            root = pq.poll();
        }

    /**
     * Generates Huffman codes for all characters by traversing the tree.
     * Left = '0', Right = '1'
     */
    public void generateCodes() {
        codes = new HashMap<>();
        if (root != null) {
            generateCodesHelper(root, "");
        }
    }
    
    /**
     * Recursive helper to generate codes.
     * 
     * @param node current node in traversal
     * @param codeSoFar the code built up to this point
     */
    private void generateCodesHelper(HuffmanNode node, String codeSoFar) {
        // TODO: If node is a leaf, store the code for its character
            if (node.isLeaf()) {
                // Edge case: if there's only one unique character, codeSoFar might be ""
                // assign "0" in that case
                if (codeSoFar.equals("")) {
                    codes.put(node.getCharacter(), "0");
                } else {
                    codes.put(node.getCharacter(), codeSoFar);
                }
                return;
            }
        // TODO: Otherwise, recursively traverse:
        //       - Left child with codeSoFar + "0"
            generateCodesHelper(node.getLeft(),  codeSoFar + "0");
        //       - Right child with codeSoFar + "1"
            generateCodesHelper(node.getRight(), codeSoFar + "1");
        
    }
    
    /**
     * Gets the Huffman codes generated for all characters.
     * 
     * @return map of characters to their Huffman codes
     */
    public HashMap<Character, String> getCodes() {
        return codes;
    }
    
    /**
     * Gets the root of the Huffman tree.
     * 
     * @return root node
     */
    public HuffmanNode getRoot() {
        return root;
    }
    
    /**
     * Main method - builds tree and generates codes from a frequency file.
     * Usage: java HuffmanTree frequencies.txt codes.txt
     * 
     * @param args command line arguments: [frequency file] [output codes file]
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java HuffmanTree <frequency file> <codes file>");
            return;
        }
        
        try {
            // Read frequencies
            HashMap<Character, Integer> frequencies = FileIOHelper.readFrequencies(args[0]);
            
            // Build tree
            HuffmanTree tree = new HuffmanTree();
            tree.buildTree(frequencies);
            
            // Generate codes
            tree.generateCodes();
            
            // Save codes
            FileIOHelper.writeCodes(args[1], tree.getCodes());
            
            System.out.println("Huffman codes generated!");
            System.out.println("Code count: " + tree.getCodes().size());
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
