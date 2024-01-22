package pama1234.util.wrapper;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public abstract class CenterAbstract<T,E extends Collection<T>>{
  public abstract E createList();

  public final E list=createList(),
    add=createList(),
    remove=createList();
  public synchronized void refresh() {
    list.addAll(add);
    add.clear();
    list.removeAll(remove);
    remove.clear();
  }

  public void addAll(T[] list) {
    Collections.addAll(add,list);
  }
  public void removeAll(T[] list) {
    Collections.addAll(remove,list);
  }
  public void add(T e) {
    add.add(e);
  }
  public void remove(T e) {
    remove.add(e);
  }
  public void listAdd(T e) {
    list.add(e);
  }
}
