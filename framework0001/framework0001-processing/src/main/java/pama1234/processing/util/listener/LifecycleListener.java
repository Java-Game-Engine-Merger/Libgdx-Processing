package pama1234.processing.util.listener;

public interface LifecycleListener extends EssentialListener{
  public void init();
  public void pause();
  public void resume();
  public void dispose();
}