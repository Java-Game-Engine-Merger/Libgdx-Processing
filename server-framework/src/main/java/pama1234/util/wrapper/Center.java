package pama1234.util.wrapper;

import java.util.LinkedList;

public class Center<T>{
  public final LinkedList<T> list=new LinkedList<T>(),
    add=new LinkedList<T>(),
    remove=new LinkedList<T>();
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
