package pama1234.processing.lang.visual.util.unit;

import pama1234.processing.lang.visual.util.UITools;
import pama1234.processing.util.app.UtilApp;

public class Function extends Unit{
  public Function(UtilApp p,int x,int y,String name) {
    super(p,x,y,"F",name);
  }
  @Override
  public void draw() {
    g.background(inBox?0xff20B9D6:0xff0099b6);
    UITools.rectBorder(g);
    drawName();
    UITools.rectBorder(g,0,3,w,textSize+2);
  }
  @Override
  public void init() {}
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
  public void frameResized(int w,int h) {
    super.frameResized(w,h);
  }
  @Override
  public void frameMoved(int x,int y) {}
}
