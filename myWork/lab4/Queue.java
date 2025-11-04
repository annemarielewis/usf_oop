interface Queue {
    // Add obj to end of queue
    void enqueue(Object item);
    // remove obj from font of queue
    Object dequeue();
    // see what's at the front of the queue
    Object front();
}