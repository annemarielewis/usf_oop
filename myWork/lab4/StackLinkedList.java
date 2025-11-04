public class StackLinkedList extends LinkedList implements Stack {
    public void push(Object item) {
        addInFront(item);  // Add to front = add to top
    }

    public Object pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        Object data = get(0);
        remove(data);
        return data;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return get(0);
    }
}