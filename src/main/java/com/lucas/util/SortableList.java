package com.lucas.util;

public interface SortableList <T extends Comparable<T>> extends List<T> {
  void sort();
}
