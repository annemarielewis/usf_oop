public class Dog {
    // member/instance variables (each dog object will have these variables)
    private String name;
    private String breed;
    private double height;
    private double weight;

    // constructor 1:
    public Dog(String name, String breed, double height, double weight) {
        this.name = name;
        this.breed = breed;
        this.height = height;
        this.weight = weight;
    }

    // constructor 2:
    // in event object is created with only name input, specifies the instance
    // values assigned to other
    // instance variables, so we don't get an error (all instance variabels must
    // have value))
    public Dog(String name) {
        this(name, "Unknown", 0.0, 0.0); // assigns default values if not assigned
    }

    // speak method
    public void speak() {
        System.out.println(name + " says: Woof!");
    }

    // setters: for setting instance variables in objects (setters=called in main)
    // when ceratin qualifications met:

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
}
