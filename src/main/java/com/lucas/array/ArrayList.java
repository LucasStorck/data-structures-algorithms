package com.lucas.array;

import com.lucas.util.List;

public class ArrayList<T> implements List<T> {

  final private static int DEFAULT_CAPACITY = 10;
  final private static int DEFAULT_GROWTH_FACTOR = 5;
  final private static int EMPTY = -1;

  private int size = 0;
  private Object[] array;

  public ArrayList() {
    super();
    this.size = 0;
    this.array = new Object[DEFAULT_CAPACITY];
  }

  private void ensureCapacity() {
    if (this.size == this.array.length) {
      Object[] newArray = new Object[this.array.length + DEFAULT_GROWTH_FACTOR];
      System.arraycopy(this.array, 0, newArray, 0, this.array.length);
      this.array = newArray;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public T get(int index) {
    if (index < 0 || index >= this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    return (T) this.array[index];
  }

  @Override
  public int position(T element) {
    for (int i = 0; i < this.size; i++) {
      if (element == null) {
        if (this.array[i] == null)
          return i;
      } else {
        if (element.equals(this.array[i]))
          return i;
      }
    }
    throw new IndexOutOfBoundsException("Index:" + this.size + " Element:" + element);
  }

  @Override
  public boolean addByIndex(int index, T element) {
    if (index < 0 || index > this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    this.ensureCapacity();
    for (int i = this.size; i > index; i--)
      this.array[i] = this.array[i - 1];
    this.array[index] = element;
    this.size++;
    return true;
  }

  @Override
  public boolean add(T element) {
    this.ensureCapacity();
    this.array[this.size] = element;
    this.size++;
    return true;
  }

  @Override
  public boolean removeByIndex(int index) {
    if (index < 0 || index >= this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    for (int i = index; i < this.size - 1; i++)
      this.array[i] = this.array[i + 1];
    this.array[this.size - 1] = null;
    this.size--;
    return true;
  }

  @Override
  public boolean remove(T element) {
    int index = this.position(element);
    if (index == EMPTY)
      return false;
    return removeByIndex(index);
  }

  @Override
  public boolean replace(T element, int index) {
    if(index < 0 || index >= this.size)
      throw new IndexOutOfBoundsException("Index:" + index);
    this.array[index] = element;
    return true;
  }

  @Override
  public int size() {
    return this.size;
  }
}
