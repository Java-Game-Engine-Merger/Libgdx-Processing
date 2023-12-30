package pama1234.util.wrapper;

import pama1234.util.UtilServer;
import pama1234.util.entity.ServerEntity;

public class ServerLayeredEntityCenter<P extends UtilServer>extends ServerEntity<P>{
  public ServerEntityCenter<P,?>[] list;
  public boolean reverseDisplay;
  public ServerLayeredEntityCenter(P p,int size) {
    super(p);
    list=new ServerEntityCenter[size];
  }
  @Override
  public void init() {
    for(ServerEntityCenter<P,?> i:list) i.init();
  }
  @Override
  public void update() {
    for(ServerEntityCenter<P,?> i:list) i.update();
  }
  @Override
  public void display() {
    if(reverseDisplay) for(int i=list.length-1;i>=0;i--) list[i].display();
    else for(ServerEntityCenter<P,?> i:list) i.display();
  }
  @Override
  public void pause() {
    for(ServerEntityCenter<P,?> i:list) i.pause();
  }
  @Override
  public void resume() {
    for(ServerEntityCenter<P,?> i:list) i.resume();
  }
  @Override
  public void dispose() {
    for(ServerEntityCenter<P,?> i:list) i.dispose();
  }
}
