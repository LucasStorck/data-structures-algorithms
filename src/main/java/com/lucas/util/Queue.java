package com.lucas.util;

public interface Queue<T> {
  boolean push(T element);
  T pop();
  T peek();
  int size();
}
