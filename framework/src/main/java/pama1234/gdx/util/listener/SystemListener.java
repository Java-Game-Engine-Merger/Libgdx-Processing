package pama1234.gdx.util.listener;

public interface SystemListener{
  /** 在电脑端窗口切换到前台时被调用 */
  default void focusGained() {}
  /** 在电脑端窗口从前台切换掉时被调用 */
  default void focusLost() {}
}
