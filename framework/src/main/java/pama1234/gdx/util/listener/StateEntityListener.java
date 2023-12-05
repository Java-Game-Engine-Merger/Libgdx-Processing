package pama1234.gdx.util.listener;

import pama1234.gdx.util.wrapper.DisplayEntity.DisplayWithCam;

public interface StateEntityListener<T> extends EntityListener,DisplayWithCam{
  /**
   * 到达这个实例
   * 
   * @param in 从这里离开
   */
  public default void from(T in) {}
  /**
   * 离开这个实例
   * 
   * @param in 从这里到达
   */
  public default void to(T in) {}
}