package pama1234.gdx.util.app;

import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntityBase;

public abstract class ScreenCoreState2D<C extends StateCenter<?,?>,E extends StateEntityBase<?,?,E>>extends ScreenCore2D implements StateChanger<E>{
  public C stateCenter;
  public E state;
  @Override
  public E state(E in) {
    E out=state;
    state=in;

    boolean outIsNull=out==null;
    boolean inIsNull=in==null;

    if(!outIsNull) {
      // if(!inIsNull&&in.overlayType) out.underOverlay=true;
      if(inIsNull||!in.overlayType) {
        removeStateEntity(in,out);
      }
    }

    this.auto.stateChangeEvent(out,in);

    if(!inIsNull) {
      // if(!outIsNull&&out.overlayType) in.underOverlay=false;
      if(outIsNull||!out.overlayType) {
        addStateEntity(in,out);
      }
    }

    return out;
  }
  public void addStateEntity(E in,E out) {
    in.resume();
    in.from(out);
    centerNeo.add.add(in);
    if(in.container!=null) centerAddContainer(in.container);
    // centerCam.add.add(in.displayCam);

    // TODO in alpha
    // refreshAllCenter();
  }
  public void removeStateEntity(E in,E out) {
    centerNeo.remove.add(out);
    if(out.container!=null) centerRemoveContainer(out.container);
    // centerCam.remove.add(out.displayCam);
    out.to(in);
    out.pause();

    // TODO in alpha
    // refreshAllCenter();
  }

  public E stateNull() {
    E out=state;
    state=null;
    if(out!=null) {
      centerNeo.list.remove(out);
      // centerCam.list.remove(out.displayCam);
      out.to(null);
      out.pause();
    }
    return out;
  }

  @Override
  public E stateBack() {
    // TODO
    return null;
  }

  //  @Override
  //  public void dispose() {
  //    stateNull();
  //    super.dispose();
  //  }
}
