package com.lucas.array;

import com.lucas.util.Queue;
import java.util.NoSuchElementException;

/**
 * {@link Queue} backed by a resizable {@code Object[]}; the front of the queue is always
 * {@code array[0]}, so {@code pop} must shift every remaining element left. This is a
 * deliberately naive design — a circular buffer (tracking a moving {@code head} index instead of
 * shifting) would make {@code pop} O(1); compare against {@code LinkedQueue}.
 */
public class ArrayQueue<T> implements Queue<T> {

  final private static int DEFAULT_CAPACITY = 10;
  final private static int DEFAULT_GROWTH_FACTOR = 5;

  private int size;
  private Object[] array;

  public ArrayQueue() {
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
   * Complexity: O(1) amortized — occasionally O(n) when the backing array must grow.
   */
  @Override
  public boolean push(T element) {
    this.ensureCapacity();
    this.array[this.size] = element;
    this.size++;
    return true;
  }

  /**
   * Complexity: O(n) — every remaining element is shifted one slot left to keep the front at
   * index 0.
   */
  @SuppressWarnings("unchecked")
  @Override
  public T pop() {
    if (this.size == 0)
      throw new NoSuchElementException();
    Object element = this.array[0];
    for (int i = 0; i < this.size - 1; i++)
      this.array[i] = this.array[i + 1];
    this.array[this.size - 1] = null;
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
    return (T) this.array[0];
  }

  @Override
  public int size() {
    return this.size;
  }
}
