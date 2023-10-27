package pama1234.processing.art.star.util;

import com.hamoid.VideoExport;

import pama1234.math.Tools;
import pama1234.math.physics.PathPoint;
import pama1234.processing.al.corewars.util.MemoryArray;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.element.PointEntity;
import pama1234.processing.util.wrapper.Center;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class CellCenter extends PointEntity<PathPoint>{
  public static final float r_const=3;
  public static final int boxR=640;
  public static final int borderSize=12;
  private static final int fadeStep=16;
  public int w;
  public int h;
  public PGraphics[] layer=new PGraphics[2];
  public Center<Cell> c=new Center<>() {
    public void refresh() {
      for(Cell i:add) {
        if(i instanceof Attractor a) core.alist.add(a);
        else if(i instanceof Star a) core.slist.add(a);
      }
      for(Cell i:remove) {
        if(i instanceof Attractor a) core.alist.remove(a);
        else if(i instanceof Star a) core.slist.remove(a);
      }
      super.refresh();
    };
  };
  public Core core=new Core(this);
  //  public static final boolean videoExport=true;
  public static final boolean videoExport=false;
  VideoExport temp;
  PGraphics tg;
  public boolean pause,focus;
  public Cell select;
  private StringBuilder sb=new StringBuilder();
  public CellCenter(UtilApp p) {
    this(p,-boxR,-boxR,boxR*2,boxR*2);
  }
  public CellCenter(final UtilApp p,int x,int y,int w,int h) {
    super(p,new PathPoint(0,0,x,y));
    this.w=w;
    this.h=h;
    for(int i=0;i<layer.length;i++) {
      PGraphics g=p.createGraphics(w+borderSize*2,h+borderSize*2);
      g.beginDraw();
      g.noStroke();
      g.endDraw();
      layer[i]=g;
    }
    if(videoExport) {
      tg=p.createGraphics(layer[0].width,layer[0].height);
      temp=new VideoExport(p);
      temp.setGraphics(tg);
      temp.startMovie();
    }
  }
  @Override
  public void update() {
    super.update();
    if(select!=null&&p.mousePressed&&p.mouseButton==PConstants.LEFT) select.point.pos.add(p.cam.deltaX,p.cam.deltaY);
    if(pause) {
      layer[0].beginDraw();
      box();
      layer[0].endDraw();
      return;
    }
    for(Cell i:c.list) {
      i.point.vel.toNumber();
      i.point.pos.toNumber();
    }
    c.refresh();
    for(Cell i:c.list) {
      for(Cell j:c.list) {
        if(i==j) continue;
        float dx=j.point.pos.x-i.point.pos.x,
          dy=j.point.pos.y-i.point.pos.y;
        final float r=PApplet.sqrt(dx*dx+dy*dy);
        dx/=r;
        dy/=r;
        final float f;
        if(r<i.dist+j.dist) {
          i.collide(j);
          f=r(r);
        }else {
          continue;
        }
        i.point.vel.add(dx*f,dy*f);
      }
    }
    core.update();
    for(Cell i:c.list) i.update();
    for(Cell i:c.list) {
      if(i.life<=0) {
        c.remove.add(i);
        continue;
      }
      //      i.point.setInBox(0,0,w,h);
    }
    if(p.frameCount%4==0) fade();
    for(PGraphics g:layer) g.beginDraw();
    //    layer.beginDraw();
    //    for(Cell i:c.list) i.display();
    layer[1].clear();
    core.display();
    box();
    for(PGraphics g:layer) g.endDraw();
  }
  public void fade() {
    layer[0].loadPixels();
    for(int i=0;i<layer[0].pixels.length;i++) {
      int a=(layer[0].pixels[i]&0xff000000)>>>24;
      if(a==0) continue;
      if(a<128) a--;
      else a-=fadeStep;
      if(a<0) a=0;
      a<<=24;
      layer[0].pixels[i]=a|(layer[0].pixels[i]&0xffffff);
    }
    layer[0].updatePixels();
  }
  public void box() {
    layer[0].noFill();
    layer[0].stroke(pause?0xff0099B6:0xffffffff);
    layer[0].rect(0,0,layer[0].width-1,layer[0].height-1);
    layer[0].stroke(0);
    layer[0].rect(1,1,layer[0].width-3,layer[0].height-3);
    layer[0].noStroke();
  }
  public float f(final float r,final float g) {
    return g/r;
  }
  public float r(final float r) {
    return -r_const/r;
  }
  @Override
  public void display() {
    if(videoExport) {
      tg.beginDraw();
      tg.background(0);
      for(PGraphics g:layer) tg.image(g,0,0);
      tg.endDraw();
      temp.saveFrame();
      p.image(tg,getXInt()-borderSize,getYInt()-borderSize);
    }else for(PGraphics g:layer) p.image(g,getXInt()-borderSize,getYInt()-borderSize);
    //    PApplet.println(point.pos.x-layer_cell_size,point.pos.y-layer_cell_size);
    if(select!=null) {
      //      System.out.println(select);
      p.stroke(0xc0ffffff);
      p.ellipse(
        select.point.pos.x+0.5f+getXInt(),
        select.point.pos.y+0.5f+getYInt(),
        select.size+4,select.size+4);
      sb.append("\nposX =");
      sb.append(MemoryArray.floatFormatter.format(select.point.pos.x));
      sb.append("\nposY =");
      sb.append(MemoryArray.floatFormatter.format(select.point.pos.y));
      sb.append("\nvelX =");
      sb.append(MemoryArray.floatFormatter.format(select.point.vel.x));
      sb.append("\nvelY =");
      sb.append(MemoryArray.floatFormatter.format(select.point.vel.y));
      p.text(
        sb.toString(),
        getXInt()-borderSize,
        getYInt()-borderSize);
      sb.setLength(0);
    }
  }
  @Override
  public void dispose() {
    if(videoExport) temp.endMovie();
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(focus) {
      if(key==' ') pause=!pause;
      else if(key=='r') layer[0].clear();
    }
  }
  @Override
  public void mousePressed(int button) {
    if(button==PConstants.LEFT) {
      if(focus=Tools.inBox(p.cam.mouseX,p.cam.mouseY,getXInt()-borderSize,getYInt()-borderSize,w+borderSize*2,h+borderSize*2)) {
        float mouseX=p.cam.mouseX-getXInt(),
          mouseY=p.cam.mouseY-getYInt();
        float minDist=Math.max(w,h);
        select=null;
        for(Cell i:c.list) {
          float dist=PApplet.dist(i.point.pos.x,i.point.pos.y,mouseX,mouseY);
          //          System.out.println(dist);
          if(dist<minDist&&dist<i.size+4) {
            minDist=dist;
            select=i;
          }
        }
      }
    }
  }
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
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
  @Override
  public void init() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
}
