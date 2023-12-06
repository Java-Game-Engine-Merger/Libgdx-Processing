package pama1234.gdx.util.wrapper;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

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

  public Stage screenStage,camStage;

  public ScreenContentContainer initMember(UtilScreen p) {
    center=new EntityCenter<UtilScreen,EntityListener>(p);
    centerCam=new EntityCenter<UtilScreen,EntityListener>(p);
    centerScreen=new EntityCenter<UtilScreen,EntityListener>(p);
    centerNeo=new EntityNeoCenter<UtilScreen,EntityNeoListener>(p);
    serverCenter=new ServerEntityCenter<ServerEntityListener>();
    return this;
  }

  public void addScreenTextFields(TextField... in) {
    for(TextField e:in) screenStage.addActor(e);
  }
  public void addCamTextFields(TextField... in) {
    for(TextField e:in) camStage.addActor(e);
  }
  public void removeScreenTextFields(TextField... in) {
    Group root=screenStage.getRoot();
    for(TextField e:in) root.removeActor(e);
  }
  public void removeCamTextFields(TextField... in) {
    Group root=camStage.getRoot();
    for(TextField e:in) root.removeActor(e);
  }
}
