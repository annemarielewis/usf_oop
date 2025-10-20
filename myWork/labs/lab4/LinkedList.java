// Starter Code for Lab 4: Linked Lists and Interfaces
public class LinkedList {

    // Inner class for Node
    private class Node {
        Object data;
        Node next;

        public Node(Object data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    // Task 1: Constructor
    public LinkedList() {
        this.head=null;
        this.size=0; //setting size to 0, we know head containes value of 0 for its int
    }

    // Task 1: Add element at the front
    public void addInFront(Object data) {
        Node newNode = new Node(data);     // Step 1 example: newNode → [X] → null | head → [A] → [B] → null
        newNode.next = head;               // Step 2 --  newNode → [X] → [A] → [B] → null | head → [A] → [B] → null | "Hey, new node — link yourself to the current head of the list ... Without this line, when you insert a node at the front, the rest of the list would be lost — disconnected and inaccessible — because you'd overwrite the head before saving what it was pointing to."
        head = newNode;                    // Step 3 -- Updates the head pointer of the list to point to the new node.
        size++;                            // Step 4 -- head → [X] → [A] → [B] → null
         }

    // Task 1: Print all elements
    public void print() {
        // TODO: Traverse and print all elements
    }

    // Task 1: Return the size
    public int size() {
        // TODO: Return the number of elements
        return 0;
    }

    // Task 1: Check if list is empty
    public boolean isEmpty() {
        // TODO: Return true if list is empty
        return false;
    }

    // TASK 2: contains - Check if element exists in the list
    public boolean contains(Object data) {
        // Traverse the list looking for the data
        return false;  // Not found
    }

    // TASK 2: remove - Remove first occurrence of element
    public boolean remove(Object data) {
        // Case 1: Empty list
        // Case 2: Element is at the head
        // Case 3: Element is elsewhere in the list
        return false;  // Element not found
    }

    // TASK 3: addAtEnd - Add element at the end of the list
    public void addAtEnd(Object data) {
        // If list is empty, new node becomes the head
        // Traverse to the end of the list
        // Add new node at the end
    }

    // TASK 3: get - Return element at specified index
    public Object get(int index) {
        // Check for invalid index
        // Traverse to the specified index
        // return the data;
        // return current.data;
        return null;
    }
    // TASK 3: clear - Remove all elements from the list
    public void clear() {
    }
}

