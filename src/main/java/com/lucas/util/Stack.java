package com.lucas.util;

public interface Stack<T> {

  boolean push(T newElement);
  T pop();
  T peek();
  int size();
}
