package com.lucas.util;

import java.util.NoSuchElementException;

/**
 * A FIFO (first-in, first-out) collection.
 * <p>
 * Both implementations ({@code ArrayQueue}, {@code LinkedQueue}) must throw
 * {@link NoSuchElementException} from {@code pop()}/{@code peek()} on an empty queue — never
 * return {@code null} or a different exception type — so callers can swap implementations
 * without changing error handling.
 *
 * @param <T> the type of elements held in this queue
 */
public interface Queue<T> {

  /**
   * Adds {@code element} to the back of the queue.
   */
  boolean push(T element);

  /**
   * Removes and returns the element at the front of the queue.
   *
   * @throws NoSuchElementException if the queue is empty
   */
  T pop();

  /**
   * Returns the element at the front of the queue without removing it.
   *
   * @throws NoSuchElementException if the queue is empty
   */
  T peek();

  /**
   * Returns the number of elements currently stored.
   */
  int size();
}
