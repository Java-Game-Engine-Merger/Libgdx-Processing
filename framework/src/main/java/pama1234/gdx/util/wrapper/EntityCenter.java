package pama1234.gdx.util.wrapper;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;

/**
 * 一种用于存储实体的链表型容器,相比{@link ArrayEntityCenter 数组型实体容器}，
 * 这个实体容器的实现在需要频繁增加和删除实体，并且不需要访问其特定位置的内容时，是更合适的
 * </p>
 * TODO 添加debug功能，对于传入add的null实体，应当在add.add方法调用时抛出异常，这一点需要创建新类继承LinkedList
 * 
 * @see ArrayEntityCenter
 *
 */
public class EntityCenter<T extends UtilScreen,E extends EntityListener>extends EntityCenterAbstract<T,E,LinkedList<E>>{

  @Override
  public LinkedList<E> createList() {
    return new LinkedList<>();
  }

  @Override
  public Iterator<E> descendingIterator(LinkedList<E> list) {
    return descendingIteratorList(list);
  }

  public EntityCenter(T p) {
    super(p);
  }
  public EntityCenter(T p,E in) {
    this(p);
    list.add(in);
  }
  public EntityCenter(T p,E[] in) {
    this(p);
    Collections.addAll(list,in);
  }
}