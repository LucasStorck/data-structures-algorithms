package com.lucas.util;

public interface List<T> {
  T get(int index);

  int position(T element);

  boolean addByIndex(int index, T element);

  boolean add(T element);

  boolean removeByIndex(int index);

  boolean remove(T element);

  int size();
}
