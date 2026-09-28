package com.lucas.util;

import java.util.EmptyStackException;

/**
 * A LIFO (last-in, first-out) collection.
 * <p>
 * Both implementations ({@code ArrayStack}, {@code LinkedStack}) must throw
 * {@link EmptyStackException} from {@code pop()}/{@code peek()} on an empty stack — never return
 * {@code null} or a different exception type — so callers can swap implementations without
 * changing error handling.
 *
 * @param <T> the type of elements held in this stack
 */
public interface Stack<T> {

  /**
   * Pushes {@code newElement} onto the top of the stack.
   */
  boolean push(T newElement);

  /**
   * Removes and returns the element at the top of the stack.
   *
   * @throws EmptyStackException if the stack is empty
   */
  T pop();

  /**
   * Returns the element at the top of the stack without removing it.
   *
   * @throws EmptyStackException if the stack is empty
   */
  T peek();

  /**
   * Returns the number of elements currently stored.
   */
  int size();
}
