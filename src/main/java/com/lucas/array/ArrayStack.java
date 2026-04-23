package com.lucas.array;
import com.lucas.util.Stack;

import java.util.EmptyStackException;

public class ArrayStack<T> implements Stack<T> {

  final static private  int DEFAULT_INITIAL_CAPACITY = 10;
  final static private  int DEFAULT_GROWTH_FACTOR = 5;

  private int size;
  private Object[] array;

  public ArrayStack() {
    this.size = 0;
    this.array = new Object[DEFAULT_INITIAL_CAPACITY];
  }

  private void ensureCapacity() {
    if (this.size == this.array.length) {
      Object[] newArray = new Object[this.array.length + DEFAULT_GROWTH_FACTOR];
      System.arraycopy(this.array, 0, newArray, 0, this.array.length);
      this.array = newArray;
    }
  }

  @Override
  public boolean push(T newElement) {
    this.ensureCapacity();
    this.array[this.size] = newElement;
    this.size++;
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T pop() {
    if (this.size == 0)
      throw new EmptyStackException();
    Object element = this.array[this.size - 1];
    this.array[this.size - 1] = null;
    this.size--;
    return (T) element;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T peek() {
    if (this.size == 0)
      throw new IllegalStateException("Stack is empty");
    return (T) this.array[0];
  }

  @Override
  public int size() {
    return this.size;
  }
}
