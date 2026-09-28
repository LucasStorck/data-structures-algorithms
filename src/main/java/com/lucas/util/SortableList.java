package com.lucas.util;

/**
 * A {@link List} whose elements can be sorted in place, ascending, using their
 * {@linkplain Comparable natural ordering}.
 *
 * @param <T> the type of elements held in this list
 */
public interface SortableList<T extends Comparable<T>> extends List<T> {

  /**
   * Sorts the elements in place, ascending, using {@link Comparable#compareTo}.
   */
  void sort();
}
