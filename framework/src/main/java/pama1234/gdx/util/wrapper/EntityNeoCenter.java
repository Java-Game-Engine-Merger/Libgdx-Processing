package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityNeoListener;

public class EntityNeoCenter<T extends UtilScreen,E extends EntityNeoListener>extends EntityCenter<T,E> implements EntityNeoListener{

  public EntityNeoCenter(T p) {
    super(p);
  }

  @Override
  public void displayCam() {
    for(E e:list) e.displayCam();
  }

}
