package com.lucas.sort;

import com.lucas.array.ArrayList;
import com.lucas.linked.LinkedList;
import com.lucas.util.List;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Runs the same inputs through every algorithm in {@link Sorting}, against both {@link List}
 * implementations, and checks the result against {@link Arrays#sort}.
 */
class SortingTest {

  private record Algorithm(String name, Consumer<List<Integer>> sort) {
  }

  private record Implementation(String name, Supplier<List<Integer>> factory) {
  }

  private static final Algorithm[] ALGORITHMS = {
      new Algorithm("selectionSort", Sorting::selectionSort),
      new Algorithm("mergeSort", Sorting::mergeSort),
      new Algorithm("quickSort", Sorting::quickSort),
  };

  private static final Implementation[] IMPLEMENTATIONS = {
      new Implementation("ArrayList", ArrayList::new),
      new Implementation("LinkedList", LinkedList::new),
  };

  private static final int[][] INPUTS = {
      {},
      {42},
      {2, 1},
      {1, 2, 3, 4, 5},
      {5, 4, 3, 2, 1},
      {3, 1, 4, 1, 5, 9, 2, 6, 5, 3},
      {7, 7, 7, 7},
      randomInts(200, 1),
  };

  @TestFactory
  Stream<DynamicTest> everyAlgorithmSortsEveryInputOnEveryImplementation() {
    return Arrays.stream(ALGORITHMS).flatMap(algorithm ->
        Arrays.stream(IMPLEMENTATIONS).flatMap(implementation ->
            Arrays.stream(INPUTS).map(input -> DynamicTest.dynamicTest(
                algorithm.name() + " on " + implementation.name() + " " + describe(input),
                () -> {
                  List<Integer> list = implementation.factory().get();
                  for (int value : input)
                    list.add(value);
                  algorithm.sort().accept(list);

                  int[] expected = input.clone();
                  Arrays.sort(expected);
                  assertArrayEquals(expected, toArray(list));
                }))));
  }

  @Test
  void mergeSortIsStable() {
    record Item(int key, String label) implements Comparable<Item> {
      @Override
      public int compareTo(Item other) {
        return Integer.compare(this.key, other.key);
      }
    }
    List<Item> list = new ArrayList<>();
    Item firstOne = new Item(1, "first");
    Item secondOne = new Item(1, "second");
    list.add(new Item(2, "x"));
    list.add(firstOne);
    list.add(new Item(0, "y"));
    list.add(secondOne);

    Sorting.mergeSort(list);

    assertEquals(0, list.get(0).key());
    assertSame(firstOne, list.get(1));
    assertSame(secondOne, list.get(2));
    assertEquals(2, list.get(3).key());
  }

  private static int[] randomInts(int count, long seed) {
    Random random = new Random(seed);
    int[] values = new int[count];
    for (int i = 0; i < count; i++)
      values[i] = random.nextInt(50);
    return values;
  }

  private static String describe(int[] input) {
    return input.length > 10 ? "(" + input.length + " random)" : Arrays.toString(input);
  }

  private static int[] toArray(List<Integer> list) {
    int[] values = new int[list.size()];
    for (int i = 0; i < list.size(); i++)
      values[i] = list.get(i);
    return values;
  }
}
