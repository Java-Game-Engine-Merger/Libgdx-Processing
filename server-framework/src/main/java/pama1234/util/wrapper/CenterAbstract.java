package pama1234.util.wrapper;

import java.util.Collection;
import java.util.Collections;

public abstract class CenterAbstract<T,L extends Collection<T>>{
  public abstract L createList();

  public final L list=createList(),
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
