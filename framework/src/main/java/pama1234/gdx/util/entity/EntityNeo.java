package pama1234.gdx.util.entity;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityNeoListener;

public class EntityNeo<T extends UtilScreen>extends Entity<T> implements EntityNeoListener{
  public EntityNeo(T p) {
    super(p);
  }
}
