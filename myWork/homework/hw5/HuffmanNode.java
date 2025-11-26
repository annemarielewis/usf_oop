/**
 * CS 514: OOP
 * Fall 2025
 * Name: Annemarie Lewis
 *
 * Represents a node in the Huffman tree.
 * Leaf nodes contain characters; internal nodes only store frequency.
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

    private Character character;  // null for internal nodes
    private int frequency;
    private HuffmanNode left;
    private HuffmanNode right;

    /**
     * Constructor for leaf nodes (contains a character).
     *
     * @param character the character stored in this leaf
     * @param frequency how often this character appears
     */
    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    /**
     * Constructor for internal nodes (no character, just connections).
     *
     * @param frequency combined frequency of children
     * @param left      left child node
     * @param right     right child node
     */
    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.character = null;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    /**
     * Checks if this node is a leaf (contains a character).
     *
     * @return true if this is a leaf node
     */

    public boolean isLeaf() {
        if (left == null && right == null) {
            return true;
        }
        return false;
    }
    //^Why can i access left and right fields without passing them in/how do i have access when they're declared private?)
    //chat gpt answer: These fields belong to each HuffmanNode object.
    //Imagine each HuffmanNode object is a folder.
    //Inside that folder are papers labeled:
    //character
    //frequency
    //left
    //right
    //When you write a method inside the same class, it runs from inside that folder, and can directly look at the papers.


    /**
     * Compares nodes by frequency for priority queue ordering.
     * Lower frequency = higher priority.
     *
     * @param other the node to compare to
     * @return negative if this < other, positive if this > other, 0 if equal
     */
    @Override
    public int compareTo(HuffmanNode other)// Order by frequency (lower frequency = higher priority in a min-heap)
    {
        if (this.frequency < other.frequency) {
            return -1;   // comes before other
        } else if (this.frequency > other.frequency) {
            return 1;    // comes after other
        } else {
            return 0;    // equal values
        }
    }

    // Getters
    public Character getCharacter() {
        return character;
    }

    public int getFrequency() {
        return frequency;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }
}