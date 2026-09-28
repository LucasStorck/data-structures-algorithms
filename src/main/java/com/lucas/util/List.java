package com.lucas.util;

/**
 * A linear, indexed collection of elements.
 * <p>
 * This is the contract every implementation ({@code ArrayList}, {@code LinkedList}) must honor,
 * regardless of the underlying storage strategy. The two implementations have very different
 * time complexity per operation (contiguous array vs. singly linked nodes) — see their class
 * Javadoc for the complexity of each method.
 *
 * @param <T> the type of elements held in this list
 */
public interface List<T> {

  /**
   * Returns the element at the given index.
   *
   * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size()}
   */
  T get(int index);

  /**
   * Returns the index of the first occurrence of {@code element}, using {@link Object#equals}
   * (or reference equality when {@code element} is {@code null}).
   *
   * @return the index of the element, or {@code -1} if it is not present
   */
  int position(T element);

  /**
   * Inserts {@code element} at {@code index}, shifting subsequent elements one position to the
   * right.
   *
   * @throws IndexOutOfBoundsException if {@code index < 0 || index > size()}
   */
  boolean addByIndex(int index, T element);

  /**
   * Appends {@code element} to the end of the list.
   */
  boolean add(T element);

  /**
   * Removes the element at {@code index}, shifting subsequent elements one position to the left.
   *
   * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size()}
   */
  boolean removeByIndex(int index);

  /**
   * Removes the first occurrence of {@code element}.
   *
   * @return {@code true} if an element was removed, {@code false} if {@code element} was not
   *     found (this method never throws for a missing element)
   */
  boolean remove(T element);

  /**
   * Replaces the element at {@code index} with {@code element}.
   *
   * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size()}
   */
  boolean replace(T element, int index);

  /**
   * Returns the number of elements currently stored.
   */
  int size();
}
