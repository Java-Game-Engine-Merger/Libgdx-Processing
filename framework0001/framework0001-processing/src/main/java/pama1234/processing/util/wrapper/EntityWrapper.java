package pama1234.processing.util.wrapper;

import pama1234.processing.util.Entity;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.listener.EntityListener;

public class EntityWrapper<T extends EntityListener>extends Entity{
  public final T content;
  public EntityWrapper(UtilApp p,T son) {
    super(p);
    this.content=son;
  }
  @Override
  public void init() {
    content.init();
  }
  @Override
  public void update() {
    content.update();
  }
  @Override
  public void display() {
    content.display();
  }
  @Override
  public void pause() {
    content.pause();
  }
  @Override
  public void resume() {
    content.resume();
  }
  @Override
  public void dispose() {
    content.dispose();
  }
  @Override
  public void mousePressed(final int button) {
    content.mousePressed(button);
  }
  @Override
  public void mouseReleased(final int button) {
    content.mouseReleased(button);
  }
  @Override
  public void mouseClicked(final int button) {
    content.mouseClicked(button);
  }
  @Override
  public void mouseMoved() {
    content.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    content.mouseDragged();
  }
  @Override
  public void mouseWheel(final int c) {
    content.mouseWheel(c);
  }
  @Override
  public void keyPressed(final char key,final int keyCode) {
    content.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(final char key,final int keyCode) {
    content.keyReleased(key,keyCode);
  }
  @Override
  public void keyTyped(final char key) {
    content.keyTyped(key);
  }
  @Override
  public void frameResized(final int w,final int h) {
    content.frameResized(w,h);
  }
  @Override
  public void frameMoved(final int x,final int y) {
    content.frameMoved(x,y);
  }
}
