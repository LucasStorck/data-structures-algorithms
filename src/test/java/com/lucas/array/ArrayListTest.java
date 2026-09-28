package com.lucas.array;

import com.lucas.util.List;
import com.lucas.util.ListContractTest;

class ArrayListTest extends ListContractTest {

  @Override
  protected List<String> createList() {
    return new ArrayList<>();
  }
}
