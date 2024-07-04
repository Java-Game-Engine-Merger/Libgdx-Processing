package pama1234.util.gdx.lwjgl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import pama1234.gdx.Pama;

public class Lwjgl3ApplicationPama extends Lwjgl3Application{
  public Lwjgl3ApplicationPama(ApplicationListener listener) {
    super(listener);
    init();
  }

  public Lwjgl3ApplicationPama(ApplicationListener listener,Lwjgl3ApplicationConfiguration config) {
    super(listener,config);
    init();
  }

  private void init() {
    Pama.img=new ImageLoadUtilLwjgl();
  }
}
