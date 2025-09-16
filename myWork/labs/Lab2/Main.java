public class Main {
    public static void main(String[] args) {
        // creating myDog object
        Dog myDog = new Dog("Mesa", "unknown", 2.5, 40.0);

        // getter in Dog being called
        System.out.println("Dog Name: " + myDog.getName() + ", breed: " + myDog.getBreed() + ", height: "
                + myDog.getHeight() + ", weight: " + myDog.getWeight());

        // re-setting/(changing) properties to myDog Object
        myDog.setName("Mesa Lewis");
        myDog.setBreed("Border Collie");
        myDog.setWeight(42);
        myDog.setHeight(2.52);
        System.out.println("updated Dog name: " + myDog.getName() + ", updated Breed: " + myDog.getBreed()
                + ", updated weight: " + myDog.getWeight() + ", updated height: " + myDog.getHeight());

        // speak called
        myDog.speak();

        // ______
        Dog dogNala = new Dog("Nala");
        dogNala.speak();
        // ______

        Cat myCat = new Cat("Charlie", "Unknown", 1, 15);

        System.out.println("Cat Name: " + myCat.getName());

        myCat.setName("Charlie Lewis");
        System.out.println("updated Cat name: " + myCat.getName());

        // speak called
        myCat.speak();

        Cat catHubert = new Cat("Hubert");
        catHubert.speak();
        Cat myCatClone = new Cat("Charlie Lewis", "Unknown", 1, 15);

        // .equals checks
        System.out.println("myCat.equals(catHubert) result: " + myCat.equals(catHubert));
        System.out.println("myCat.equals(myCatClone) result: " + myCat.equals(myCatClone));

    }
}
