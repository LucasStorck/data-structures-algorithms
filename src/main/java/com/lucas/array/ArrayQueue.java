package com.lucas.array;

import com.lucas.util.Queue;
import java.util.NoSuchElementException;

/**
 * {@link Queue} backed by a resizable {@code Object[]} used as a circular buffer: {@code head}
 * is the index of the front element and the back is {@code (head + size) % array.length}. Both
 * indices wrap around the end of the array, so {@code pop} just advances {@code head} instead of
 * shifting every remaining element left.
 */
public class ArrayQueue<T> implements Queue<T> {

  final private static int DEFAULT_CAPACITY = 10;
  final private static int DEFAULT_GROWTH_FACTOR = 5;

  private int head;
  private int size;
  private Object[] array;

  public ArrayQueue() {
    this.head = 0;
    this.size = 0;
    this.array = new Object[DEFAULT_CAPACITY];
  }

  /**
   * When full, copies the elements in queue order (front first) into a larger array, which also
   * "unwraps" the buffer back to {@code head == 0}.
   */
  private void ensureCapacity() {
    if (this.size == this.array.length) {
      Object[] newArray = new Object[this.array.length + DEFAULT_GROWTH_FACTOR];
      for (int i = 0; i < this.size; i++)
        newArray[i] = this.array[(this.head + i) % this.array.length];
      this.array = newArray;
      this.head = 0;
    }
  }

  /**
   * Complexity: O(1) amortized — occasionally O(n) when the backing array must grow.
   */
  @Override
  public boolean push(T element) {
    this.ensureCapacity();
    this.array[(this.head + this.size) % this.array.length] = element;
    this.size++;
    return true;
  }

  /**
   * Complexity: O(1) — the front slot is cleared and {@code head} advances (wrapping around).
   */
  @SuppressWarnings("unchecked")
  @Override
  public T pop() {
    if (this.size == 0)
      throw new NoSuchElementException();
    Object element = this.array[this.head];
    this.array[this.head] = null;
    this.head = (this.head + 1) % this.array.length;
    this.size--;
    return (T) element;
  }

  /**
   * Complexity: O(1).
   */
  @SuppressWarnings("unchecked")
  @Override
  public T peek() {
    if (this.size == 0)
      throw new NoSuchElementException();
    return (T) this.array[this.head];
  }

  @Override
  public int size() {
    return this.size;
  }
}
