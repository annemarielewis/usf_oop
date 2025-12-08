/**
 * Implements the Merge Sort algorithm for sorting integer arrays.
 * <p>
 * Merge Sort is a divide-and-conquer algorithm that recursively splits an array
 * into two halves, sorts each half, and then merges the sorted halves back together.
 * It guarantees stable sorting and has consistent performance regardless of input order.
 * <br><br>
 * Time Complexity: O(n log n) in all cases (best, average, worst) <br>
 * Space Complexity: O(n) due to temporary arrays used during merging <br>
 * Sorting Type: Stable, Not In-Place
 * </p>
 */
public class MergeSort {
    /**
     * Recursively sorts the portion of the array between the given indices
     * using the Merge Sort algorithm.
     *
     * @param arr   the array to be sorted
     * @param left  the starting index of the subarray to be sorted
     * @param right the ending index of the subarray to be sorted
     *
     * @throws IllegalArgumentException if the array is null
     */

    static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    /**
     * Merges two sorted subarrays of the given array.
     * <p>
     * The first subarray is arr[left..mid], and the second subarray is arr[mid+1..right].
     * Elements from both subarrays are compared and merged into their correct positions
     * in the original array.
     * </p>
     *
     * @param arr   the array containing the subarrays to be merged
     * @param left  the starting index of the first sorted subarray
     * @param mid   the ending index of the first subarray, and one less than the start of the second
     * @param right the ending index of the second sorted subarray
     *
     * @throws IllegalArgumentException if the array is null
     */
    static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        for (int i = 0; i < n1; ++i)
            leftArr[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            rightArr[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }
        while (i < n1) arr[k++] = leftArr[i++];
        while (j < n2) arr[k++] = rightArr[j++];
    }
}
