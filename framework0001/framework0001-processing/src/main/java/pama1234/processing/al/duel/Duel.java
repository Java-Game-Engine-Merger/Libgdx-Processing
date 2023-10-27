package pama1234.processing.al.duel;

import static pama1234.processing.al.duel.util.DuelCenter.borderSize;
import static pama1234.processing.al.duel.util.DuelCenter.boxR;

import pama1234.math.physics.MassPoint;
import pama1234.math.physics.PathPoint;
import pama1234.processing.al.duel.app.MainApp;
import pama1234.processing.al.duel.util.DuelCenter;
import pama1234.processing.al.duel.util.Player;
import pama1234.processing.al.duel.util.control.hist.Control;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.element.PointEntity;
import pama1234.processing.util.wrapper.PointCenter;

public class Duel extends PointCenter<PointEntity<PathPoint>>{
  public DuelCenter duel;
  public Player a,b;
  public Control<MassPoint> ctrl;
  public Duel(UtilApp p) {
    super(p);
    duel=new DuelCenter(p);
    {
      a=new Player(duel,duel.w/4,duel.h/2);
      b=new Player(duel,duel.w/4*3,duel.h/2);
      duel.center.add.add(a);
      duel.center.add.add(b);
      duel.center.refresh();
    }
    add.add(duel);
    ctrl=new Control<MassPoint>(p,-boxR-borderSize*2+1,boxR+borderSize*2+1,1,a.point);
    ctrl.player=a;
    ctrl.comp=b;
    add.add(ctrl);
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new MainApp().run();
  }
}