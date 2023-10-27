package pama1234.processing.al.sandbox.util.organ.copy;

import pama1234.processing.al.sandbox.util.Player;
import pama1234.processing.util.app.UtilApp;

public class Motion extends Organ{
  public float x,y;
  public float dir,mag;
  public Motion(UtilApp p,int x,int y,Player parent) {
    super(p,x,y,parent,2,2);
  }
  @Override
  public void playerToWorldUpdate() {}
  @Override
  public void worldToPlayerUpdate() {}
}
