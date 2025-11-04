// CS 514: Bar Class
// Name: <add your name here>
 //complete the implementation of the Bar class:
// The object that stores the data we want to display

//Comparable=java pre-made interface that has compareto method
public class Bar implements Comparable<Bar> {
        protected String name;
        protected int value;
        protected String category;
        protected String label;


        // Creates a new bar/constructor
        public Bar(String name, int value, String category, String label) {
            this.name = name;
            this.value = value;
            this.category = category;
            this.label = label;
        }
    public Bar(String name, int value, String category) {
        this.name = name;
        this.value = value;
        this.category = category;
    }

        // Returns the name of this bar
        public String getName() {
                return this.name;
        }

        // Returns the value of this bar
        public int getValue() {
                return this.value;
        }

        // Returns the category of this bar.
        public String getCategory() {
                return this.category;
        }

        public String getLabel() {
                return this.label;
        }
        // Compare two bars by value.
    //NOTES:
    // this.x is for the current object. otherBar is the other object that is passed in as a parameter (in main).
//In main:
// Bar bar1 = new Bar("A", 10, "Fruit", "Red");
// Bar bar2 = new Bar("B", 15, "Fruit", "Blue");
//bar1.compareTo(bar2);
//➡️ Inside compareTo(): this → points to bar1
//otherBar → points to bar2
        @Override
        public int compareTo(Bar otherBar) {
            if (otherBar == null) {
                throw new NullPointerException("Cannot compare to a null Bar object.");
            }
            if (otherBar.name == null || otherBar.category == null || otherBar.value < 0) {
                throw new IllegalArgumentException("Invalid Bar: name + category cannot be null values or value must be non-negative.");
            }
            if (this.value < otherBar.value) {
                return -1;
            } else if (this.value > otherBar.value) {
                return 1;
            } else {
                return 0;
            }
        }
}

