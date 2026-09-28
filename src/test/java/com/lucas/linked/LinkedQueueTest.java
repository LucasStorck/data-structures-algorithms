package com.lucas.linked;

import com.lucas.util.Queue;
import com.lucas.util.QueueContractTest;

class LinkedQueueTest extends QueueContractTest {

  @Override
  protected Queue<Integer> createQueue() {
    return new LinkedQueue<>();
  }
}
