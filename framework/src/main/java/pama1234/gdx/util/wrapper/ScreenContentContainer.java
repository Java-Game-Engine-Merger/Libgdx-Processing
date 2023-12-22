package pama1234.gdx.util.wrapper;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import pama1234.gdx.game.ui.element.TextField;
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

  // public Stage screenStage,camStage;
  public ArrayList<Actor> screenStage,camStage;

  public ScreenContentContainer initMember(UtilScreen p) {
    center=new EntityCenter<UtilScreen,EntityListener>(p);
    centerCam=new EntityCenter<UtilScreen,EntityListener>(p);
    centerScreen=new EntityCenter<UtilScreen,EntityListener>(p);
    centerNeo=new EntityNeoCenter<UtilScreen,EntityNeoListener>(p);
    serverCenter=new ServerEntityCenter<ServerEntityListener>();

    // screenStage=new Stage(p.screenViewport,p.imageBatch);
    // camStage=new Stage(p.camViewport,p.imageBatch);

    screenStage=new ArrayList<>();
    camStage=new ArrayList<>();

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
  public void addScreenTextFields(TextField... in) {
    // for(TextField e:in) screenStage.addActor(e);
    for(TextField e:in) screenStage.add(e);
  }
  public void addCamTextFields(TextField... in) {
    // for(TextField e:in) camStage.addActor(e);
    for(TextField e:in) camStage.add(e);
  }
  public void removeScreenTextFields(TextField... in) {
    // Group root=screenStage.getRoot();
    // for(TextField e:in) root.removeActor(e);
    for(TextField e:in) screenStage.remove(e);

  }
  public void removeCamTextFields(TextField... in) {
    // Group root=camStage.getRoot();
    // for(TextField e:in) root.removeActor(e);
    for(TextField e:in) camStage.remove(e);
  }

  public void addScreenTextFields(Array<TextField> in) {
    // for(TextField e:in) screenStage.addActor(e);
    for(TextField e:in) screenStage.add(e);
  }
  public void addCamTextFields(Array<TextField> in) {
    // for(TextField e:in) camStage.addActor(e);
    for(TextField e:in) camStage.add(e);
  }
  public void removeScreenTextFields(Array<TextField> in) {
    // Group root=screenStage.getRoot();
    // for(TextField e:in) root.removeActor(e);
    for(TextField e:in) screenStage.remove(e);
  }
  public void removeCamTextFields(Array<TextField> in) {
    // Group root=camStage.getRoot();
    // for(TextField e:in) root.removeActor(e);
    for(TextField e:in) camStage.remove(e);
  }
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
