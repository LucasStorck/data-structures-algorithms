package com.lucas.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortableArrayListTest {

  @Test
  void sortOnEmptyListDoesNothing() {
    SortableArrayList<Integer> list = new SortableArrayList<>();
    list.sort();
    assertEquals(0, list.size());
  }

  @Test
  void sortOnSingleElementDoesNothing() {
    SortableArrayList<Integer> list = new SortableArrayList<>();
    list.add(42);
    list.sort();
    assertEquals(42, list.get(0));
  }

  @Test
  void sortsElementsInReverseOrder() {
    SortableArrayList<Integer> list = new SortableArrayList<>();
    for (int value : new int[] {5, 4, 3, 2, 1})
      list.add(value);
    list.sort();
    for (int i = 0; i < list.size(); i++)
      assertEquals(i + 1, list.get(i));
  }

  @Test
  void sortsElementsAlreadyInOrder() {
    SortableArrayList<Integer> list = new SortableArrayList<>();
    for (int value : new int[] {1, 2, 3, 4, 5})
      list.add(value);
    list.sort();
    for (int i = 0; i < list.size(); i++)
      assertEquals(i + 1, list.get(i));
  }

  @Test
  void sortsElementsInRandomOrderWithDuplicates() {
    SortableArrayList<Integer> list = new SortableArrayList<>();
    for (int value : new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3})
      list.add(value);
    list.sort();
    int[] expected = {1, 1, 2, 3, 3, 4, 5, 5, 6, 9};
    for (int i = 0; i < list.size(); i++)
      assertEquals(expected[i], list.get(i));
  }
}
