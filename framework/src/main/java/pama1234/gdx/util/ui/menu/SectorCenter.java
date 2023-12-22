package pama1234.gdx.util.ui.menu;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.wrapper.StateEntityBase;
import pama1234.math.physics.PathPoint;
import pama1234.util.wrapper.Center;

public class SectorCenter<P extends UtilScreen,E extends StateEntityBase<P,?,?>,T extends SettingSector<P,E>>extends Center<T>{
  public P p;
  public E ps;
  public T current;
  public PathPoint indexPoint;

  public SectorCenter(P p,E ps) {
    this.p=p;
    this.ps=ps;
    indexPoint=new PathPoint(0,0);
  }

  public void init() {
    for(var i:list) i.createButton(p,ps);
  }
  public void update() {
    indexPoint.update();
  }
  public void switchSetting(T in) {
    if(current==in) return;
    boolean flag=current==null;
    if(!flag) removeAll(current);
    // ps.clearUiComp();
    current=in;
    if(current!=null) {
      indexPoint.des.set(current.pb.rect.x1()+6,current.pb.rect.cy());
      if(flag) indexPoint.pos.set(indexPoint.des);
    }
    if(in==null) return;
    addAll(in);
  }
  public void addAll(T in) {
    // p.centerScreenAddAll(in.buttonsScreen);
    ps.container.centerScreenAddAll(in.buttonsScreen);

    // p.centerCamAddAll(in.buttonsCam);
    ps.container.centerCamAddAll(in.buttonsCam);

    // p.addCamTextFields(in.camTextFields);
    ps.container.addCamTextFields(in.camTextFields);
  }
  public void removeAll(T in) {
    // p.centerScreenRemoveAll(in.buttonsScreen);
    ps.container.centerScreenRemoveAll(in.buttonsScreen);

    // p.centerCamRemoveAll(in.buttonsCam);
    ps.container.centerCamRemoveAll(in.buttonsCam);

    // p.removeCamTextFields(in.camTextFields);
    ps.container.removeCamTextFields(in.camTextFields);
  }
}
