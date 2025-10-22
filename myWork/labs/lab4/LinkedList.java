// Starter Code for Lab 4: Linked Lists and Interfaces
public class LinkedList {

    // Inner class, for Node
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

    // Constructor
    public LinkedList() {
        this.head=null; //head at first needs to point to null
        this.size=0; //keeping track of linked list's # of nodes
    }

    //So the golden rule 🪄:
    //🔹 Use head = newNode; to begin the list.
    //🔹 Use .next to connect nodes once the list exists.

    // Task 1a: Add element at the front
    public void addInFront(Object data) {
        Node newNode = new Node(data);     // Step 1 example: newNode → [X] → null | head → [A] → [B] → null
        newNode.next = head;               // Step 2 --  newNode → [X] → [A] → [B] → null | head → [A] → [B] → null | "Hey, new node — link yourself to the current head of the list ... Without this line, when you insert a node at the front, the rest of the list would be lost — disconnected and inaccessible — because you'd overwrite the head before saving what it was pointing to."
        head = newNode;                    // Step 3 -- Updates the head pointer of the list to point to the new node.
        size++;       //counting up for each new node we add                     // Step 4 -- head → [X] → [A] → [B] → null
         }

         //QUESTION: Id head is a node all te same like the ther nodes (data and pointer), Why isn't it:
    //newNode.next = head.next;
    //head.next = newNode;

    // Task 1b: Print all elements
    public void print() {
        Node current = head; // Points to whatever node head is referencing
        System.out.print("[");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(", ");
            }
            current = current.next; // move to the next node
        }
        System.out.print("]");

    // Task 1c: Return the size
    public int size() {
            System.out.print(size);
        return size;
    }

    // Task 1d: Check if list is empty
    public boolean isEmpty() {
        return head == null; //returns true if it's empty (head points to null when empty LinkedList)
    }

    // TASK 2: contains - Check if element exists in the list
        public boolean contains(Object data) {
            Node current = head; // start from the first node
            //traverse list looking for a data match:
            while (current != null) {
                if (current.data.equals(data)) {
                    return true;
                }
                current = current.next;
            }
            return false; // Not found
        }

// TASK 2: remove - Remove first occurrence of element
        public boolean remove(Object data) {
            // Case 1: Empty list
            if (head == null) {
                return false;
            }
            // Case 2: Element is at the head
            if (head.data.equals(data)) {
                head = head.next;  // move head to next node
                size--;
                return true;
            }
            // Case 3: Element is elsewhere in the list
            Node current = head;
            while (current.next != null) {
                if (current.next.data.equals(data)) {
                    current.next = current.next.next;  // skip over the node to delete
                    size--;
                    return true;
                }
                current = current.next;
            }
            return false;  // Element not found
        }

    // TASK 3: addAtEnd - Add element at the end of the list
    public void addAtEnd(Object data) {
            node newNode = new Node(data);
        }
        // If list is empty, new node becomes the head
            if (head == null) { //why not : head.next == newNode?
                head = newNode; //why not : head.next = newNode?
                size++;
                return;
            };

        // Traverse to the end of the list
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        // Add new node at the end
        current.next = newNode; //if we don;t need .next in head, why do we need it here?
        size++
    }
    //QUESTION: how does the linkedlist get linked to newNode and not just current?

    // TASK 3: get - Return element at specified index
    public Object get(int index) {
        // Check for invalid index
        if (index <0 || index > size) {
            throw new IndexOutofBoundsException ("Index: " + index + "seize: " + size);
        }
        // Traverse to the specified index
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        // return the data;
        return current.data;
        // return current.data;

    }
    // TASK 3: clear - Remove all elements from the list
    public void clear() {
        head = null;
        size = 0;
    }
}

