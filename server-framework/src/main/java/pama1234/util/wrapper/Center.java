package pama1234.util.wrapper;

import java.util.LinkedList;

public class Center<T> extends CenterAbstract<T,LinkedList<T>>{

  @Override
  public LinkedList<T> createList() {
    return new LinkedList<>();
  }
}
