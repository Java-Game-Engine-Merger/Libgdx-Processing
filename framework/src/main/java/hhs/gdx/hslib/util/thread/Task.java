package hhs.gdx.hslib.util.thread;

public interface Task{
  public bool running=new bool(true);
  public abstract void run(float delta);
}
