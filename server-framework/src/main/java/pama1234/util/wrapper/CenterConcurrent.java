package pama1234.util.wrapper;

import java.util.concurrent.ConcurrentLinkedDeque;

public class CenterConcurrent<T>{
  public final ConcurrentLinkedDeque<T> list=new ConcurrentLinkedDeque<T>(),
    add=new ConcurrentLinkedDeque<T>(),
    remove=new ConcurrentLinkedDeque<T>();
  public synchronized void refresh() {
    list.addAll(add);
    add.clear();
    list.removeAll(remove);
    remove.clear();
  }

  public void addAll(T[] list) {
    for(T e:list) add.add(e);
  }
  public void removeAll(T[] list) {
    for(T e:list) remove.add(e);
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
