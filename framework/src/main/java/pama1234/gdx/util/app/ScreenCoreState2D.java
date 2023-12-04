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
    if(out!=null) {
      centerScreen.remove.add(out);
      centerCam.remove.add(out.displayCam);
      out.to(in);
      out.pause();

      // TODO in alpha
      // refreshAllCenter();
    }
    auto.stateChangeEvent(out,in);
    if(in!=null) {
      in.resume();
      in.from(out);
      centerScreen.add.add(in);
      centerCam.add.add(in.displayCam);

      // TODO in alpha
      // refreshAllCenter();
    }
    return out;
  }

  @Override
  public E stateBack() {
    // TODO
    return null;
  }
}
