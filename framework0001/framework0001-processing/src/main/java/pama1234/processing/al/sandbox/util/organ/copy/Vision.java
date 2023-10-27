package pama1234.processing.al.sandbox.util.organ.copy;

import pama1234.processing.al.autometa.particle.util.Cell;
import pama1234.processing.al.sandbox.util.Player;
import pama1234.processing.util.app.UtilApp;

public class Vision extends Organ{
  public float dir;
  public int[] colors;
  public Vision(UtilApp p,int x,int y,Player parent) {
    super(p,x,y,parent,(int)Cell.size*12*3,1);
    colors=new int[(int)Cell.size*12];
  }
  @Override
  public void playerToWorldUpdate() {}
  @Override
  public void worldToPlayerUpdate() {}
}
