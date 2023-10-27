package pama1234.processing.util.element;

import pama1234.processing.util.Life;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.listener.EntityListener;

public class EntityAdapter extends Life implements EntityListener{
  public EntityAdapter(UtilApp p) {
    super(p);
  }
  @Override
  public void init() {}
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
  @Override
  public void mousePressed(int button) {}
  @Override
  public void mouseReleased(int button) {}
  @Override
  public void mouseClicked(int button) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(int c) {}
  @Override
  public void keyPressed(char key,int keyCode) {}
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
}
