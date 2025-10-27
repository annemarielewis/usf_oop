public class QueueLinkedList extends LinkedList implements Queue{
    public void enqueue (Object item){
addAtEnd(item); //inherited method from LinkedList
    }

    public Object dequeue() {
if (isEmpty()) {
    throw new RuntimeException("Queue is empty");
}
Object data = get(0);
remove (data);
return data;
    }

    public Object front() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return get(0); // get inherited from LinkedList
    }
}