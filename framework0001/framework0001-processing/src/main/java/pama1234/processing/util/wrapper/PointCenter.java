package pama1234.processing.util.wrapper;

import pama1234.processing.util.UITools;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.element.PointEntity;
import processing.core.PConstants;

public class PointCenter<T extends PointEntity<?>>extends EntityCenter<T>{
  public float minDist,minDisplayDist;
  private T select;
  public PointCenter(UtilApp p) {
    this(p,4);
  }
  public PointCenter(UtilApp p,float u) {
    super(p);
    this.minDist=u;
    this.minDisplayDist=u*2;
  }
  @Override
  public void update() {
    super.update();
    if(p.mousePressed&&p.mouseButton==PConstants.LEFT&&select!=null) select.point.set(p.cam.mouseX,p.cam.mouseY);
  }
  @Override
  public void mousePressed(int button) {
    if(p.mouseButton==PConstants.LEFT) {
      float tmd=minDist;
      select=null;
      for(T i:list) {
        float td=i.point.pos.dist(p.cam.mouseX,p.cam.mouseY);
        if(td<tmd) {
          tmd=td;
          select=i;
        }
      }
      //      if(select!=null) select.point.set(p.cam.mouseX,p.cam.mouseY);
    }
    if(select==null) super.mousePressed(button);
  }
  @Override
  public void display() {
    super.display();
    //    UITools.cross(p.g,p.cam.mouseX,p.cam.mouseY,minDist/2,minDist/2);
    //  System.out.println(i.point.pos.dist(p.cam.mouseX,p.cam.mouseY));
    for(T i:list) if(i.point.pos.dist(p.cam.mouseX,p.cam.mouseY)<minDisplayDist) {
      p.stroke(0xc0ffffff);
      p.ellipse(i.point.pos.x,i.point.pos.y,minDist*2,minDist*2);
      UITools.cross(p.g,i.point.pos.x,i.point.pos.y,minDist*2,minDist*2);
      String ts=i.point.pos.toString();
      p.text(ts,i.point.pos.x-p.textWidth(ts)/2,i.point.pos.y-p.g.textSize);
    }
  }
}
