public class QueueLinkedList extends LinkedList implements Queue {

    public void enqueue(Object item) {
        addAtEnd(item);  // Add to back of queue
    }

    public Object dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        Object data = get(0);
        remove(data);
        return data;
    }

    public Object front() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return get(0);
    }
}