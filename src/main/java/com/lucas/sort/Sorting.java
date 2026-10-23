package com.lucas.sort;

import com.lucas.util.List;

/**
 * In-place, ascending sorting algorithms over any {@link List}, using the elements'
 * {@linkplain Comparable natural ordering}.
 * <p>
 * Every algorithm here reads and writes only through {@link List#get} and {@link List#replace},
 * so the complexities below assume O(1) indexed access ({@code ArrayList}). On a
 * {@code LinkedList} each {@code get}/{@code replace} is itself O(n), which multiplies the cost
 * by another factor of n.
 */
public final class Sorting {

  private Sorting() {
  }

  /**
   * Selection sort: repeatedly finds the smallest remaining element and swaps it into place.
   * <p>
   * Complexity: O(n^2) time in every case, O(1) extra space. Not stable: a swap can jump an
   * element past an equal one.
   */
  public static <T extends Comparable<T>> void selectionSort(List<T> list) {
    for (int i = 0; i < list.size() - 1; i++) {
      int minIndex = i;
      for (int j = i + 1; j < list.size(); j++) {
        if (list.get(j).compareTo(list.get(minIndex)) < 0)
          minIndex = j;
      }
      if (minIndex != i)
        swap(list, i, minIndex);
    }
  }

  /**
   * Merge sort (top-down): splits the range in half, sorts each half recursively and merges the
   * two sorted halves.
   * <p>
   * Complexity: O(n log n) time in every case, O(n) extra space for the merge buffer. Stable:
   * on ties the merge always takes from the left half first.
   */
  public static <T extends Comparable<T>> void mergeSort(List<T> list) {
    Object[] buffer = new Object[list.size()];
    mergeSort(list, buffer, 0, list.size() - 1);
  }

  private static <T extends Comparable<T>> void mergeSort(List<T> list, Object[] buffer, int low,
      int high) {
    if (low >= high)
      return;
    int middle = low + (high - low) / 2;
    mergeSort(list, buffer, low, middle);
    mergeSort(list, buffer, middle + 1, high);
    merge(list, buffer, low, middle, high);
  }

  @SuppressWarnings("unchecked")
  private static <T extends Comparable<T>> void merge(List<T> list, Object[] buffer, int low,
      int middle, int high) {
    for (int i = low; i <= high; i++)
      buffer[i] = list.get(i);
    int left = low;
    int right = middle + 1;
    for (int i = low; i <= high; i++) {
      if (left > middle)
        list.replace((T) buffer[right++], i);
      else if (right > high)
        list.replace((T) buffer[left++], i);
      else if (((T) buffer[right]).compareTo((T) buffer[left]) < 0)
        list.replace((T) buffer[right++], i);
      else
        list.replace((T) buffer[left++], i);
    }
  }

  /**
   * Quick sort (Lomuto partition, middle element as pivot): places the pivot in its final
   * position with smaller elements to its left, then sorts each side recursively.
   * <p>
   * Complexity: O(n log n) average time, O(n^2) worst case (pivot is always the smallest or
   * largest element); O(log n) average extra space for the recursion. Not stable. Using the
   * middle element as pivot avoids the worst case on already sorted input.
   */
  public static <T extends Comparable<T>> void quickSort(List<T> list) {
    quickSort(list, 0, list.size() - 1);
  }

  private static <T extends Comparable<T>> void quickSort(List<T> list, int low, int high) {
    if (low >= high)
      return;
    int pivotIndex = partition(list, low, high);
    quickSort(list, low, pivotIndex - 1);
    quickSort(list, pivotIndex + 1, high);
  }

  private static <T extends Comparable<T>> int partition(List<T> list, int low, int high) {
    swap(list, low + (high - low) / 2, high);
    T pivot = list.get(high);
    int boundary = low;
    for (int i = low; i < high; i++) {
      if (list.get(i).compareTo(pivot) < 0)
        swap(list, i, boundary++);
    }
    swap(list, boundary, high);
    return boundary;
  }

  private static <T> void swap(List<T> list, int i, int j) {
    T temp = list.get(i);
    list.replace(list.get(j), i);
    list.replace(temp, j);
  }
}
