package com.lucas.linked;

import com.lucas.util.Stack;
import com.lucas.util.StackContractTest;

class LinkedStackTest extends StackContractTest {

  @Override
  protected Stack<Integer> createStack() {
    return new LinkedStack<>();
  }
}
