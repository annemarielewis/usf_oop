// CS 514: Lab 4 - Custom LinkedList and 
//  Stack Implementation
// This class will serve as the interface to impleme

public interface Stack {

    // Add an item to the top of the stack
    void push(Object item);
    
    // remove and return the top item 
    Object pop();
    
    // view top item without removing it from the list
    Object peek();
}
