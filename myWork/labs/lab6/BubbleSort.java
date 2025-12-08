/**
 * Implements the Bubble Sort algorithm for sorting integer arrays.
 * <p>
 * Bubble Sort is a simple comparison-based sorting algorithm that repeatedly
 * traverses the array, compares adjacent elements, and swaps them if they are
 * in the wrong order. With each pass, the largest unsorted element is "bubbled"
 * to its correct position at the end of the array.
 * </p>
 *
 * <p>
 * Time Complexity: <br>
 * - Best Case: O(n) (when optimized with early exit for already sorted arrays) <br>
 * - Average and Worst Case: O(n²) <br>
 * Space Complexity: O(1) (In-place) <br>
 * Sorting Type: Stable
 * </p>
 *
 * <p>
 * Bubble Sort is primarily used for educational purposes due to its simplicity.
 * It is inefficient for large datasets but useful for explaining basic sorting logic.
 * </p>
 */
public class BubbleSort {
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        // Outer loop for passes through the array
        for (int i = 0; i < n - 1; i++) {
            // Inner loop for comparisons and swaps in each pass
            // The (n - i - 1) part optimizes the inner loop as the largest elements
            // are already at the end after each pass.
            for (int j = 0; j < n - i - 1; j++) {
                // Compare adjacent elements
                if (arr[j] > arr[j + 1]) {
                    // Swap if the current element is greater than the next
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}