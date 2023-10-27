package pama1234.processing.al.duel.util.control;

import pama1234.processing.util.UITools;
import pama1234.processing.util.element.Part;
import processing.core.PGraphics;

public abstract class KeyBar extends Part{
  public Keyboard parent;
  public KeyData data;
  public float x,y,w,h;
  public KeyBarStyle s;
  public KeyBar(Keyboard parent,KeyData data,PGraphics g,KeyBarStyle style,int x,int y,int w,int h) {
    super(g);
    this.parent=parent;
    this.data=data;
    this.s=style;
    this.x=x;
    this.y=y;
    this.w=w;
    this.h=h;
  }
  @Override
  public void display() {
    drawKey(data.data,data.name,
      numToPos(x),
      numToPos(y),
      numToSize(w),numToSize(h),(int)Math.ceil(parent.s.barWeight));
  }
  public int numToPos(float in) {
    return (int)Math.ceil(parent.s.barWeight+in*(parent.s.keySize+parent.s.borderSize*2)+parent.s.borderSize);
  }
  public int numToSize(float in) {
    return (int)Math.ceil(in*parent.s.keySize);
  }
  public void drawKey(boolean b,char in,int x,int y,int w,int h,int l) {
    if(!b) y-=l;
    g.fill(b?0xffDDA0DD:0xff8B008B);
    g.rect(x,y,w,h);
    if(!b) {
      g.fill(0xffDC143C);
      g.rect(x,y+h,w,l);
    }
    g.fill(b?255:191);
    g.text(in,x+1,y-parent.s.keySize/4);
    UITools.border(g,x,y,w,h,0x80DDAE4E,0x803289A8);
  }
  @Override
  public void update() {
    tick();
    if(data.refresh()) {
      if(data.data) keyPressed();
      else keyReleased();
    }
  }
  public abstract void tick();
  public abstract void keyPressed();
  public abstract void keyReleased();
}