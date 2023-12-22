package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.EntityNeo;
import pama1234.gdx.util.listener.StateEntityListener;

// TODO remove E  
public abstract class StateEntity<T extends UtilScreen,E extends StateEntityListener<?>>extends EntityNeo<T>{
  public StateEntity(T p) {
    super(p);
  }
}
