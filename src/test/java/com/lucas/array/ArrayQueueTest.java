package com.lucas.array;

import com.lucas.util.Queue;
import com.lucas.util.QueueContractTest;

class ArrayQueueTest extends QueueContractTest {

  @Override
  protected Queue<Integer> createQueue() {
    return new ArrayQueue<>();
  }
}
