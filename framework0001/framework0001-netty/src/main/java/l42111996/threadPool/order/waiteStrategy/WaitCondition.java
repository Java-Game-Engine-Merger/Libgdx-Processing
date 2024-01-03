package l42111996.threadPool.order.waiteStrategy;

/**
 * 等待条件
 * 
 * @param <T>
 */
public interface WaitCondition<T>{

  /**
   * 附件
   * 
   * @return
   */
  T getAttach();

}
