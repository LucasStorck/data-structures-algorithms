package com.lucas.array;

import com.lucas.util.List;

/**
 * {@link List} backed by a contiguous, resizable {@code Object[]}.
 * <p>
 * Growth strategy: when full, the backing array grows by a fixed
 * {@link #DEFAULT_GROWTH_FACTOR} (arithmetic growth), not by doubling (geometric growth) like
 * {@code java.util.ArrayList}. That makes inserting {@code n} elements cost O(n^2/DEFAULT_GROWTH_FACTOR)
 * copied slots overall instead of O(n) amortized — a deliberate contrast worth comparing against
 * {@code java.util.ArrayList}'s doubling strategy.
 */
public class ArrayList<T> implements List<T> {

  final private static int DEFAULT_CAPACITY = 10;
  final private static int DEFAULT_GROWTH_FACTOR = 5;
  final private static int EMPTY = -1;

  private int size = 0;
  private Object[] array;

  public ArrayList() {
    super();
    this.size = 0;
    this.array = new Object[DEFAULT_CAPACITY];
  }

  private void ensureCapacity() {
    if (this.size == this.array.length) {
      Object[] newArray = new Object[this.array.length + DEFAULT_GROWTH_FACTOR];
      System.arraycopy(this.array, 0, newArray, 0, this.array.length);
      this.array = newArray;
    }
  }

  /**
   * Complexity: O(1) — direct array indexing.
   */
  @SuppressWarnings("unchecked")
  @Override
  public T get(int index) {
    if (index < 0 || index >= this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    return (T) this.array[index];
  }

  /**
   * Complexity: O(n) — linear scan; must compare every element until a match (or the end) is
   * found.
   */
  @Override
  public int position(T element) {
    for (int i = 0; i < this.size; i++) {
      if (element == null) {
        if (this.array[i] == null)
          return i;
      } else {
        if (element.equals(this.array[i]))
          return i;
      }
    }
    return EMPTY;
  }

  /**
   * Complexity: O(n) — every element from {@code index} onward is shifted one slot to the right;
   * occasionally O(n) extra for a resize.
   */
  @Override
  public boolean addByIndex(int index, T element) {
    if (index < 0 || index > this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    this.ensureCapacity();
    for (int i = this.size; i > index; i--)
      this.array[i] = this.array[i - 1];
    this.array[index] = element;
    this.size++;
    return true;
  }

  /**
   * Complexity: O(1) amortized — occasionally O(n) when the backing array must grow.
   */
  @Override
  public boolean add(T element) {
    this.ensureCapacity();
    this.array[this.size] = element;
    this.size++;
    return true;
  }

  /**
   * Complexity: O(n) — every element after {@code index} is shifted one slot to the left.
   */
  @Override
  public boolean removeByIndex(int index) {
    if (index < 0 || index >= this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    for (int i = index; i < this.size - 1; i++)
      this.array[i] = this.array[i + 1];
    this.array[this.size - 1] = null;
    this.size--;
    return true;
  }

  /**
   * Complexity: O(n) — {@link #position} scans linearly, then {@link #removeByIndex} shifts.
   */
  @Override
  public boolean remove(T element) {
    int index = this.position(element);
    if (index == EMPTY)
      return false;
    return removeByIndex(index);
  }

  /**
   * Complexity: O(1) — direct array indexing.
   */
  @Override
  public boolean replace(T element, int index) {
    if (index < 0 || index >= this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    this.array[index] = element;
    return true;
  }

  @Override
  public int size() {
    return this.size;
  }
}
