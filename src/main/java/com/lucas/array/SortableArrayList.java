package com.lucas.array;

import com.lucas.util.SortableList;

public class SortableArrayList <T extends Comparable<T>> implements SortableList<T>
{
  @Override
  public void sort() {
    for(int j = 0; j < this.size() - 1; j++){
      int position = j;
      for(int i = j + 1; i < this.size(); i++){
        if(this.get(i).compareTo(this.get(position)) < 0){
          position = i;
        }
        if(position != j){
          T temp = this.get(j);
          this.replace(this.get(position), j);
          this.replace(temp, position);
        }
      }
    }
  }

  @Override
  public T get(int index) {
    return null;
  }

  @Override
  public int position(T element) {
    return 0;
  }

  @Override
  public boolean addByIndex(int index, T element) {
    return false;
  }

  @Override
  public boolean add(T element) {
    return false;
  }

  @Override
  public boolean removeByIndex(int index) {
    return false;
  }

  @Override
  public boolean remove(T element) {
    return false;
  }

  @Override
  public boolean replace(T element, int index) {
    return false;
  }

  @Override
  public int size() {
    return 0;
  }
}
