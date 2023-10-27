package pama1234.processing.art.star.util;

import java.util.ArrayList;

import pama1234.math.vec.Vec2f;
import pama1234.processing.util.listener.EssentialListener;
import processing.core.PApplet;
import processing.core.PConstants;

public class Core implements EssentialListener{
  public ArrayList<Star> slist=new ArrayList<>();
  public ArrayList<Attractor> alist=new ArrayList<>();
  //---
  public CellCenter parent;
  public Core(CellCenter parent) {
    this.parent=parent;
  }
  @Override
  public void update() {
    Vec2f vel=new Vec2f();
    for(Star i:slist) {
      float direction=0;
      for(Attractor j:alist) {
        float x=i.point.pos.x-j.point.pos.x,
          y=i.point.pos.y-j.point.pos.y;
        direction+=PApplet.atan2(y,x)+PConstants.PI;
        //          /PApplet.mag(x,y)*16;
      }
      vel.set(1,0);
      vel.rotate(direction);
      i.point.vel.add(vel);
      i.point.vel.add(
        (parent.w/2f-i.point.pos.x)/512,
        (parent.h/2f-i.point.pos.y)/512);
    }
    //    for(Attractor j:alist) j.point.vel.set(0,0);
  }
  @Override
  public void display() {
    parent.layer[0].fill(0);
    int c=0;
    for(Star i:slist) {
      //      parent.layer[0].stroke(c%256,255,255);
      parent.layer[0].fill(c%256,255,255);
      i.display(parent.layer[0]);
      c++;
    }
    //    parent.layer.fill(255);
    //    parent.layer.noStroke();
    for(Attractor i:alist) i.display(parent.layer[1]);
  }
}
