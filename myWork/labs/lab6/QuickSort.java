/**
 * Implements the QuickSort algorithm for sorting integer arrays.
 * <p>
 * QuickSort is a divide-and-conquer algorithm that works by selecting a pivot element
 * and partitioning the array into two subarrays: elements less than or equal to the pivot,
 * and elements greater than the pivot. It then recursively sorts the subarrays.
 * </p>

 */
public class QuickSort {
    /**
     * Sorts the given array using the QuickSort algorithm.
     * <p>
     * This method recursively partitions the array into subarrays and sorts them.
     * It selects a pivot element and arranges all smaller elements to the left of the pivot
     * and all greater elements to the right. Then it recursively applies the same logic
     * to the left and right subarrays.
     * </p>
     *
     * @param arr  the array of integers to be sorted
     * @param low  the starting index of the portion of the array to be sorted
     * @param high the ending index of the portion of the array to be sorted
     *
     * @throws IllegalArgumentException if the array is null
     */
    public static void sort(int[] arr, int low, int high) {
        if (low < high) {
            // pi is partitioning index, arr[pi] is now at right place
            int pi = partition(arr, low, high);

            // Recursively sort elements before partition and after partition
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }


    }

    /**
     * Partitions the specified portion of the array around a pivot element.
     * <p>
     * The last element in the range is chosen as the pivot. All elements smaller than or
     * equal to the pivot are moved to its left, and all greater elements are moved to its right.
     * After rearrangement, the pivot is placed in its correct sorted position, and its index
     * is returned.
     * </p>
     *
     * @param arr  the array to be partitioned
     * @param low  the starting index of the subarray to partition
     * @param high the ending index of the subarray to partition (pivot position)
     * @return the index of the pivot element after it has been placed in the correct position
     *
     * @throws IllegalArgumentException if the array is null
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // Choose the last element as the pivot
        int i = (low - 1); // Index of smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot) {
                i++;

                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1; // Return the partitioning index
    }
}