public class MyClass {
    // Fields (variables)
    private int number;
    public String name;

    // Static field
    public static int count = 0;

    // Constructor
    public MyClass(int number, String name) {
        this.number = number;
        this.name = name;
        count++;
    }

    // Method (non-static)
    public void displayInfo() {
        System.out.println("Number: " + number + ", Name: " + name);
    }

    // Static method
    public static void showCount() {
        System.out.println("Total objects created: " + count);
    }

    // Getter
    public int getNumber() {
        return number;
    }

    // Setter
    public void setNumber(int number) {
        this.number = number;
    }
}