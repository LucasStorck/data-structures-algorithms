package com.lucas.array;

import com.lucas.sort.Sorting;
import com.lucas.util.SortableList;

/**
 * {@link ArrayList} that can additionally sort itself in place via {@link #sort()}, using
 * selection sort ({@link Sorting#selectionSort}).
 * <p>
 * Complexity: O(n^2) time (two nested passes over the elements), O(1) extra space (sorts using
 * only the existing array and one temporary variable). Not stable: equal elements can be
 * reordered relative to each other, because a swap can jump an element past an equal one.
 * See {@link Sorting#mergeSort} and {@link Sorting#quickSort} for O(n log n) alternatives.
 */
public class SortableArrayList<T extends Comparable<T>> extends ArrayList<T> implements SortableList<T> {

  @Override
  public void sort() {
    Sorting.selectionSort(this);
  }
}
