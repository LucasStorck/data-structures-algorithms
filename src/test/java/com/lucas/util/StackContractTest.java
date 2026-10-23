package com.lucas.util;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Contract tests every {@link Stack} implementation must satisfy: LIFO order, and
 * {@link EmptyStackException} (never {@code null} or a different exception) when
 * {@code pop}/{@code peek} is called on an empty stack.
 */
public abstract class StackContractTest {

  protected abstract Stack<Integer> createStack();

  @Test
  void popReturnsElementsInReverseInsertionOrder() {
    Stack<Integer> stack = createStack();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    assertEquals(3, stack.pop());
    assertEquals(2, stack.pop());
    assertEquals(1, stack.pop());
    assertEquals(0, stack.size());
  }

  @Test
  void peekReturnsTopWithoutRemovingIt() {
    Stack<Integer> stack = createStack();
    stack.push(1);
    stack.push(2);
    assertEquals(2, stack.peek());
    assertEquals(2, stack.size());
    assertEquals(2, stack.pop());
  }

  @Test
  void growsPastInitialCapacity() {
    Stack<Integer> stack = createStack();
    for (int i = 0; i < 100; i++)
      stack.push(i);
    assertEquals(100, stack.size());
    for (int i = 99; i >= 0; i--)
      assertEquals(i, stack.pop());
  }

  @Test
  void stackIsReusableAfterBeingEmptied() {
    Stack<Integer> stack = createStack();
    stack.push(1);
    stack.pop();
    stack.push(2);
    assertEquals(2, stack.peek());
    assertEquals(1, stack.size());
  }

  @Test
  void popOnEmptyStackThrows() {
    assertThrows(EmptyStackException.class, () -> createStack().pop());
  }

  @Test
  void peekOnEmptyStackThrows() {
    assertThrows(EmptyStackException.class, () -> createStack().peek());
  }
}
