package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.listener.EntityNeoListener;
import pama1234.util.Annotations.RedundantCache;
import pama1234.util.listener.ServerEntityListener;
import pama1234.util.wrapper.ServerEntityCenter;

@RedundantCache
public class ScreenContentContainer implements EntityNeoListener{
  public EntityCenter<UtilScreen,EntityListener> center;
  public EntityCenter<UtilScreen,EntityListener> centerCam;
  public EntityCenter<UtilScreen,EntityListener> centerScreen;
  public EntityNeoCenter<UtilScreen,EntityNeoListener> centerNeo;
  public ServerEntityCenter<ServerEntityListener> serverCenter;

  // public ActorCenter screenStage,camStage;

  public ScreenContentContainer initMember(UtilScreen p) {
    center=new EntityCenter<UtilScreen,EntityListener>(p);
    centerCam=new EntityCenter<UtilScreen,EntityListener>(p);
    centerScreen=new EntityCenter<UtilScreen,EntityListener>(p);
    centerNeo=new EntityNeoCenter<UtilScreen,EntityNeoListener>(p);
    // TODO
    serverCenter=new ServerEntityCenter<ServerEntityListener>(null);

    // screenStage=new Group();
    // camStage=new Group();

    // screenStage=new ActorCenter();
    // camStage=new ActorCenter();

    // Stage a=null;
    // Group b=a.getRoot();

    return this;
  }
  public void refreshAll() {
    center.refresh();
    centerCam.refresh();
    centerScreen.refresh();
    centerNeo.refresh();
    serverCenter.refresh();
  }

  //---------------------------------------------------------------------------
  // public void addScreenTextFields(TextField... in) {
  //   // for(TextField e:in) screenStage.addActor(e);
  //   for(TextField e:in) screenStage.list.add(e);
  // }
  // public void addCamTextFields(TextField... in) {
  //   // for(TextField e:in) camStage.addActor(e);
  //   for(TextField e:in) camStage.list.add(e);
  // }
  // public void removeScreenTextFields(TextField... in) {
  //   // for(TextField e:in) screenStage.removeActor(e);
  //   for(TextField e:in) screenStage.list.remove(e);

  // }
  // public void removeCamTextFields(TextField... in) {
  //   // for(TextField e:in) camStage.removeActor(e);
  //   for(TextField e:in) camStage.list.remove(e);
  // }

  // public void addScreenTextFields(Array<TextField> in) {
  //   // for(TextField e:in) screenStage.addActor(e);
  //   for(TextField e:in) screenStage.list.add(e);
  // }
  // public void addCamTextFields(Array<TextField> in) {
  //   // for(TextField e:in) camStage.addActor(e);
  //   for(TextField e:in) camStage.list.add(e);
  // }
  // public void removeScreenTextFields(Array<TextField> in) {
  //   // for(TextField e:in) screenStage.removeActor(e);
  //   for(TextField e:in) screenStage.list.remove(e);
  // }
  // public void removeCamTextFields(Array<TextField> in) {
  //   // for(TextField e:in) camStage.removeActor(e);
  //   for(TextField e:in) camStage.list.remove(e);
  // }
  //---------------------------------------------------------------------------
  public void centerCamAddAll(EntityListener... in) {
    for(EntityListener i:in) centerCam.add.add(i);
  }
  public void centerCamRemoveAll(EntityListener... in) {
    for(EntityListener i:in) centerCam.remove.add(i);
  }
  public void centerScreenAddAll(EntityListener... in) {
    for(EntityListener i:in) centerScreen.add.add(i);
  }
  public void centerScreenRemoveAll(EntityListener... in) {
    for(EntityListener i:in) centerScreen.remove.add(i);
  }
  public void centerNeoAddAll(EntityNeoListener... in) {
    for(EntityNeoListener i:in) centerNeo.add.add(i);
  }
  public void centerNeoRemoveAll(EntityNeoListener... in) {
    for(EntityNeoListener i:in) centerNeo.remove.add(i);
  }
  public <T extends EntityListener> void centerCamAddAll(Iterable<T> in) {
    for(EntityListener i:in) centerCam.add.add(i);
  }
  public <T extends EntityListener> void centerCamRemoveAll(Iterable<T> in) {
    for(EntityListener i:in) centerCam.remove.add(i);
  }
  public <T extends EntityListener> void centerScreenAddAll(Iterable<T> in) {
    for(EntityListener i:in) centerScreen.add.add(i);
  }
  public <T extends EntityListener> void centerScreenRemoveAll(Iterable<T> in) {
    for(EntityListener i:in) centerScreen.remove.add(i);
  }
  public <T extends EntityNeoListener> void centerNeoAddAll(Iterable<T> in) {
    for(EntityNeoListener i:in) centerNeo.add.add(i);
  }
  public <T extends EntityNeoListener> void centerNeoRemoveAll(Iterable<T> in) {
    for(EntityNeoListener i:in) centerNeo.remove.add(i);
  }
  //---------------------------------------------------------------------------
}
