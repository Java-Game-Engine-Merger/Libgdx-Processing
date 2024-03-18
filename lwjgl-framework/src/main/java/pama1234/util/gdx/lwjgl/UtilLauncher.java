package pama1234.util.gdx.lwjgl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowListener;

import pama1234.gdx.SystemSetting;
import pama1234.gdx.util.launcher.MainAppBase;

public class UtilLauncher{

  public static Lwjgl3ApplicationConfiguration getDefaultConfiguration(ApplicationListener appl) {
    return getDefaultConfiguration(appl,"Loading");
  }
  public static Lwjgl3ApplicationConfiguration getDefaultConfiguration(ApplicationListener appl,String title) {

    Gdx.files=new Lwjgl3Files();
    SystemSetting.load();

    Lwjgl3ApplicationConfiguration conf=new Lwjgl3ApplicationConfiguration();
    conf.setTitle(title);
    conf.useVsync(true);
    conf.setForegroundFPS(SystemSetting.data.targetFps);
    if(SystemSetting.data.fullScreen) conf.setFullscreenMode(
      Lwjgl3ApplicationConfiguration.getDisplayMode(Lwjgl3ApplicationConfiguration.getPrimaryMonitor()));
    conf.setWindowedMode(SystemSetting.data.width,SystemSetting.data.height);
    conf.setWindowIcon("icon/icon128.png","icon/icon64.png","icon/icon32.png","icon/icon16.png");

    if(appl instanceof MainAppBase app) {
      conf.setWindowListener(new Lwjgl3WindowListener() {
        @Override
        public void created(Lwjgl3Window window) {}
        @Override
        public void iconified(boolean isIconified) {}
        @Override
        public void maximized(boolean isMaximized) {}
        @Override
        public void focusLost() {
          app.focusLost();
        }
        @Override
        public void focusGained() {
          app.focusGained();
        }
        @Override
        public boolean closeRequested() {
          return true;
        }
        @Override
        public void filesDropped(String[] files) {}
        @Override
        public void refreshRequested() {}
      });
    }
    return conf;
  }
}