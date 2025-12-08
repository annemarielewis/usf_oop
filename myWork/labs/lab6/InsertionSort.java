/**
 * Provides an implementation of the Insertion Sort algorithm for sorting integer arrays.
 * <p>
 * Insertion Sort builds the final sorted array one element at a time by repeatedly
 * inserting elements into their correct position within the already-sorted portion
 * of the array. It is efficient for small or nearly sorted datasets and is commonly
 * used in educational contexts to demonstrate sorting principles.
 * <br><br>
 * Time Complexity: <br>
 * - Best Case: O(n) when the array is already sorted <br>
 * - Average/Worst Case: O(n²) <br>
 * Space Complexity: O(1) (In-place) <br>
 * Sorting Type: Stable, In-Place
 * </p>
 *
 * <p>
 * This class also includes a utility method to print the contents of an array.
 * </p>
 */
public class InsertionSort {

    /**
     * Sorts an array of integers in ascending order using the Insertion Sort algorithm.
     *
     * @param arr The array of integers to be sorted.
     */
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        // Start from the second element (index 1) as the first element is considered sorted
        for (int i = 1; i < n; i++) {
            int key = arr[i]; // The current element to be inserted into the sorted subarray
            int j = i - 1;    // Index of the last element in the sorted subarray

            // Move elements of arr[0..i-1], that are greater than key,
            // to one position ahead of their current position
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]; // Shift element to the right
                j--;                 // Move to the previous element in the sorted subarray
            }
            arr[j + 1] = key; // Place the key in its correct sorted position
        }
    }

    /**
     * A utility function to print an array.
     *
     * @param arr The array to be printed.
     */
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
