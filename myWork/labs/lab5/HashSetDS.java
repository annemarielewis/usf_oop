//HashSet = a box that holds unique items, with no duplicates allowed.
// HashSet is good for:
//Removing duplicates --> cannot push same value twice into a hashset, it will skip and keep going in the code
//Checking “does this item exist?”
//Fast membership tests
//Tasks like your “print all unique numbers”

import java.util.HashSet;

public class HashSetDS {

    public static void main(String[] args) {

        HashSet<Integer> numberSet = new HashSet<>();

        // Add numbers {4, 7, 4, 2, 7, 9}
        numberSet.add(4);
        numberSet.add(7);
        numberSet.add(4); // duplicate ignored
        numberSet.add(2);
        numberSet.add(7); // duplicate ignored
        numberSet.add(9);

        // Print the entire set
        System.out.println("Set of unique numbers:");
        System.out.println(numberSet);
        System.out.println();

        // Remove element "4"
        numberSet.remove(4);

        // Print the entire set
        System.out.println("After removing 4:");
        System.out.println(numberSet);
        System.out.println();

        // Add element 7
        // --> add a 7th element to the hashset or try to add "7" again, which won't work?
        //to be safe, did both:
        numberSet.add(12);
        numberSet.add(7); //duplicate ignored

        // Print the entire set
        System.out.println("After adding a 7th element to the hashset AND attempting to add the number 7 again:");
        System.out.println(numberSet);
        System.out.println("HashSet size: " + numberSet.size());
        System.out.println();

        // Check if set contains the number 10
        boolean containsTen = numberSet.contains(10);

        System.out.println("Does the set contain 10?");
        System.out.println(containsTen);
    }
}

