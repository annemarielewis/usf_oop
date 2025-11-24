import java.util.ArrayList;

public class ArrayListDS {

    public static void main(String[] args) {

        ArrayList<String> foodList = new ArrayList<>();

        foodList.add("pasta");
        foodList.add("sushi");
        foodList.add("burrito");
        foodList.add("(medium rare) steak");
        foodList.add("ice cream");

        System.out.println("Favorite foods:");
        System.out.println(foodList);
        System.out.println();

        foodList.remove(2);

        System.out.println("After removing the 3rd food:");
        System.out.println(foodList);
    }
}

//From Scratch NOT USING java's utility package/premade Array class:
//"public class ArrayListDS {
//
//    static class MyArrayList {
//        private String[] data;   // internal array
//        private int size;        // number of elements stored
//
//        // Constructor
//        public MyArrayList() {
//            data = new String[5];
//            size = 0;
//        }
//
//        // Add element to end
//        public void add(String item) {
//            // Resize if full
//            if (size == data.length) {
//                resize();
//            }
//            data[size] = item;
//            size++;
//        }
//
//        // Remove element at a specific index
//        public void remove(int index) {
//            if (index < 0 || index >= size) {
//                System.out.println("Invalid index!");
//                return;
//            }
//
//            // Shift items left
//            for (int i = index; i < size - 1; i++) {
//                data[i] = data[i + 1];
//            }
//
//            size--;
//            data[size] = null;  // optional: clean leftover
//        }
//
//        // Resize internal array when full
//        private void resize() {
//            String[] newData = new String[data.length * 2];
//            for (int i = 0; i < data.length; i++) {
//                newData[i] = data[i];
//            }
//            data = newData;
//        }
//
//        // Convert to printable string
//        public String toString() {
//            if (size == 0) return "[]";
//
//            StringBuilder sb = new StringBuilder();
//            sb.append("[");
//
//            for (int i = 0; i < size; i++) {
//                sb.append(data[i]);
//                if (i < size - 1) sb.append(", ");
//            }
//
//            sb.append("]");
//            return sb.toString();
//        }
//    }
//
//    // ----- MAIN PROGRAM (assignment requirements) -----
//    public static void main(String[] args) {
//
//        MyArrayList foodList = new MyArrayList();
//
//        // Add top 5 favorite foods
//        foodList.add("Pasta");
//        foodList.add("Sushi");
//        foodList.add("Burritos");
//        foodList.add("Pho");
//        foodList.add("Ice Cream");
//
//        // Print original list
//        System.out.println("My top 5 favorite foods:");
//        System.out.println(foodList);
//        System.out.println();
//
//        // Remove 3rd food (index 2)
//        foodList.remove(2);
//
//        // Print updated list
//        System.out.println("After removing the 3rd food:");
//        System.out.println(foodList);
//    }
//}"