package com.lucas.linked;

import com.lucas.util.List;

public class LinkedList<T> implements List<T> {

  private class Node<T> {
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

  @Override
  public T get(int index) {
    return null;
  }

  @Override
  public int position(T element) {
    return 0;
  }

  @Override
  public boolean addByIndex(int index, T element) {
    if (index < 0 || index >= this.size)
      return false;
    if (index == this.size)
      return add(element);
    Node<T> newNode = new Node<T>(element);
    if (index == 0) {
      newNode.next = this.head;
      this.head = newNode;
    } else if (index < this.size) {
      Node<T> node = this.head;
      for (int i = 0; i < index - 1; i++)
        node = node.next;
      newNode.next = node.next;
      node.next = newNode;
    }
    this.size++;
    return true;
  }

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

  @Override
  public boolean removeByIndex(int index) {
    return false;
  }

  @Override
  public boolean remove(T element) {
    return false;
  }

  @Override
  public int size() {
    return 0;
  }
}
