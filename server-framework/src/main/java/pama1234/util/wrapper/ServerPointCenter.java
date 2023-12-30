package pama1234.util.wrapper;

import pama1234.util.UtilServer;
import pama1234.util.entity.ServerPointEntity;

import java.util.ListIterator;

public class ServerPointCenter<P extends UtilServer,T extends ServerPointEntity<?,?>>extends ServerEntityCenter<P,T>{
  public float minDist,minDisplayDist;
  public T select;
  public ServerPointCenter(P p) {
    this(p,4);
  }
  public ServerPointCenter(P p,float u) {
    super(p);
    this.minDist=u;
    this.minDisplayDist=u;
  }
  @Override
  public void update() {
    super.update();
  }
  public void find(float x,float y) {
    float tmd=minDist;
    select=null;
    ListIterator<T> it=list.listIterator(list.size());
    while(it.hasPrevious()) {
      T i=it.previous();
      float td=i.point.pos.dist(x,y);
      if(td<tmd) {
        tmd=td;
        select=i;
      }
    }
  }
}
