package pama1234.gdx.util.wrapper;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;

public class EntityCenterConcurrent<T extends UtilScreen,E extends EntityListener>extends EntityCenterAbstract<T,E,ConcurrentLinkedDeque<E>>{

  @Override
  public ConcurrentLinkedDeque<E> createList() {
    return new ConcurrentLinkedDeque<>();
  }

  @Override
  public Iterator<E> descendingIterator(ConcurrentLinkedDeque<E> list) {
    return descendingIteratorDeque(list);
  }

  public EntityCenterConcurrent(T p) {
    super(p);
  }
  public EntityCenterConcurrent(T p,E in) {
    this(p);
    list.add(in);
  }
  public EntityCenterConcurrent(T p,E[] in) {
    this(p);
    Collections.addAll(list,in);
  }
}