package pama1234.util.entity;

import pama1234.math.physics.Point;
import pama1234.util.UtilServer;

public abstract class ServerPointEntity<P extends UtilServer,T extends Point>extends ServerEntity<P>{
  public final T point;
  public ServerPointEntity(P p,T in) {
    super(p);
    point=in;
  }
  @Override
  public void update() {
    point.update();
    //    update2();
  }
  public float x() {
    return point.x();
  }
  public float y() {
    return point.y();
  }
  public int xInt() {
    return (int)Math.floor(point.pos.x);
  }
  public int yInt() {
    return (int)Math.floor(point.pos.y);
  }
  // public float mouseX() {
  //   return p.mouse.x-x();
  // }
  // public float mouseY() {
  //   return p.mouse.y-y();
  // }
  //  abstract public void update2();
  public String getName() {
    return getClass().getSimpleName();
  }
}
