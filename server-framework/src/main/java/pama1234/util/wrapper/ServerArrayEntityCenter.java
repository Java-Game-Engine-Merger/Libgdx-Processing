package pama1234.util.wrapper;

import pama1234.util.UtilServer;
import pama1234.util.entity.ServerEntity;
import pama1234.util.listener.ServerEntityListener;

import java.util.ArrayList;

public class ServerArrayEntityCenter<P extends UtilServer,T extends ServerEntityListener>extends ServerEntity<P>{
  public final ArrayList<T> list=new ArrayList<T>(),
    add=new ArrayList<T>(),
    remove=new ArrayList<T>();
  public boolean reverseDisplay=true;
  public ServerArrayEntityCenter(P p) {
    super(p);
  }
  // public ServerArrayEntityCenter(UtilServer p,T in) {
  //   super(p);
  //   list.add(in);
  // }
  // public ServerArrayEntityCenter(UtilServer p,T[] in) {
  //   super(p);
  //   for(T i:in) list.add(i);
  // }
  public void refresh() {
    list.addAll(add);
    add.clear();
    list.removeAll(remove);
    // for(T i:remove) i.dispose();
    remove.clear();
  }
  @Override
  public void init() {
    for(T e:list) e.init();
  }
  @Override
  public void update() {
    refresh();
    for(T e:list) e.update();
  }
  @Override
  public void display() {
    if(reverseDisplay) {
      // Iterator<T> di=list.descendingIterator();
      // while(di.hasNext()) di.next().display();
      for(int i=list.size()-1;i>=0;i--) list.get(i).display();
    }else for(T e:list) e.display();
  }
  @Override
  public void pause() {
    for(T e:list) e.pause();
  }
  @Override
  public void resume() {
    for(T e:list) e.resume();
  }
  @Override
  public void dispose() {
    for(T e:list) e.dispose();
    for(T e:add) e.dispose();
    for(T e:remove) e.dispose();
  }
}