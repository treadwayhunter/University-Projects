package parallelsort;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction<E> extends RecursiveAction {
    private E[] arr;
    private Comparator<? super E> comp;
    private Class<E> elementType;

    public MergeSortAction(E[] arr, Comparator<? super E> comp, Class<E> elementType) {
        this.arr = arr;
        this.comp = comp;
        this.elementType = elementType;
    }

    @Override
    protected void compute() {
        if(arr.length < 2) return;
        int mid = arr.length / 2;

        E[] left = (E[])Array.newInstance(elementType, mid);
        System.arraycopy(arr, 0, left, 0, mid);
        E[] right = (E[])Array.newInstance(elementType, arr.length - mid);
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        MergeSortAction<E> leftAction = new MergeSortAction<E>(left, comp, elementType);
        MergeSortAction<E> rightAction = new MergeSortAction<E>(right, comp, elementType);

        invokeAll(leftAction, rightAction);
        leftAction.join();
        rightAction.join();

        merge(left, right);
    }

    private void merge(E[] left, E[] right) {
        int i = 0;
        int j = 0;
        int k = 0;

        while(i < left.length && j < right.length) {
            if(comp.compare(left[i], right[j]) < 0) {
                arr[k++] = left[i++];
            }
            else {
                arr[k++] = right[j++];
            }
        }
        while(i < left.length) {
            arr[k++] = left[i++];
        }
        while(j < right.length) {
            arr[k++] = right[j++];
        }
    }
}
