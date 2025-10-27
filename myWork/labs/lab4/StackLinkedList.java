public class StackLinkedList extends LinkedList implements Stack {

    // Push method: add element to top of stack
    public void push(Object item) {
        addInFront(item); // inherited from LinkedList
    }

    // Pop method: remove and return top element
    public Object pop() {
        if (isEmpty()) { //isEmpty is inhereted method from LinkedList!
            throw new RuntimeException("Stack is empty");
        }
        Object data = get(0); // top of stack (front of list)
        remove(data);         // remove that element
        return data;
    }

    // Peek method — returns the top element without removing it
    public Object peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return get(0); // just look at the top (index 0), don’t remove it
    }
}