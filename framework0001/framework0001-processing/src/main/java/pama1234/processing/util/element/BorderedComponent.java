package pama1234.processing.util.element;

import pama1234.processing.util.UITools;
import pama1234.processing.util.app.UtilApp;

public abstract class BorderedComponent extends BlankComponent{
  public int w,h;
  public int borderSize;
  public BorderedComponent(UtilApp p,int x,int y) {
    this(p,x,y,1,1,1);
  }
  public BorderedComponent(UtilApp p,int x,int y,int w,int h) {
    this(p,x,y,w,h,1);
  }
  public BorderedComponent(UtilApp p,int x,int y,int w,int h,int borderSize) {
    super(p,x,y,w+borderSize*2,h+borderSize*2);
    //    g=p.createGraphics(w+borderSize*2,h+borderSize*2);
    //    initGraphics();
    this.borderSize=borderSize;
    this.w=w;
    this.h=h;
  }
  @Override
  public void refresh() {
    g.beginDraw();
    background();
    translate();
    draw();
    g.endDraw();
  }
  @Override
  public void background() {
    super.background();
    UITools.rectBorder(g);
  }
  @Override
  public void resize(int w,int h) {
    this.w=w;
    this.h=h;
    super.resize(w+borderSize*2,h+borderSize*2);
  }
  public void translate() {
    g.translate(borderSize,borderSize);
  }
  //  public static void main(String[] args) {
  //    new UtilApp() {
  //      @Override
  //      public void update() {}
  //      @Override
  //      public void init() {
  //        BorderedComponent comp=new BorderedComponent(this,0,0,32,32) {
  //          @Override
  //          public void mousePressed(int button) {}
  //          @Override
  //          public void mouseReleased(int button) {}
  //          @Override
  //          public void mouseClicked(int button) {}
  //          @Override
  //          public void mouseMoved() {}
  //          @Override
  //          public void mouseDragged() {}
  //          @Override
  //          public void mouseWheel(int c) {}
  //          @Override
  //          public void keyPressed(char key,int keyCode) {}
  //          @Override
  //          public void keyReleased(char key,int keyCode) {}
  //          @Override
  //          public void keyTyped(char key) {}
  //          @Override
  //          public void frameMoved(int x,int y) {}
  //          @Override
  //          public void init() {}
  //          @Override
  //          public void pause() {}
  //          @Override
  //          public void resume() {}
  //          @Override
  //          public void dispose() {}
  //          @Override
  //          public void draw() {}
  //        };
  //        comp.refresh();
  //        center.add.add(comp);
  //      }
  //      @Override
  //      public void display() {}
  //    }.run();
  //  }
}
