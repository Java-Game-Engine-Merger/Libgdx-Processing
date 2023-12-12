package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.StateEntityListener;

public abstract class StateEntityBase<P extends UtilScreen,E extends StateEntityListener<?>,T extends StateEntityBase<P,E,?>>extends StateEntity<P,E> implements StateEntityListener<T>{
  public int id;
  public ScreenContentContainer container;
  public boolean overlayType;
  // public boolean underOverlay;
  public StateEntityBase(P p) {
    super(p);
    // initContainer(p);
    // displayCam=new DisplayEntity(this::displayCam);
  }
  public void initContainer() {
    container=new ScreenContentContainer().initMember(p);
  }
  public StateEntityBase(P p,int id) {
    this(p);
    this.id=id;
  }
  // @Override
  // public void from(StateEntity0004 in) {
  //   super.from(in);
  //   // TODO 评估是否需要将centerScreen.refresh放到ScreenCoreState2D和3D中
  //   p.refreshAllCenter();
  // }
  // @Override
  // public void to(StateEntity0004 in) {
  //   super.to(in);
  //   p.refreshAllCenter();
  // }
}
