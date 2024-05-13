package parallelsort;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * This class carries out the merge sort algorithm.
 */
public class MergeSort {

    /**
     * Sorts an array, using the merge sort algorithm.
     * Sends the array to MergeSortAction for processing.
     *
     * @param a the array to sort
     * @param comp the comparator to compare array elements
     */
    public static <E> void sort(E[] a, Comparator<? super E> comp) {
        //mergeSort(a, 0, a.length - 1, comp);
        ForkJoinPool threadPool = new ForkJoinPool();
        threadPool.invoke(new MergeSortAction(a, comp, Integer.class));
        threadPool.close();
    }
}
