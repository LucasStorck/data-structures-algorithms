# data-structures-algorithms

An educational Java project on data structures: reimplementations of `List`, `Stack`, `Queue`
and sorting, each with two storage strategies — a resizable array and a linked list — to compare
the behavior and cost of each operation in practice.

## Running

Requires Java 25. This project has no Maven Wrapper; use a local Maven installation (or your
IDE's Maven integration).

```bash
mvn test                          # runs the 47 JUnit 5 tests
mvn compile exec:java -Dexec.mainClass=com.lucas.Main   # or run Main from your IDE
```

`Main.java` is a runnable demo: it instantiates each structure and prints the result of every
operation to the console. It does not replace the tests — it lets you read the behavior without
firing up a debugger.

## Code structure

```
com.lucas.util      -> interfaces: List, Stack, Queue, SortableList (the contract)
com.lucas.array     -> ArrayList, ArrayStack, ArrayQueue, SortableArrayList (resizable array)
com.lucas.linked    -> LinkedList, LinkedStack, LinkedQueue (linked nodes)
```

Each interface documents the contract (what each method guarantees, when it throws), and each
concrete class documents the Big-O complexity of every operation in its own Javadoc — complexity
depends on the storage strategy, so it makes no sense to document it on the interface.

> Naming note: `List`, `Stack`, `Queue`, `ArrayList` and `LinkedList` share their names with
> the `java.util` classes. This is deliberate — the goal is to compare against Java's standard
> implementations — but it means `import java.util.*` in this code would be ambiguous. Imports
> here are always explicit.

## Complexity per operation

| Operation         | `ArrayList` | `LinkedList` |
|-------------------|-------------|--------------|
| `get(index)`      | O(1)        | O(n)         |
| `position(elem)`  | O(n)        | O(n)         |
| `add(elem)`       | O(1) amort. | O(1)         |
| `addByIndex(0,·)` | O(n)        | O(1)         |
| `addByIndex(i,·)` | O(n)        | O(n)         |
| `removeByIndex`   | O(n)        | O(1) at the head, O(n) elsewhere |
| `replace`         | O(1)        | O(n)         |

| Operation | `ArrayStack` | `LinkedStack` |
|-----------|--------------|---------------|
| `push`    | O(1) amort.  | O(1)          |
| `pop`     | O(1)         | O(n)          |
| `peek`    | O(1)         | O(1)          |

`ArrayStack` keeps the top in the last used slot (`array[size-1]`), so `push`/`pop`/`peek`
never touch the start of the array. `LinkedStack` keeps the top in the `last` pointer: `push`
and `peek` are O(1), but `pop` has to walk the whole list from the head to find the node before
`last`, because the list only links forward (there is no `previous` pointer). This is the most
interesting point of the comparison — the array implementation is strictly better here.

| Operation | `ArrayQueue` | `LinkedQueue` |
|-----------|--------------|---------------|
| `push`    | O(1) amort.  | O(1)          |
| `pop`     | O(n)         | O(1)          |
| `peek`    | O(1)         | O(1)          |

`ArrayQueue` always keeps the front at `array[0]`, deliberately naive: every `pop` shifts all
remaining elements one position to the left. A circular buffer (a moving head index instead of
shifting) would make `pop` O(1) — a natural next exercise from here. `LinkedQueue`, with
`first`/`last` pointers, is already O(1) everywhere.

`SortableArrayList` adds `sort()` via **selection sort**: O(n²) time, O(1) extra space, not
stable (two equal elements may swap their relative order).

## Design decisions

- **Invalid index contract**: `get`, `addByIndex`, `removeByIndex` and `replace` always throw
  `IndexOutOfBoundsException` for an invalid index — in both implementations. `position(elem)`,
  on the other hand, returns `-1` when the element is not found (it does not throw), because
  not finding something is not a usage error, it is a valid result — the same convention as
  `java.util.List#indexOf`. `remove(elem)` uses that `-1` internally to decide between actually
  removing or returning `false`, never letting an invalid index reach `removeByIndex`.
- **Exception on empty collection**: `Stack` always throws `EmptyStackException` on an empty
  `pop`/`peek` (the convention of `java.util.Stack`); `Queue` always throws
  `NoSuchElementException` (the convention of `java.util.Queue#element()`/`remove()`). Before
  this adjustment, each implementation threw a different exception for the same condition — the
  point is that swapping `ArrayStack` for `LinkedStack` (or `ArrayQueue` for `LinkedQueue`)
  should not change what the calling code has to handle.
- **Array growth**: when full, the array grows in fixed steps of `+5` (arithmetic growth),
  rather than doubling in size (geometric growth, like `java.util.ArrayList`). Inserting `n`
  elements costs O(n²) copies in total under this scheme, versus amortized O(n) with doubling —
  left this way on purpose as a comparison point, it is not a bug.
- **`SortableArrayList` extends `ArrayList`**: it reuses all of `ArrayList`'s already-tested
  storage and operations, adding only `sort()`. (The previous version implemented `List`
  directly with stub methods that stored nothing — a skeleton that was never finished.)

## Tests

`src/test` uses a **contract test** pattern: `ListContractTest`, `StackContractTest` and
`QueueContractTest` are abstract classes that declare the tests once against the interface; each
implementation (`ArrayListTest`, `LinkedListTest`, ...) only provides the factory method
(`createList()`, `createStack()`, `createQueue()`). This guarantees that both implementations of
each interface are tested against exactly the same rules — if a new implementation breaks the
contract, the test fails without duplicating a single assertion.

```bash
mvn test
```

## Possible next steps

Out of the current scope, but natural paths for further exploration: a binary search tree, a
custom hash table, a heap/priority queue, a circular buffer to fix `ArrayQueue`'s O(n) `pop`,
and a `MergeSort`/`QuickSort` to compare with `SortableArrayList`'s selection sort.
