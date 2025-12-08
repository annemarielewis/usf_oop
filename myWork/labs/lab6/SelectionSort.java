import java.util.Arrays;
/**
 * Implements the Selection Sort algorithm for sorting integer arrays.
 * <p>
 * Selection Sort works by repeatedly selecting the smallest (or largest)
 * element from the unsorted portion of the array and swapping it with the
 * first element of the unsorted section. This process divides the array into
 * a sorted and an unsorted region, expanding the sorted region one element at
 * a time until the entire array is sorted.
 * </p>
 *
 * <p>
 * Time Complexity: <br>
 * - Best, Average, and Worst Case: O(n²) <br>
 * Space Complexity: O(1) (In-place) <br>
 * Sorting Type: Not Stable, In-Place
 * </p>
 *
 * <p>
 * This algorithm is simple to implement but generally inefficient on large
 * datasets. However, it's useful for educational purposes and for small arrays
 * where simplicity is preferred over performance.
 * </p>
 */
public class SelectionSort {

    /**
     * Sorts an array of integers in ascending order using 
     * the Selection Sort algorithm.
     *
     * @param arr The array of integers to be sorted.
     */

    public static void selectionSort(int[] arr) {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {

            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[min_idx]) {
                    min_idx = j;
                }
            }

            // Swap the found minimum element with the first 
            //    element of the unsorted part
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }
}
