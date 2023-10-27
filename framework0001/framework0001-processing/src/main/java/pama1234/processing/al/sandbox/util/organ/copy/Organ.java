package pama1234.processing.al.sandbox.util.organ.copy;

import pama1234.processing.al.sandbox.util.Player;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.element.BorderedComponent;

public abstract class Organ extends BorderedComponent{
  public Player parent;
  public float[] perception,action;
  public Organ(UtilApp p,int x,int y,Player parent,int inputSize,int outputSize) {
    super(p,x,y);
    this.parent=parent;
    perception=new float[inputSize];
    action=new float[outputSize];
  }
  //  public abstract void update();
  public abstract void playerToWorldUpdate();
  public abstract void worldToPlayerUpdate();
  @Override
  public void init() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
  @Override
  public void draw() {}
  @Override
  public void background() {}
  @Override
  public void update() {
    super.update();
    draw();
  }
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
  public void frameMoved(int x,int y) {}
}
