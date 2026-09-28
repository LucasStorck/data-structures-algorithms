package com.lucas.linked;

import com.lucas.util.List;
import com.lucas.util.ListContractTest;

class LinkedListTest extends ListContractTest {

  @Override
  protected List<String> createList() {
    return new LinkedList<>();
  }
}
