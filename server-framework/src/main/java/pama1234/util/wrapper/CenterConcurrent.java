package pama1234.util.wrapper;

import java.util.concurrent.ConcurrentLinkedDeque;

public class CenterConcurrent<T> extends CenterAbstract<T,ConcurrentLinkedDeque<T>>{

  @Override
  public ConcurrentLinkedDeque<T> createList() {
    return new ConcurrentLinkedDeque<>();
  }
}
