package pama1234.gdx.util.wrapper;

import java.util.*;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.listener.EntityListener;

public abstract class EntityCenterAbstract<T extends UtilScreen,E extends EntityListener,L extends Collection<E>>extends Entity<T>{
  public static <T> Iterator<T> descendingIterator(List<? extends T> list) {
    ListIterator<? extends T> li=list.listIterator(list.size());
    return new Iterator<T>() {
      public boolean hasNext() {
        return li.hasPrevious();
      }
      public T next() {
        return li.previous();
      }
    };
  }
  public static <T> Iterator<T> descendingIterator(Deque<? extends T> list) {
    return (Iterator<T>)list.descendingIterator();
  }
  public abstract L createList();
  public abstract Iterator<E> descendingIterator(L list);
  public final L list=createList(),
    add=createList(),
    remove=createList();
  public boolean reverseDisplay=true;
  public EntityCenterAbstract(T p) {
    super(p);
  }
  public EntityCenterAbstract(T p,E in) {
    this(p);
    list.add(in);
  }
  public EntityCenterAbstract(T p,E[] in) {
    this(p);
    Collections.addAll(list,in);
  }
  public synchronized void refresh() {
    list.addAll(add);
    add.clear();
    list.removeAll(remove);
    // for(T i:remove) i.dispose();
    remove.clear();
  }
  @Override
  public void init() {
    for(E e:list) e.init();
  }
  @Override
  public void update() {
    refresh();
    for(E e:list) e.update();
  }
  @Override
  public void display() {
    if(reverseDisplay) {
      Iterator<E> di=descendingIterator(list);
      while(di.hasNext()) di.next().display();
    }else for(E e:list) e.display();
  }
  @Override
  public void pause() {
    for(E e:list) e.pause();
  }
  @Override
  public void resume() {
    for(E e:list) e.resume();
  }
  @Override
  public void dispose() {
    refresh();//TODO
    for(E e:list) e.dispose();
    for(E e:add) e.dispose();
    for(E e:remove) e.dispose();
  }
  @Override
  public void mousePressed(MouseInfo info) {
    for(E e:list) e.mousePressed(info);
  }
  @Override
  public void mouseReleased(MouseInfo info) {
    for(E e:list) e.mouseReleased(info);
  }
  @Override
  public void mouseMoved() {
    for(E e:list) e.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    for(E e:list) e.mouseDragged();
  }
  @Override
  public void mouseWheel(float x,float y) {
    for(E e:list) e.mouseWheel(x,y);
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {
    for(E e:list) e.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(final char key,final int keyCode) {
    for(E e:list) e.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(final char key) {
    for(E e:list) e.keyTyped(key);
  }
  @Override
  public void frameResized(final int w,final int h) {
    for(E e:list) e.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    for(E e:list) e.frameMoved(x,y);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    for(E e:list) e.touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    for(E e:list) e.touchEnded(info);
  }
  @Override
  public void touchMoved(TouchInfo info) {
    for(E e:list) e.touchMoved(info);
  }
  // public <O extends E> void doRemove(O e) {//TODO
  //   if(e instanceof E ie) remove.add(ie);
  // }

  // @Override
  // public void focusGained() {
  //   for(E e:list) e.focusGained();
  // }
  // @Override
  // public void focusLost() {
  //   for(E e:list) e.focusLost();
  // }
  //---------------------------------------------------------------------------
  public void addAll(E[] list) {
    Collections.addAll(add,list);
  }
  public void removeAll(E[] list) {
    Collections.addAll(remove,list);
  }
  public void addAll(List<E> list) {
    add.addAll(list);
  }
  public void removeAll(List<E> list) {
    remove.addAll(list);
  }
  public void add(E e) {
    add.add(e);
  }
  public void remove(E e) {
    remove.add(e);
  }
}