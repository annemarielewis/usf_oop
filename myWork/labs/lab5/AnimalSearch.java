//rule of thumb (it seems): if a function initiated something at zero and then
//uses iteration to add +1 to it, can likely use recursion to solve instead
//(though may or may not be wise to switch to it)

import java.util.*;

public class AnimalSearch {

    public static boolean searchRecursive(List<String> list, String target, int index) {
        // TODO: Implement recursive search
        // Base case: reached end
        if (index == list.size()) {
            return false;
        }
        // Found it
        if (list.get(index).equalsIgnoreCase(target)) {
            return true;
        }
        // Recursion: keep searching
        return searchRecursive(list, target, index + 1);
    }

    public static void reversePrintRecursive(List<String> list, int index) {
        // Base case: stop once index goes past the beginning
        if (index < 0) {
            return;
        }
        // Print the current element
        System.out.println(list.get(index));

        // Recursive call: move one step backward
        reversePrintRecursive(list, index - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> animals = new ArrayList<>(Arrays.asList("Lion", "Tiger", "Cat", "Dog", "Zebra"));

        System.out.print("Enter animal to search: ");
        String search = sc.nextLine();

        if (searchRecursive(animals, search, 0)) {
            System.out.println(search + " found in the list!");
        } else {
            System.out.println(search + " not found in the list.");
        }

        System.out.println("\nAnimals in reverse order:");
        reversePrintRecursive(animals, animals.size() - 1);
    }
}

