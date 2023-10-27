package pama1234.gdx.util.listener;

public interface StateChanger<T>{
  /**
   * @param in 新的
   * @return out 旧的
   */
  public T state(T in);
  /**
   * 返回到上一个状态
   * 
   * @return
   */
  public default T stateBack() {
    return null;
  }
}
