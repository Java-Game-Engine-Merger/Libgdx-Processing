package pama1234.processing.util.element;

import pama1234.math.physics.Point;
import pama1234.processing.util.Entity;
import pama1234.processing.util.app.UtilApp;

public abstract class PointEntity<T extends Point>extends Entity{
  public final T point;
  public PointEntity(UtilApp p,T in) {
    super(p);
    point=in;
  }
  @Override
  public void update() {
    point.update();
    //    update2();
  }
  public int getXInt() {
    return (int)Math.floor(point.pos.x);
  }
  public int getYInt() {
    return (int)Math.floor(point.pos.y);
  }
  //  abstract public void update2();
}
