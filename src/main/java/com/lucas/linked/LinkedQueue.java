package com.lucas.linked;

import com.lucas.util.Queue;

import java.util.NoSuchElementException;

/**
 * {@link Queue} backed by singly linked nodes with a {@code first} (front) and {@code last}
 * (back) pointer, so every operation touches only one end and needs no shifting.
 */
public class LinkedQueue<T> implements Queue<T> {

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

  public LinkedQueue() {
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  /**
   * Complexity: O(1) — the {@code last} pointer gives direct access to the back.
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
   * Complexity: O(1) — the {@code first} pointer gives direct access to the front.
   */
  @Override
  public T pop() {
    if (this.first == null)
      throw new NoSuchElementException();
    T returnValue = this.first.value;
    this.first = this.first.next;
    if (this.first == null)
      this.last = null;
    this.size--;
    return returnValue;
  }

  /**
   * Complexity: O(1).
   */
  @Override
  public T peek() {
    if (this.first == null)
      throw new NoSuchElementException();
    return this.first.value;
  }

  @Override
  public int size() {
    return this.size;
  }
}
