//Completed for 1-3

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

    // TASK 1: Constructor - Initialize empty list
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    // TASK 1: addInFront - Add element at the beginning of the list
    public void addInFront(Object data) {
        Node newNode = new Node(data);
        newNode.next = head;  // Point new node to current head
        head = newNode;        // Make new node the head
        size++;
    }

    // TASK 1: print - Display all elements in the list
    public void print() {
        Node current = head;
        System.out.print("[");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(", ");
            }
            current = current.next;
        }
        System.out.println("]");
    }

    // TASK 1: size - Return the number of elements
    public int size() {
        return size;
    }

    // TASK 1: isEmpty - Check if the list is empty
    public boolean isEmpty() {
        return head == null;
    }

    // TASK 2: contains - Check if element exists in the list
    public boolean contains(Object data) {
        Node current = head;

        // Traverse the list looking for the data
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;  // Not found
    }

    // TASK 2: remove - Remove first occurrence of element
    public boolean remove(Object data) {
        // Case 1: Empty list
        if (head == null) {
            return false;
        }

        // Case 2: Element is at the head
        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }

        // Case 3: Element is elsewhere in the list
        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                // Remove the node by bypassing it
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;  // Element not found
    }

    // TASK 3: addAtEnd - Add element at the end of the list
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

    // TASK 3: get - Return element at specified index
    public Object get(int index) {
        // Check for invalid index
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        // Traverse to the specified index
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    // TASK 3: clear - Remove all elements from the list
    public void clear() {
        head = null;
        size = 0;
    }
}

