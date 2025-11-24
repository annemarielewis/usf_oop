public class ArrayDS {
    public static void main(String[] args) {

//        int[] numbers = new int[5];
//        numbers[0] = 5;
//        numbers[1] = 10;
//        numbers[2] = 15;
//        numbers[3] = 20;
//        numbers[4] = 25; -->
//        int[] numbers = {5, 10, 15, 20, 25};
        int[] numbers = new int[]{5, 10, 15, 20, 25};
//-->^I get confused about how these arrays are functionally different other than the below being better for declaring outside of a method that will then populate it...

        System.out.print("Numbers: ");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
    }
}
