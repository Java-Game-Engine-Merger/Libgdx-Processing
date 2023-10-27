package pama1234.processing.lang.visual.util;

import pama1234.math.physics.PathPoint;
import pama1234.processing.util.Entity;
import pama1234.processing.util.app.UtilApp;
import processing.core.PConstants;
import processing.core.PGraphics;

public abstract class Component extends Entity{
  public PGraphics g;
  public PathPoint point;
  //---
  public int textSize=32,w,h;
  public int textAlignX=PConstants.LEFT,textAlignY=PConstants.TOP;
  public int borderSize=8;
  public Component(UtilApp p,int x,int y) {
    super(p);
    g=p.createGraphics(1,1);
    initGraphics();
    point=new PathPoint(0,0,x,y);
    w=1;
    h=1;
  }
  public Component(UtilApp p,int x,int y,int w,int h) {
    super(p);
    g=p.createGraphics(w+borderSize*2,h+borderSize*2);
    initGraphics();
    point=new PathPoint(0,0,x,y);
    this.w=w;
    this.h=h;
  }
  @Override
  public void display() {
    p.image(g,point.pos.x,point.pos.y);
  }
  @Override
  public void update() {
    point.update();
  }
  public void refresh() {
    g.beginDraw();
    translate();
    draw();
    g.endDraw();
  }
  public abstract void draw();
  public void resize(int w,int h) {
    g=p.createGraphics(w+borderSize*2,h+borderSize*2);
    this.w=w;
    this.h=h;
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
  public void translate() {
    g.translate(borderSize,borderSize);
  }
  @Override
  public void frameResized(int w,int h) {
    initGraphics();
  }
}
