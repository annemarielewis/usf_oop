import java.io.*;
import java.util.*;
/**
 * CS 514: OOP
 *  Fall 2025
 * Utility class for file I/O operations in Huffman coding project.
 * All file reading and writing functionality is provided.
 */
public class FileIOHelper {
    
    /**
     * Reads a text file and returns its contents as a String.
     * 
     * @param filename the path to the file to read
     * @return the complete contents of the file
     * @throws IOException if file cannot be read
     */
    public static String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                content.append((char) ch);
            }
        }
        return content.toString();
    }
    
    /**
     * Writes text content to a file.
     * 
     * @param filename the path to the output file
     * @param content the text to write
     * @throws IOException if file cannot be written
     */
    public static void writeFile(String filename, String content) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(content);
        }
    }
    
    /**
     * Reads frequency map from a saved codebook file.
     * Format: character:frequency (one per line)
     * 
     * @param filename the codebook file to read
     * @return HashMap mapping characters to their frequencies
     * @throws IOException if file cannot be read
     */
    public static HashMap<Character, Integer> readFrequencies(String filename) throws IOException {
        HashMap<Character, Integer> frequencies = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(":", 2);

                // Handle special character escapes
                char ch;
                if (parts[0].equals("\\n")) {
                    ch = '\n';
                } else if (parts[0].equals("\\r")) {
                    ch = '\r';
                } else if (parts[0].equals("\\t")) {
                    ch = '\t';
                } else {
                    ch = parts[0].charAt(0);
                }

                int freq = Integer.parseInt(parts[1]);
                frequencies.put(ch, freq);
            }
        }
        return frequencies;
    }
    
    /**
     * Writes frequency map to a codebook file.
     * Format: character:frequency (one per line)
     * 
     * @param filename the output codebook file
     * @param frequencies the map of character frequencies
     * @throws IOException if file cannot be written
     */
    public static void writeFrequencies(String filename, HashMap<Character, Integer> frequencies) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
                char ch = entry.getKey();

                // Handle special characters for display
                String charDisplay;
                if (ch == '\n') {
                    charDisplay = "\\n";  // Show newline as \n
                } else if (ch == '\r') {
                    charDisplay = "\\r";  // Show carriage return as \r
                } else if (ch == '\t') {
                    charDisplay = "\\t";  // Show tab as \t
                } else {
                    charDisplay = String.valueOf(ch);
                }

                writer.println(charDisplay + ":" + entry.getValue());
            }
        }
    }
    
    /**
     * Writes Huffman codes to a codebook file.
     * Format: character:code (one per line)
     * 
     * @param filename the output codebook file
     * @param codes the map of Huffman codes
     * @throws IOException if file cannot be written
     */
    public static void writeCodes(String filename, HashMap<Character, String> codes) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Map.Entry<Character, String> entry : codes.entrySet()) {
                char ch = entry.getKey();

                // Handle special characters for display
                String charDisplay;
                if (ch == '\n') {
                    charDisplay = "\\n";
                } else if (ch == '\r') {
                    charDisplay = "\\r";
                } else if (ch == '\t') {
                    charDisplay = "\\t";
                } else {
                    charDisplay = String.valueOf(ch);
                }

                writer.println(charDisplay + ":" + entry.getValue());
            }
        }
    }
    
    /**
     * Reads Huffman codes from a codebook file.
     * Format: character:code (one per line)
     * 
     * @param filename the codebook file to read
     * @return HashMap mapping characters to their Huffman codes
     * @throws IOException if file cannot be read
     */
    public static HashMap<Character, String> readCodes(String filename) throws IOException {
        HashMap<Character, String> codes = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(":", 2);

                // Handle special character escapes
                char ch;
                if (parts[0].equals("\\n")) {
                    ch = '\n';
                } else if (parts[0].equals("\\r")) {
                    ch = '\r';
                } else if (parts[0].equals("\\t")) {
                    ch = '\t';
                } else {
                    ch = parts[0].charAt(0);
                }

                String code = parts[1];
                codes.put(ch, code);
            }
        }
        return codes;
    }
}

