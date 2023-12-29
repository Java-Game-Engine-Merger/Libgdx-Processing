package pama1234.util.wrapper;

import java.util.ArrayList;

import pama1234.util.UtilServer;
import pama1234.util.entity.ServerEntity;
import pama1234.util.listener.ServerEntityListener;

public class ServerStateCenter<P extends UtilServer,T extends ServerEntityListener>extends ServerEntity<P>{
  public final ArrayList<T> list=new ArrayList<T>();
  public int pointer;
  public ServerStateCenter(P p) {
    super(p);
  }
  public ServerStateCenter(P p,T in) {
    super(p);
    list.add(in);
  }
  public ServerStateCenter(P p,T[] in) {
    super(p);
    for(T i:in) list.add(i);
  }
  public void set(int in) {
    list.get(pointer).pause();
    list.get(in).resume();
    pointer=in;
  }
  @Override
  public void init() {
    for(T e:list) e.init();
  }
  @Override
  public void update() {
    list.get(pointer).update();
  }
  @Override
  public void display() {
    list.get(pointer).display();
  }
  @Override
  public void pause() {
    list.get(pointer).pause();
  }
  @Override
  public void resume() {
    list.get(pointer).resume();
  }
  @Override
  public void dispose() {
    for(T e:list) e.dispose();
  }
}
