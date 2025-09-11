import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class test {

    public static Scanner getFileHandle(String filename) {
        Scanner sc = null;
        try {
            File inputFile = new File(filename); // creates a Java object that represents the path to a file
            sc = new Scanner(inputFile);// This actually tries to open and read the file. --> If the file doesn’t exist, this line will throw a FileNotFoundException.


        } catch(FileNotFoundException e) {
            System.out.println("No such file: " + e.getMessage());
        }
        return sc; //Returns the Scanner (or null)

    }
    //^getFileHandle=a method on how to find + read files w/in Wordle class
    //______________________________________________________________________________

    public static void testGetFileHandle() {
        System.out.print("Enter the filename to open: ");
        Scanner userInput = new Scanner(System.in); //path to console input
        String filename = userInput.nextLine(); //what user typed into console
    
        Scanner fileScanner = getFileHandle(filename); //path to file
        if (fileScanner != null) {
            System.out.println("Successfully opened: " + filename);
    
            // print contents
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        } else {
            System.out.println("This file does not exist in this directory:" + filename);
        }
    }

    public static void main(String[] args) {
        testGetFileHandle();
    }
}
