public class Cat {
    // member/instance variables (each cat object will have)
    String name;
    String breed;
    double height;
    double weight;

    public Cat(String name, String breed, double height, double weight) {
        this.name = name;
        this.breed = breed;
        this.height = height;
        this.weight = weight;
    }

    // in event object is created with only name input, values assigned to other
    // instance variables for the object so it doesn;t error)
    public Cat(String name) {
        this(name, "Unknown", 0.0, 0.0); // calls the other constructor with defaults
    }

    public void speak() {
        System.out.println("Meow!");
    }

        // setters: for setting instamce variables in objects (called in main)
        public void setName(String name) {
            this.name = name;
        }
    
        public void setBreed(String breed) {
            this.breed = breed;
        }
    
        public void setHeight(double height) {
            this.height = height;
        }
    
        public void setWeight(double weight) {
            this.weight = weight;
        }
    
        // getters: for getting/printing the values of a certain object's instamce
        // variables, called in main)
        public String getName() {
            return name;
        }
    
        public String getBreed() {
            return breed;
        }
    
        public double height() {
            return height;
        }
    
        public double weight() {
            return weight;
        }
}