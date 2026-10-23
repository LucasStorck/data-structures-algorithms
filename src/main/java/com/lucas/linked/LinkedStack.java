package com.lucas.linked;

import com.lucas.util.Stack;

import java.util.EmptyStackException;

/**
 * {@link Stack} backed by singly linked nodes; the head node is the top of the stack, so
 * push/pop/peek only ever touch the first node and never walk the list.
 * <p>
 * Keeping the top at the head (not the tail) is what makes {@code pop} O(1) in a singly linked
 * list: removing the head only needs {@code head.next}, while removing the tail would need the
 * node <em>before</em> it, which can only be found by walking from the head.
 */
public class LinkedStack<T> implements Stack<T> {

  private static class Node<T> {
    private Node<T> next;
    private T value;

    public Node(T value, Node<T> next) {
      this.next = next;
      this.value = value;
    }
  }

  private Node<T> top;
  private int size;

  public LinkedStack() {
    this.top = null;
    this.size = 0;
  }

  /**
   * Complexity: O(1) — the new node becomes the head.
   */
  @Override
  public boolean push(T element) {
    this.top = new Node<>(element, this.top);
    this.size++;
    return true;
  }

  /**
   * Complexity: O(1) — the head is unlinked and its successor becomes the new top.
   */
  @Override
  public T pop() {
    if (this.top == null)
      throw new EmptyStackException();
    T returnValue = this.top.value;
    this.top = this.top.next;
    this.size--;
    return returnValue;
  }

  /**
   * Complexity: O(1).
   */
  @Override
  public T peek() {
    if (this.top == null)
      throw new EmptyStackException();
    return this.top.value;
  }

  @Override
  public int size() {
    return this.size;
  }
}
