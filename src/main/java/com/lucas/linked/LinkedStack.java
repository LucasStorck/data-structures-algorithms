package com.lucas.linked;

import com.lucas.util.Stack;

import java.util.EmptyStackException;

/**
 * {@link Stack} backed by singly linked nodes; the tail pointer is the top of the stack, so
 * {@code push} is O(1) but {@code pop} must walk from the head to find the new tail.
 */
public class LinkedStack<T> implements Stack<T> {

  private static class Node<T> {
    private Node<T> next;
    private T value;

    public Node(T value) {
      this.next = null;
      this.value = value;
    }
  }

  private Node<T> first;
  private Node<T> last;
  private int size;

  public LinkedStack() {
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  /**
   * Complexity: O(1).
   */
  @Override
  public boolean push(T element) {
    Node<T> newNode = new Node<>(element);
    if (this.size == 0)
      this.first = newNode;
    else
      this.last.next = newNode;
    this.last = newNode;
    this.size++;
    return true;
  }

  /**
   * Complexity: O(n) — must walk from the head to find the node before the tail, since the
   * list is singly linked (no {@code previous} pointer).
   */
  @Override
  public T pop() {
    if (this.last == null)
      throw new EmptyStackException();
    T returnValue = this.last.value;
    if (this.last == this.first) {
      this.last = null;
      this.first = null;

    } else {
      Node<T> current = this.first;
      while (current.next != this.last)
        current = current.next;
      this.last = current;
      this.last.next = null;
    }
    this.size--;
    return returnValue;
  }

  /**
   * Complexity: O(1) — the tail pointer gives direct access to the top.
   */
  @Override
  public T peek() {
    if (this.size == 0)
      throw new EmptyStackException();
    return this.last.value;
  }

  @Override
  public int size() {
    return this.size;
  }
}
