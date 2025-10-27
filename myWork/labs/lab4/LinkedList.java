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
        this.head = null;          // head at first needs to point to null
        this.size = 0;             // keeping track of linked list's # of nodes
    }

    // So the golden rule 🪄:
    // 🔹 Use head = newNode; to begin the list.
    // 🔹 Use .next to connect nodes once the list exists.

    // Task 1a: Add element at the front
    public void addInFront(Object data) {
        Node newNode = new Node(data);      // Step 1: newNode → [X] → null | head → [A] → [B] → null
        newNode.next = head;                // Step 2: Link newNode → old head → [A] → [B] → null
        head = newNode;                     // Step 3: head → newNode → [A] → [B] → null
        size++;                             // Step 4: increment size
    }

    // QUESTION: If head is a node all the same like the other nodes (data and pointer), Why isn't it:
    // newNode.next = head.next;
    // head.next = newNode;
    //
    // Answer: Because if head currently refers to the first node (or null if list is empty), you want the
    // new node to become the new head and link *into* the old head. If you did head.next = newNode you
    // would leave head pointing at the old first node and insert newNode *after* it (not at the front).
    // The pattern for “add at front” is: newNode.next = head; head = newNode.
    // --> BUT doesn't the head node have a next field too, like every node does? Confused...

    // Task 1b: Print all elements
    public void print() {
        Node current = head;               // Points to whatever node head is referencing
        System.out.print("[");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(", ");
            }
            current = current.next;        // move to the next node
        }
        System.out.print("]");
    }

    // Task 1c: Return the size
    public int size() {
        System.out.print(size);
        return size;
    }

    // Task 1d: Check if list is empty
    public boolean isEmpty() {
        return head == null;               // returns true if it's empty (head points to null)
    }

    // TASK 2: contains - Check if element exists in the list
    public boolean contains(Object data) {
        Node current = head;                // start from the first node
        // traverse list looking for a data match:
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;                       // Not found
    }

    // TASK 2: remove - Remove first occurrence of element
    public boolean remove(Object data) {
        // Case 1: Empty list
        if (head == null) {
            return false;
        }
        // Case 2: Element is at the head
        if (head.data.equals(data)) {
            head = head.next;               // move head to next node
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
        return false;                       // Element not found
    }

    // TASK3: addAtEnd - Add element at the end of the list
    public void addAtEnd(Object data) {
        Node newNode = new Node(data);

        // If list is empty, new node becomes the head
        if (head == null) {
            head = newNode;
            size++;
            return;
        }

        // Traverse to the end of the list
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        // Add new node at the end
        current.next = newNode;
        size++;
    }
    // QUESTION: how does the linked list get linked to newNode and not just current?
    // Answer: “current” is pointing to the last node of the list (whose .next is null).
    // By doing current.next = newNode, you link that last node’s pointer to the new node,
    // thus the list “links in” the new node at the end.

    // TASK 3: get - Return element at specified index
    public Object get(int index) {
        // Check for invalid index
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }
        // Traverse to the specified index
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        // return the data
        return current.data;
    }

    // TASK 3: clear - Remove all elements from the list
    public void clear() {
        head = null;
        size = 0;
    }
}
