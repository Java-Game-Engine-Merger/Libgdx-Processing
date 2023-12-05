package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.listener.EntityNeoListener;
import pama1234.util.listener.ServerEntityListener;
import pama1234.util.wrapper.ServerEntityCenter;

public class ScreenContentContainer implements EntityNeoListener{
  public EntityCenter<UtilScreen,EntityListener> center;
  public EntityCenter<UtilScreen,EntityListener> centerCam;
  public EntityCenter<UtilScreen,EntityListener> centerScreen;
  public EntityNeoCenter<UtilScreen,EntityNeoListener> centerNeo;
  public ServerEntityCenter<ServerEntityListener> serverCenter;
}
