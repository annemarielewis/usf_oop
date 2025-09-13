public class Main {
    public static void main(String[] args) {
        // creating myDog object
        Dog myDog = new Dog("Mesa", "Border-collie", 2.5, 40.0);

        // getter in Dog being called
        System.out.println("Dog Name: " + myDog.getName());

        // re-setting/(changing) properties to myDog Object
        myDog.setName("Mesa Lewis");
        System.out.println("updated Dog name: " + myDog.getName());

        // speak called
        myDog.speak();

        Cat myCat = new Cat("Charlie", "Unknown", 1, 15);

        System.out.println("Cat Name: " + myCat.getName());

        myCat.setName("Charlie Lewis");
        System.out.println("updated Cat name: " + myCat.getName());

        // speak called
        myCat.speak();
    }
}
