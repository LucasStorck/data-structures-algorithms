package com.lucas;

import com.lucas.array.ArrayList;
import com.lucas.array.ArrayQueue;
import com.lucas.array.ArrayStack;
import com.lucas.array.SortableArrayList;
import com.lucas.linked.LinkedList;
import com.lucas.linked.LinkedQueue;
import com.lucas.linked.LinkedStack;
import com.lucas.util.List;
import com.lucas.util.Queue;
import com.lucas.util.Stack;

/**
 * Runnable demo exercising every structure in this project. It is not a test suite (see
 * {@code src/test}) — it exists so a reader can run one file and see each structure's behavior
 * printed to the console.
 */
public class Main {

  static void main() {
    demoList("ArrayList", new ArrayList<>());
    demoList("LinkedList", new LinkedList<>());

    demoStack("ArrayStack", new ArrayStack<>());
    demoStack("LinkedStack", new LinkedStack<>());

    demoQueue("ArrayQueue", new ArrayQueue<>());
    demoQueue("LinkedQueue", new LinkedQueue<>());

    demoSort();
  }

  private static void demoList(String label, List<String> list) {
    IO.println("--- " + label + " ---");
    list.add("a");
    list.add("b");
    list.addByIndex(1, "a-b");
    IO.println("after add/addByIndex: " + toString(list));
    IO.println("position(\"b\") = " + list.position("b"));
    IO.println("position(\"missing\") = " + list.position("missing"));
    list.remove("a-b");
    IO.println("after remove(\"a-b\"): " + toString(list));
    IO.println();
  }

  private static void demoStack(String label, Stack<Integer> stack) {
    IO.println("--- " + label + " ---");
    stack.push(1);
    stack.push(2);
    stack.push(3);
    IO.println("peek() = " + stack.peek() + " (should be the last pushed: 3)");
    IO.println("pop() = " + stack.pop());
    IO.println("pop() = " + stack.pop());
    IO.println("pop() = " + stack.pop());
    IO.println();
  }

  private static void demoQueue(String label, Queue<Integer> queue) {
    IO.println("--- " + label + " ---");
    queue.push(1);
    queue.push(2);
    queue.push(3);
    IO.println("peek() = " + queue.peek() + " (should be the first pushed: 1)");
    IO.println("pop() = " + queue.pop());
    IO.println("pop() = " + queue.pop());
    IO.println("pop() = " + queue.pop());
    IO.println();
  }

  private static void demoSort() {
    IO.println("--- SortableArrayList ---");
    SortableArrayList<Integer> list = new SortableArrayList<>();
    for (int value : new int[] {5, 3, 8, 1, 9, 2})
      list.add(value);
    IO.println("before sort: " + toString(list));
    list.sort();
    IO.println("after sort: " + toString(list));
  }

  private static <T> String toString(List<T> list) {
    StringBuilder builder = new StringBuilder("[");
    for (int i = 0; i < list.size(); i++) {
      if (i > 0)
        builder.append(", ");
      builder.append(list.get(i));
    }
    return builder.append("]").toString();
  }
}
