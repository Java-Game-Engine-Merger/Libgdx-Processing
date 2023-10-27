package pama1234.processing.util.element;

import pama1234.math.physics.PathPoint;
import pama1234.processing.util.app.UtilApp;
import processing.core.PConstants;
import processing.core.PGraphics;

public abstract class BlankComponent extends PointEntity<PathPoint>{
  public PGraphics g;
  //---
  public int textSize=32;
  public int textAlignX=PConstants.LEFT,textAlignY=PConstants.TOP;
  public BlankComponent(UtilApp p,int x,int y) {
    this(p,x,y,1,1);
  }
  public BlankComponent(UtilApp p,int x,int y,int w,int h) {
    super(p,new PathPoint(0,0,x,y));
    g=p.createGraphics(w,h);
    //    System.out.println("BlankComponent.BlankComponent()");
    //    System.out.println(g);
    initGraphics();
    //    System.out.println(g);
  }
  @Override
  public void display() {
    p.image(g,point.pos.x,point.pos.y);
  }
  @Override
  public void update() {
    super.update();
  }
  public void refresh() {
    g.beginDraw();
    background();
    draw();
    g.endDraw();
  }
  public abstract void draw();
  public void background() {
    g.background(127);
  }
  public void resize(int w,int h) {
    g=p.createGraphics(w,h);
    initGraphics();
    refresh();
  }
  public void initGraphics() {
    g.noSmooth();
    g.beginDraw();
    g.textFont(p.font);
    g.textAlign(textAlignX,textAlignY);
    g.textSize(textSize);
    g.textLeading(textSize);
    g.noStroke();
    g.endDraw();
  }
  @Override
  public void frameResized(int w,int h) {
    initGraphics();
  }
}
