public class Main {
    public static void main(String[] args) {
        LinkedList llist = new LinkedList();
        llist.addInFront("usfca.edu");
        System.out.println(llist.isEmpty()); // false
        llist.print();
        llist.addInFront("bbc.co.uk");
        llist.print();
        System.out.println(llist.contains("abcnews.com")); //false
        System.out.println(llist.contains("usfca.edu")); //true
        llist.clear();
        System.out.println(llist.isEmpty()); //true

        // simulate a document change
        StackLinkedList sllist = new StackLinkedList();
        sllist.push("bold font");
        sllist.push("green font");
        sllist.print();
        System.out.println(sllist.peek());
        // undo the last step (green font)
        sllist.pop();
        sllist.print();

        //Simulate coffee drink queue
        QueueLinkedList qllist = new QueueLinkedList();
        qllist.enqueue("tall latte with oat milk");
        qllist.enqueue("mocha");
        qllist.enqueue("iced chai latte");
        qllist.print();
        Object firstDrink = qllist.dequeue();
        System.out.println("Order ready: "+ firstDrink);
        //simulate a coffee drink
        System.out.println("Upcoming drink: " + qllist.front());
        qllist.print();



    }
}
