package parallelsort;

import java.util.Comparator;
import java.util.Random;

/**
 * ParallelSort Extra Credit implementation
 * @author Steckler
 */
public class SortMain<E> {
    private static final Logging logger = new Logging();
    public static void main(String[] args) {
        
        logger.infoLog("Started a new parallel sort.");
        logger.warningLog("Only warning and servere logs show in the console.");
        
        int len = 10000000;   
        Integer[] a = createRandomArray(len);

        Comparator<Integer> comp = new Comparator<Integer>() {
            public int compare(Integer d1, Integer d2) {
                return d1.compareTo(d2);
            }
        };

        // run the algorithm and time how long it takes to sort the elements
        long startTime = System.currentTimeMillis();
        MergeSort.sort(a, comp);
        long endTime = System.currentTimeMillis();

        if(!isSorted(a, comp)) {
            throw new RuntimeException("Not sorted!");
        }

        System.out.printf("%10d elements serial  =>  %6d ms %n", len, endTime - startTime);
        logger.infoLog("Finished parallel sort");
    
    }// end main

    /**
     * Returns true if the given array is in sorted ascending order.
     *
     * @param a the array to examine
     * @param comp the comparator to compare array elements
     * @return true if the given array is sorted, false otherwise
     */
    public static <E> boolean isSorted(E[] a, Comparator<? super E> comp) {
        for (int i = 0; i < a.length - 1; i++) {
            if (comp.compare(a[i], a[i + 1]) > 0) {
                System.out.println(a[i] + " > " + a[i + 1]);
                logger.warningLog("Array is not sorted. Did it not sort correctly?");
                return false;
            }
        }
        logger.infoLog("Array is sorted.");
        return true;
    }

    public static Integer[] createRandomArray(int length) {
        logger.infoLog("Generating Array");
        Integer[] a = new Integer[length];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt(10000000);
        }
        return a;
    }
}
