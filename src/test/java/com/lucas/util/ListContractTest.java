package com.lucas.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Contract tests every {@link List} implementation must satisfy, regardless of the underlying
 * storage strategy. Each implementation contributes its own test class that only supplies
 * {@link #createList()} — the assertions below run unchanged against every implementation, which
 * is exactly what "programming to an interface" is supposed to buy you.
 */
public abstract class ListContractTest {

  protected abstract List<String> createList();

  @Test
  void newListIsEmpty() {
    assertEquals(0, createList().size());
  }

  @Test
  void addAppendsAndGetReturnsWhatWasAdded() {
    List<String> list = createList();
    list.add("a");
    list.add("b");
    list.add("c");
    assertEquals(3, list.size());
    assertEquals("a", list.get(0));
    assertEquals("b", list.get(1));
    assertEquals("c", list.get(2));
  }

  @Test
  void getOutOfBoundsThrows() {
    List<String> list = createList();
    list.add("a");
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
  }

  @Test
  void positionUsesEqualsNotReferenceIdentity() {
    List<String> list = createList();
    list.add(new String("value"));
    // A different String object, equal by content but not by reference.
    assertEquals(0, list.position(new String("value")));
  }

  @Test
  void positionReturnsMinusOneWhenNotFound() {
    List<String> list = createList();
    list.add("a");
    assertEquals(-1, list.position("missing"));
  }

  @Test
  void addByIndexInsertsAtStartMiddleAndEnd() {
    List<String> list = createList();
    list.add("b");
    list.addByIndex(0, "a");
    list.addByIndex(2, "c");
    list.addByIndex(1, "a-b");
    assertEquals(4, list.size());
    assertEquals("a", list.get(0));
    assertEquals("a-b", list.get(1));
    assertEquals("b", list.get(2));
    assertEquals("c", list.get(3));
  }

  @Test
  void addByIndexOutOfBoundsThrows() {
    List<String> list = createList();
    list.add("a");
    assertThrows(IndexOutOfBoundsException.class, () -> list.addByIndex(-1, "x"));
    assertThrows(IndexOutOfBoundsException.class, () -> list.addByIndex(2, "x"));
  }

  @Test
  void removeByIndexShiftsRemainingElements() {
    List<String> list = createList();
    list.add("a");
    list.add("b");
    list.add("c");
    assertTrue(list.removeByIndex(1));
    assertEquals(2, list.size());
    assertEquals("a", list.get(0));
    assertEquals("c", list.get(1));
  }

  @Test
  void removeByIndexOutOfBoundsThrows() {
    List<String> list = createList();
    list.add("a");
    assertThrows(IndexOutOfBoundsException.class, () -> list.removeByIndex(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.removeByIndex(1));
  }

  @Test
  void removeExistingElementSucceeds() {
    List<String> list = createList();
    list.add("a");
    list.add("b");
    assertTrue(list.remove("a"));
    assertEquals(1, list.size());
    assertEquals("b", list.get(0));
  }

  @Test
  void removeMissingElementReturnsFalseWithoutThrowing() {
    List<String> list = createList();
    list.add("a");
    assertFalse(list.remove("missing"));
    assertEquals(1, list.size());
  }

  @Test
  void replaceOverwritesElementAtIndex() {
    List<String> list = createList();
    list.add("a");
    list.add("b");
    assertTrue(list.replace("z", 1));
    assertEquals("z", list.get(1));
    assertEquals(2, list.size());
  }

  @Test
  void growsPastInitialCapacity() {
    List<String> list = createList();
    for (int i = 0; i < 100; i++)
      list.add("e" + i);
    list.addByIndex(0, "first");
    assertEquals(101, list.size());
    assertEquals("first", list.get(0));
    assertEquals("e0", list.get(1));
    assertEquals("e99", list.get(100));
  }

  @Test
  void supportsNullElements() {
    List<String> list = createList();
    list.add("a");
    list.add(null);
    assertEquals(1, list.position(null));
    assertTrue(list.remove(null));
    assertEquals(-1, list.position(null));
    assertEquals(1, list.size());
  }

  @Test
  void addAfterRemovingLastElementAppendsAtTheEnd() {
    List<String> list = createList();
    list.add("a");
    list.add("b");
    list.removeByIndex(1);
    list.add("c");
    assertEquals(2, list.size());
    assertEquals("a", list.get(0));
    assertEquals("c", list.get(1));
  }

  @Test
  void listIsReusableAfterBeingEmptied() {
    List<String> list = createList();
    list.add("a");
    list.removeByIndex(0);
    assertEquals(0, list.size());
    list.add("b");
    list.addByIndex(0, "a");
    assertEquals(2, list.size());
    assertEquals("a", list.get(0));
    assertEquals("b", list.get(1));
  }

  @Test
  void replaceOutOfBoundsThrows() {
    List<String> list = createList();
    list.add("a");
    assertThrows(IndexOutOfBoundsException.class, () -> list.replace("z", -1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.replace("z", 1));
  }
}
