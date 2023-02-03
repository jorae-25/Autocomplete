package src;

import java.util.ArrayList;
import java.util.Comparator;

public class Mergesort {

    /**
     * Sort the arrayList argument, based on the comparator given.
     */
    public static <E> void mergesort(ArrayList<E> arrayList, Comparator<E> comparator) {
        if (arrayList.size() > 1) {
            // Split array into halves.
            int halfSize = arrayList.size() / 2;
            ArrayList<E> leftArrayList = new ArrayList<E>(arrayList.subList(0, halfSize));
            ArrayList<E> rightArrayList = new ArrayList<E>(arrayList.subList(halfSize, arrayList.size()));

            // Sort the halves.
            mergesort(leftArrayList, comparator);
            mergesort(rightArrayList, comparator);

            // Merge the halves.
            merge(arrayList, leftArrayList, rightArrayList, comparator);
        }
    }

    // Helper merge function.
    private static <E> void merge(ArrayList<E> outputArrayList, ArrayList<E> leftArrayList,
                                  ArrayList<E> rightArrayList, Comparator<E> comparator) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < leftArrayList.size() && j < rightArrayList.size()) {
            if (comparator.compare(leftArrayList.get(i), rightArrayList.get(j)) < 0){
                outputArrayList.set(k,leftArrayList.get(i));
                k++;
                i++;
            }
            else{
                outputArrayList.set(k,rightArrayList.get(j));
                k++;
                j++;
            }
        }
        while(i<leftArrayList.size()){
            outputArrayList.set(k, leftArrayList.get(i));
            k++;
            i++;
        }
        while(j<rightArrayList.size()){
            outputArrayList.set(k, rightArrayList.get(j));
            k++;
            j++;
        }
    }
}

