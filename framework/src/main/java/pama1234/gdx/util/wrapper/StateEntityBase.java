package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.StateEntityListener;

public abstract class StateEntityBase<P extends UtilScreen,E extends StateEntityListener<?>,T extends StateEntityBase<P,E,?>>extends StateEntity<P,E> implements StateEntityListener<T>{
  public int id;
  public ScreenContentContainer container;
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
}
