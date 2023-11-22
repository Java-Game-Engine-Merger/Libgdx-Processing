package hhs.gdx.hslib.actions;

import hhs.gdx.hslib.entities.Entity;

public interface Action<T extends Entity>{
  public abstract void start(T entity);
  public abstract boolean update(T entity,float delta);
}
