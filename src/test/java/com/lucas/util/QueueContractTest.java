package com.lucas.util;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Contract tests every {@link Queue} implementation must satisfy: FIFO order, and
 * {@link NoSuchElementException} (never {@code null} or a different exception) when
 * {@code pop}/{@code peek} is called on an empty queue.
 */
public abstract class QueueContractTest {

  protected abstract Queue<Integer> createQueue();

  @Test
  void popReturnsElementsInInsertionOrder() {
    Queue<Integer> queue = createQueue();
    queue.push(1);
    queue.push(2);
    queue.push(3);
    assertEquals(1, queue.pop());
    assertEquals(2, queue.pop());
    assertEquals(3, queue.pop());
    assertEquals(0, queue.size());
  }

  @Test
  void peekReturnsFrontWithoutRemovingIt() {
    Queue<Integer> queue = createQueue();
    queue.push(1);
    queue.push(2);
    assertEquals(1, queue.peek());
    assertEquals(2, queue.size());
    assertEquals(1, queue.pop());
  }

  @Test
  void popOnEmptyQueueThrows() {
    assertThrows(NoSuchElementException.class, () -> createQueue().pop());
  }

  @Test
  void peekOnEmptyQueueThrows() {
    assertThrows(NoSuchElementException.class, () -> createQueue().peek());
  }
}
