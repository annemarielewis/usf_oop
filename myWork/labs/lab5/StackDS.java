//stacks are most useful for stpring data that requires:
//The last thing you put in is the first thing that comes out.
// Undo / Redo systems
//function calls that call each other are called on a stack
// backtracking algorithms

import java.util.Stack;
import java.util.HashSet;

public class StackDS {

    public static void main(String[] args) {

        Stack<Integer> numberStack = new Stack<>();

        numberStack.push(4);
        numberStack.push(7);
        numberStack.push(4);
        numberStack.push(2);
        numberStack.push(7);
        numberStack.push(9);

        // 1. Print unique numbers
        System.out.println("unique numbers:");

        //could push into a hashset then print the hashset...
        HashSet<Integer> unique = new HashSet<>();

        for (int num : numberStack) {
            unique.add(num); // Hashset removes duplicates automatically
        }

        System.out.println(unique);
        System.out.println();

        // 2. Print entire stack
        System.out.println("entire stack:");
        System.out.println(numberStack);
        System.out.println();

        // 3. Print the TOP element
        System.out.println("Top element (peek): " + numberStack.peek());
        System.out.println();

        // 4. Pop off the top element
        System.out.println("Popping the top element: " + numberStack.pop());
        System.out.println();

        // 5. Print the stack
        System.out.println("Stack after pop:");
        System.out.println(numberStack);
    }
}

