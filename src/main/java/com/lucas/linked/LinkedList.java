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
    if(index < 0 || index >= size)
      throw new IndexOutOfBoundsException();
    Node<T> current = head;
    for(int i = 0; i < index; i++)
      current = current.next;
    return (T) current.value;
  }

  @Override
  public int position(T element) {
    Node<T> current = head;
    for(int i = 0; i < this.size; i++){
      if(current.value == element)
        return i;
      current = current.next;
    }
    return -1;
  }

  @Override
  public boolean addByIndex(int index, T element) {
    if (index < 0 || index > this.size)
      return false;
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
    if (index < 0 || index >= this.size)
      return false;
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

  @Override
  public boolean remove(T element) {
    int index = this.position(element);
    return removeByIndex(index);
  }

  @Override
  public int size() {
    return this.size;
  }
}
