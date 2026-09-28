package com.lucas.array;

import com.lucas.util.Stack;
import com.lucas.util.StackContractTest;

class ArrayStackTest extends StackContractTest {

  @Override
  protected Stack<Integer> createStack() {
    return new ArrayStack<>();
  }
}
