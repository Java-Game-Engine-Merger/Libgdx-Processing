package pama1234.processing.al.autometa.particle.util.copy;

import java.nio.ByteBuffer;

import com.hamoid.VideoExport;

import pama1234.math.Tools;
import pama1234.nio.ByteData;
import pama1234.processing.al.corewars.util.MemoryArray;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.wrapper.EntityCenter;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class CellCenter extends EntityCenter<Cell> implements ByteData{
  public static final float r_const=Cell.dist/4;
  public static final int boxR=80;
  public static final int layer_cell_size=(int)Cell.size;
  private static final int fadeStep=16;
  public final MetaInfoCenter meta;
  public static final int x1=-boxR,y1=-boxR,x2=boxR,y2=boxR;
  public static final int w=x2-x1,h=y2-y1;
  public PGraphics layer;
  public static final boolean videoExport=false;
  VideoExport temp;
  PGraphics tg;
  public boolean pause,focus;
  public Cell select;
  private StringBuilder sb=new StringBuilder();
  @SuppressWarnings("unused")
  public CellCenter(final UtilApp p,final MetaInfoCenter parent) {
    super(p);
    this.meta=parent;
    layer=p.createGraphics(w+layer_cell_size*2+1,h+layer_cell_size*2+1);
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
    if(pause) {
      layer.beginDraw();
      box();
      layer.endDraw();
      return;
    }
    for(Cell i:list) {
      i.point.vel.toNumber();
      i.point.pos.toNumber();
      i.back.clear();
      i.score.toNumber();
    }
    super.update();
    for(Cell i:list) {
      for(Cell j:list) {
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
    for(Cell i:list) {
      i.point.vel.toNumber();
      i.point.setInBox(x1,y1,x2,y2);
      i.point.pos.toNumber();
      i.score.toNumber();
      //      i.totalScore+=i.score.pos;
      for(Cell t:i.back) {
        final float scoreG=meta.core[t.id][i.id].scoreG;
        if(scoreG!=0) i.score.vel+=t.score.pos*scoreG;
      }
    }
    //---
    fade();
    layer.beginDraw();
    super.display();
    box();
    layer.endDraw();
    //    layer.loadPixels();
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
      p.image(tg,x1-layer_cell_size,y1-layer_cell_size);
    }else p.image(layer,x1-layer_cell_size,y1-layer_cell_size);
    if(select!=null) {
      p.ellipse(
        select.point.pos.x+0.5f,
        select.point.pos.y+0.5f,
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
        0,0);
      //        x-Cell.dist*2,y-Cell.dist*2);
      sb.setLength(0);
    }
  }
  @Override
  public void dispose() {
    if(videoExport) temp.endMovie();
    super.dispose();
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(focus) {
      if(key==' ') pause=!pause;
    }
    super.keyPressed(key,keyCode);
  }
  @Override
  public void mousePressed(int button) {
    if(button==PConstants.LEFT) if(focus=Tools.inBox(p.cam.mouseX,p.cam.mouseY,x1,y1,w,h)) {
      float minDist=Cell.dist;
      select=null;
      for(Cell i:list) {
        float dist=PApplet.dist(i.point.pos.x,i.point.pos.y,p.cam.mouseX,p.cam.mouseY);
        if(dist<minDist) {
          minDist=dist;
          select=i;
        }
      }
    }
    super.mousePressed(button);
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    int ts=in.getInt(offset);
    offset+=ByteData.INT_SIZE;
    list.clear();
    while(list.size()<ts) {
      Cell i=new Cell(p,this,0,0,0);
      i.fromData(in,offset,offset+=Cell.buffer_size);
      list.add(i);
    }
    layer.clear();
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putInt(offset,list.size());
    offset+=ByteData.INT_SIZE;
    for(Cell i:list) {
      i.toData(in,offset);
      offset+=i.bufferSize();
    }
    return in;
  }
  @Override
  public int bufferSize() {
    return list.size()*Cell.buffer_size+ByteData.INT_SIZE;
  }
}
