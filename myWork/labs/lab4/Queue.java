interface Queue {
    //add object to end of queue (end of list)
    void enqueue (Object item);

    //remove object from front of queue (remove from head of list)
    Object dequeue();

    //see what's at the front of the queue (at the head)
    Object front();
}