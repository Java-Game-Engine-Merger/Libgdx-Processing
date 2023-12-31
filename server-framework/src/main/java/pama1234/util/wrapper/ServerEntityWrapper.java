package pama1234.util.wrapper;

import pama1234.util.UtilServer;
import pama1234.util.entity.ServerEntity;
import pama1234.util.listener.ServerEntityListener;

public class ServerEntityWrapper<P extends UtilServer,T extends ServerEntityListener>extends ServerEntity<P>{
  public final T content;
  public ServerEntityWrapper(P p,T son) {
    super(p);
    this.content=son;
  }
  @Override
  public void init() {
    content.init();
  }
  @Override
  public void update() {
    content.update();
  }
  @Override
  public void display() {
    content.display();
  }
  @Override
  public void pause() {
    content.pause();
  }
  @Override
  public void resume() {
    content.resume();
  }
  @Override
  public void dispose() {
    content.dispose();
  }
}
