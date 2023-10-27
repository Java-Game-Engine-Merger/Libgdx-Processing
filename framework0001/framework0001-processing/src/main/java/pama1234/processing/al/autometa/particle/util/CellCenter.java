package pama1234.processing.al.autometa.particle.util;

import java.nio.ByteBuffer;

import com.hamoid.VideoExport;

import pama1234.math.Tools;
import pama1234.math.physics.PathPoint;
import pama1234.nio.ByteData;
import pama1234.processing.al.corewars.util.MemoryArray;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.element.PointEntity;
import pama1234.processing.util.wrapper.Center;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class CellCenter extends PointEntity<PathPoint> implements ByteData{
  public static final float r_const=Cell.dist/4;
  public static final int boxR=80;
  public static final int borderSize=(int)Cell.size;
  private static final int fadeStep=16;
  public final MetaInfoCenter meta;
  public int w=boxR*2;
  public int h=boxR*2;
  public PGraphics layer;
  public Center<Cell> c=new Center<>();
  public static final boolean videoExport=false;
  VideoExport temp;
  PGraphics tg;
  public boolean pause,focus;
  public Cell select;
  private StringBuilder sb=new StringBuilder();
  @SuppressWarnings("unused")
  public CellCenter(final UtilApp p,final MetaInfoCenter parent) {
    super(p,new PathPoint(0,0,-boxR,-boxR));
    this.meta=parent;
    layer=p.createGraphics(w+borderSize*2+1,h+borderSize*2+1);
    if(Cell.size<2) layer.smooth();
    layer.beginDraw();
    layer.noStroke();
    layer.endDraw();
    if(videoExport) {
      tg=p.createGraphics(layer.width,layer.height);
      temp=new VideoExport(p);
      temp.setGraphics(tg);
      temp.startMovie();
    }
  }
  @Override
  public void update() {
    super.update();
    if(pause) {
      layer.beginDraw();
      box();
      layer.endDraw();
      return;
    }
    for(Cell i:c.list) {
      i.point.vel.toNumber();
      i.point.pos.toNumber();
      i.back.clear();
      i.score.toNumber();
    }
    c.refresh();
    for(Cell i:c.list) i.update();
    for(Cell i:c.list) {
      for(Cell j:c.list) {
        if(i==j) continue;
        final MetaInfo info=meta.core[i.id][j.id];
        float dx=j.point.pos.x-i.point.pos.x,
          dy=j.point.pos.y-i.point.pos.y;
        final float r=PApplet.sqrt(dx*dx+dy*dy);
        if(r>info.max) continue;
        else if(r<info.scoreR&&info.scoreG>0) j.back.add(i);
        dx/=r;
        dy/=r;
        final float f;
        if(r<Cell.dist) f=r(r);
        else if(info.g!=0&&r>info.min) f=f(r,info.g);
        else continue;
        i.point.vel.add(dx*f,dy*f);
      }
    }
    for(Cell i:c.list) {
      i.point.vel.toNumber();
      i.point.setInBox(0,0,w,h);
      i.point.pos.toNumber();
      i.score.toNumber();
      for(Cell t:i.back) {
        final float scoreG=meta.core[t.id][i.id].scoreG;
        if(scoreG!=0) i.score.vel+=t.score.pos*scoreG;
      }
    }
    fade();
    layer.beginDraw();
    for(Cell i:c.list) i.display();
    box();
    layer.endDraw();
  }
  public void fade() {
    layer.loadPixels();
    for(int i=0;i<layer.pixels.length;i++) {
      int a=(layer.pixels[i]&0xff000000)>>>24;
      if(a==0) continue;
      if(a<128) a--;
      else a-=fadeStep;
      if(a<0) a=0;
      a<<=24;
      layer.pixels[i]=a|(layer.pixels[i]&0xffffff);
    }
    layer.updatePixels();
  }
  public void box() {
    layer.noFill();
    layer.stroke(pause?0xff0099B6:0xffffffff);
    layer.rect(0,0,layer.width-1,layer.height-1);
    layer.stroke(0);
    layer.rect(1,1,layer.width-3,layer.height-3);//FIXME
    layer.noStroke();
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
      tg.image(layer,0,0);
      tg.endDraw();
      temp.saveFrame();
      p.image(tg,getXInt()-borderSize,getYInt()-borderSize);
    }else p.image(layer,getXInt()-borderSize,getYInt()-borderSize);
    //    PApplet.println(point.pos.x-layer_cell_size,point.pos.y-layer_cell_size);
    if(select!=null) {
      p.stroke(0xc0ffffff);
      p.ellipse(
        select.point.pos.x+0.5f+getXInt(),
        select.point.pos.y+0.5f+getYInt(),
        Cell.dist*2,Cell.dist*2);
      sb.append("type = ");
      sb.append(select.id);
      sb.append("\ntotal=");
      sb.append(MemoryArray.floatFormatter.format(select.totalScore));
      sb.append("\nscore=");
      sb.append(MemoryArray.floatFormatter.format(select.score.pos));
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
        getXInt(),
        getYInt()-p.g.textSize);
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
    }
  }
  @Override
  public void mousePressed(int button) {
    if(button==PConstants.LEFT) {
      if(focus=Tools.inBox(p.cam.mouseX,p.cam.mouseY,getXInt()-borderSize,getYInt()-borderSize,w+borderSize*2,h+borderSize*2)) {
        float mouseX=p.cam.mouseX-getXInt(),
          mouseY=p.cam.mouseY-getYInt();
        float minDist=Cell.dist;
        select=null;
        for(Cell i:c.list) {
          float dist=PApplet.dist(i.point.pos.x,i.point.pos.y,mouseX,mouseY);
          if(dist<minDist) {
            minDist=dist;
            select=i;
          }
        }
      }
    }
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    int ts=in.getInt(offset);
    offset+=ByteData.INT_SIZE;
    c.list.clear();
    while(c.list.size()<ts) {
      Cell i=new Cell(this,0,0,0);
      i.fromData(in,offset,offset+=Cell.buffer_size);
      c.list.add(i);
    }
    layer.clear();
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putInt(offset,c.list.size());
    offset+=ByteData.INT_SIZE;
    for(Cell i:c.list) {
      i.toData(in,offset);
      offset+=i.bufferSize();
    }
    return in;
  }
  @Override
  public int bufferSize() {
    return c.list.size()*Cell.buffer_size+ByteData.INT_SIZE;
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
