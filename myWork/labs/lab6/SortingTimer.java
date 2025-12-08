import java.util.Arrays;

public class SortingTimer {

    /**
     * Times how long a sorting method takes to sort an array.
     *
     * @param sortMethod A String identifying which sort to use
     * @param array      The array to sort
     * @return execution time in nanoseconds
     */
    public static long timeSortingAlgorithm(String sortMethod, int[] array) {
        // Create a copy so we don't modify the original array
        int[] arrayCopy = Arrays.copyOf(array, array.length);

        // Record start time
        long startTime = System.nanoTime();

        // Execute the appropriate sorting algorithm
        switch (sortMethod.toLowerCase()) {
            case "bubble":
                BubbleSort.bubbleSort(arrayCopy);
                break;
            case "selection":
                SelectionSort.selectionSort(arrayCopy);
                break;
            case "insertion":
                InsertionSort.insertionSort(arrayCopy);
                break;
            case "merge":
                MergeSort.mergeSort(arrayCopy,0, arrayCopy.length-1);
                break;
            case "quick":
                QuickSort.sort(arrayCopy,0, arrayCopy.length-1);
                break;
        }

        // Record end time
        long endTime = System.nanoTime();

        // Calculate execution time in nanoseconds
        return endTime - startTime;

    }

    /**
     * Converts nanoseconds to milliseconds for easier reading
     */
    public static double nanosToMillis(long nanos) {
        return nanos / 1_000_000.0;
    }


    public static void main(String[] args) {
        int[] testArray20 = {47, 12, 89, 5, 63, 28, 94, 31, 76, 15, 8, 52,
                67, 23, 90, 41, 36, 78, 4, 59};

        int[] testArray100 = {64, 34, 25, 12, 22, 11, 90, 88, 45, 50, 33, 17, 78, 65, 23,
                89, 19, 40, 75, 31, 96, 52, 47, 63, 81, 29, 44, 18, 70, 59,
                91, 36, 27, 83, 58, 14, 72, 41, 26, 77, 95, 68, 37, 84, 56,
                20, 48, 62, 32, 87, 73, 15, 99, 43, 53, 28, 71, 38, 82, 61,
                13, 97, 46, 24, 67, 35, 79, 55, 21, 49, 86, 39, 74, 30, 92,
                69, 16, 98, 42, 54, 66, 76, 51, 85, 60, 93, 57, 80, 94, 100,
                10, 9, 8, 7, 6, 5, 4, 3, 2, 1};


        int[] testArray200 = {
                47, 12, 89, 5, 63, 28, 94, 31, 76, 15,
                8, 52, 67, 23, 90, 41, 36, 78, 4, 59,
                102, 87, 33, 71, 19, 54, 3, 120, 66, 45,
                11, 98, 72, 144, 6, 81, 37, 25, 130, 55,
                68, 7, 149, 24, 116, 65, 40, 20, 83, 14,
                137, 49, 62, 100, 17, 92, 27, 58, 73, 10,
                84, 39, 150, 18, 64, 2, 79, 26, 91, 46,
                60, 126, 9, 34, 121, 16, 50, 85, 143, 22,
                70, 29, 139, 13, 95, 53, 101, 38, 30, 122,
                48, 1, 141, 35, 142, 61, 74, 51, 96, 21,
                82, 136, 32, 86, 125, 42, 111, 93, 75, 117,
                118, 135, 80, 57, 145, 110, 69, 132, 103, 88,
                129, 43, 56, 140, 97, 127, 113, 31, 104, 44,
                138, 123, 99, 146, 114, 124, 105, 128, 109, 115,
                131, 147, 119, 148, 108, 107, 106, 112, 134, 133,
                41, 17, 63, 28, 94, 31, 76, 15, 8, 52,
                67, 23, 90, 41, 36, 78, 4, 59, 102, 87,
                33, 71, 19, 54, 3, 120, 66, 45, 11, 98,
                72, 144, 6, 81, 37, 25, 130, 55, 68, 7,
                149, 24, 116, 65, 40, 20, 83, 14, 137, 49
        };

        testElements(testArray20);
        testElements(testArray100);
        testElements(testArray200);
    }
    /**
     * Tests all five sorting algorithms on the provided array and displays
     * performance comparison results.
     *
     * This method runs each sorting algorithm (Bubble Sort, Selection Sort,
     * Insertion Sort, Merge Sort, and Quick Sort) on a copy of the test array,
     * measures the execution time for each, and prints a formatted comparison
     * of their performance in milliseconds.
     *
     * @param testArray the array of integers to be sorted by each algorithm
     */
    public static void testElements(int[] testArray) {
        System.out.println("Sorting Algorithm Performance Comparison");
        System.out.println("========================================");
        System.out.println("Array size: " + testArray.length + " elements\n");

        long bubbleTime = timeSortingAlgorithm("bubble", testArray);
        System.out.printf("Bubble Sort:     %.6f milliseconds%n", nanosToMillis(bubbleTime));

        long selectionTime = timeSortingAlgorithm("selection", testArray);
        System.out.printf("Selection Sort:  %.6f milliseconds%n", nanosToMillis(selectionTime));

        long insertionTime = timeSortingAlgorithm("insertion", testArray);
        System.out.printf("Insertion Sort:  %.6f milliseconds%n", nanosToMillis(insertionTime));

        long mergeTime = timeSortingAlgorithm("merge", testArray);
        System.out.printf("Merge Sort:      %.6f milliseconds%n", nanosToMillis(mergeTime));

        long quickTime = timeSortingAlgorithm("quick", testArray);
        System.out.printf("Quick Sort:      %.6f milliseconds%n", nanosToMillis(quickTime));
    }

   /*
   `public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    */

}



