package com.lucas.linked;

import com.lucas.util.List;

/**
 * {@link List} backed by singly linked nodes, with a head and tail pointer.
 * <p>
 * Unlike {@code ArrayList}, there is no resizing and no shifting of untouched elements, but
 * every index-based operation other than the two ends requires walking the list node by node.
 */
public class LinkedList<T> implements List<T> {

  private static final int EMPTY = -1;

  private static class Node<T> {
    private T value;
    private Node<T> next;

    public Node(T element) {
      this.value = element;
      this.next = null;
    }
  }

  private Node<T> head;
  private Node<T> tail;
  private int size;

  public LinkedList() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   * Complexity: O(n) — must walk {@code index} nodes from the head.
   */
  @Override
  public T get(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("Index:" + index);
    Node<T> current = head;
    for (int i = 0; i < index; i++)
      current = current.next;
    return current.value;
  }

  /**
   * Complexity: O(n) — linear scan; must compare every node until a match (or the end) is found.
   */
  @Override
  public int position(T element) {
    Node<T> current = head;
    for (int i = 0; i < this.size; i++) {
      if (element == null) {
        if (current.value == null)
          return i;
      } else {
        if (element.equals(current.value))
          return i;
      }
      current = current.next;
    }
    return EMPTY;
  }

  /**
   * Complexity: O(1) at the head, O(n) elsewhere — walking to the node before {@code index}
   * dominates the cost; the link update itself is O(1).
   */
  @Override
  public boolean addByIndex(int index, T element) {
    if (index < 0 || index > this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    if (index == this.size)
      return add(element);
    Node<T> newNode = new Node<T>(element);
    if (index == 0) {
      newNode.next = this.head;
      this.head = newNode;
    } else {
      Node<T> current = this.head;
      for (int i = 0; i < index - 1; i++)
        current = current.next;
      newNode.next = current.next;
      current.next = newNode;
    }
    this.size++;
    return true;
  }

  /**
   * Complexity: O(1) — the tail pointer avoids walking the list.
   */
  @Override
  public boolean add(T element) {
    Node<T> newNode = new Node<>(element);
    if (this.size == 0)
      this.head = newNode;
    else
      this.tail.next = newNode;
    this.tail = newNode;
    this.size++;
    return true;
  }

  /**
   * Complexity: O(1) at the head, O(n) elsewhere — same rationale as {@link #addByIndex}.
   */
  @Override
  public boolean removeByIndex(int index) {
    if (index < 0 || index >= this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    if (index == 0) {
      this.head = this.head.next;
      if (this.head == null)
        this.tail = null;
    } else {
      Node<T> node = this.head;
      for (int i = 0; i < index - 1; i++)
        node = node.next;
      node.next = node.next.next;
      if (index == this.size - 1) {
        this.tail = node;
      }
    }
    this.size--;
    return true;
  }

  /**
   * Complexity: O(n) — {@link #position} scans linearly, then {@link #removeByIndex} walks
   * again.
   */
  @Override
  public boolean remove(T element) {
    int index = this.position(element);
    if (index == EMPTY)
      return false;
    return removeByIndex(index);
  }

  /**
   * Complexity: O(n) — must walk {@code index} nodes from the head.
   */
  @Override
  public boolean replace(T element, int index) {
    if (index < 0 || index >= this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    Node<T> current = this.head;
    for (int i = 0; i < index; i++)
      current = current.next;
    current.value = element;
    return true;
  }

  @Override
  public int size() {
    return this.size;
  }
}
