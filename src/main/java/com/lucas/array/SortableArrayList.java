package com.lucas.array;

import com.lucas.util.SortableList;

/**
 * {@link ArrayList} that can additionally sort itself in place via {@link #sort()}, using
 * selection sort.
 * <p>
 * Complexity: O(n^2) time (two nested passes over the elements), O(1) extra space (sorts using
 * only the existing array and one temporary variable). Not stable: equal elements can be
 * reordered relative to each other, because a swap can jump an element past an equal one.
 */
public class SortableArrayList<T extends Comparable<T>> extends ArrayList<T> implements SortableList<T> {

  @Override
  public void sort() {
    for (int i = 0; i < this.size() - 1; i++) {
      int minIndex = i;
      for (int j = i + 1; j < this.size(); j++) {
        if (this.get(j).compareTo(this.get(minIndex)) < 0)
          minIndex = j;
      }
      if (minIndex != i) {
        T temp = this.get(i);
        this.replace(this.get(minIndex), i);
        this.replace(temp, minIndex);
      }
    }
  }
}
