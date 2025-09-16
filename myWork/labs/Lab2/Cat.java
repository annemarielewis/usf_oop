public class Cat {
    // member/instance variables (each cat object will have)
    String name;
    String breed;
    double height;
    double weight;

    public void speak() {
        System.out.println(name + " says: Meow!");
    }

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

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Invalid name. Name cannot be null or empty.");
        }
    }

    public void setBreed(String breed) {
        if (breed != null && !breed.trim().isEmpty()) {
            this.breed = breed;
        } else {
            System.out.println("Invalid breed. Breed cannot be null or empty.");
        }
    }

    public void setHeight(double height) {
        if (height > 0) {
            this.height = height;
        } else {
            System.out.println("Invalid height. Height must be greater than 0.");
        }
    }

    public void setWeight(double weight) {
        if (weight > 0) {
            this.weight = weight;
        } else {
            System.out.println("Invalid weight. Weight must be greater than 0.");
        }
    }

    // getters: for getting/printing the values of a certain object's instance
    // variables (getters=called in main)
    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    // comparing specific cat objects to see if their values are equal:
    public boolean equals(Cat obj) {
        if (this == obj)
            return true; // same object
        if (obj == null || getClass() != obj.getClass())
            return false; // Null, or not a Cat object
        return this.name.equals(obj.name) && this.breed.equals(obj.breed) && this.height == obj.height
                && this.weight == obj.weight;
    }
}