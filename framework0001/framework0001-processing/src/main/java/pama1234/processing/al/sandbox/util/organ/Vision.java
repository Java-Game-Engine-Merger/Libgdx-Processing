package pama1234.processing.al.sandbox.util.organ;

import static pama1234.processing.al.autometa.particle.util.CellCenter.borderSize;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import pama1234.math.Tools;
import pama1234.processing.al.autometa.particle.util.Cell;
import pama1234.processing.al.sandbox.util.Player;
import processing.awt.PGraphicsJava2D;
import processing.core.PApplet;

public class Vision extends Organ{
  public static final float MAG_SIZE=Cell.size*6;
  public static final int INPUT_SIZE=(int)MAG_SIZE*4;
  public static final int DIR=0;
  public WritableRaster raster;
  //  public int[] pixels;
  public int[][] vi=new int[(int)MAG_SIZE][3];
  //  public int[][] trvi=new int[(int)MAG_SIZE*(int)MAG_SIZE*4][3];
  public Vision(Player parent,int x,int y) {
    super(parent,x,y,(int)MAG_SIZE*2+2,(int)MAG_SIZE*2+2,INPUT_SIZE,1,0,0);
    c.g.noStroke();
  }
  @Override
  public void perceive() {
    raster=getRaster();
    //    pixels=getPixels();
    //    System.out.print(raster.getSample(getCellX(),getCellY(),0)+" ");
    //    System.out.print(raster.getSample(getCellX(),getCellY(),1)+" ");
    //    System.out.println(raster.getSample(getCellX(),getCellY(),2));
    //    System.out.print(getCellX()+" ");
    //    System.out.println(getCellY());
    float dx=PApplet.sin(action[DIR])*MAG_SIZE,
      dy=PApplet.cos(action[DIR])*MAG_SIZE;
    for(int i=0;i<MAG_SIZE;i++) {
      float tdx=dx/MAG_SIZE*i,
        tdy=dy/MAG_SIZE*i;
      vi[i][0]=(int)tdx;
      vi[i][1]=(int)tdy;
      int tx=(int)(getX()+tdx),
        ty=(int)(getY()+tdy);
      //      final PImage l=getImage();
      //      if(!inBox(tx,ty,0,0,l.width,l.height)) {
      if(!inBox(tx,ty,0,0,raster.getWidth(),raster.getHeight())) {
        perception[i*4+3]=0xff;
        perception[i*4]=perception[i*4+1]=perception[i*4+2]=0xff;
      }else {
        perception[i*4]=raster.getSampleFloat(tx,ty,0);
        perception[i*4+1]=raster.getSampleFloat(tx,ty,1);
        perception[i*4+2]=raster.getSampleFloat(tx,ty,2);
        perception[i*4+3]=raster.getSampleFloat(tx,ty,3);
        //        raster.setSample(tx,ty,0,0xff);
        //        raster.setSample(tx,ty,1,0xff);
        //        raster.setSample(tx,ty,2,0xff);
        //        raster.setSample(tx,ty,3,0xff);
      }
      //      final int tc=pixels[ty*l.width+tx];
      //      perception[i*3]=(tc>>16)&0xff;
      //      perception[i*3+1]=(tc>>8)&0xff;
      //      perception[i*3+2]=tc&0xff;
      vi[i][2]=Tools.color(
        perception[i*4],
        perception[i*4+1],
        perception[i*4+2],
        perception[i*4+3]);
      //      System.out.println(Integer.toHexString(vi[i][2]));
    }
    //    for(int i=0;i<trvi.length;i++) {
    //      //      int tx=(int)p.p.random(-MAG_SIZE,MAG_SIZE),
    //      //        ty=(int)p.p.random(-MAG_SIZE,MAG_SIZE);
    //      int tx=i%((int)MAG_SIZE*2)-(int)MAG_SIZE,
    //        ty=i/((int)MAG_SIZE*2)-(int)MAG_SIZE;
    //      trvi[i][0]=tx;
    //      trvi[i][1]=ty;
    //      tx+=getX();
    //      ty+=getY();
    //      //      final PImage l=getImage();
    //      //      if(!inBox(tx,ty,0,0,l.width,l.height)) {
    //      if(!inBox(tx,ty,0,0,raster.getWidth(),raster.getHeight())) {
    //        trvi[i][2]=Tools.color(255,255,255);
    //      }else {
    //        //      trvi[i][2]=pixels[ty*l.width+tx];
    //        trvi[i][2]=Tools.color(
    //          raster.getSample(tx,ty,0),
    //          raster.getSample(tx,ty,1),
    //          raster.getSample(tx,ty,2),
    //          raster.getSample(tx,ty,3));
    //      }
    //    }
  }
  @Override
  public void draw() {
    //    for(int i=0;i<trvi.length;i++) {
    //      float tx=MAG_SIZE+trvi[i][0];
    //      float ty=MAG_SIZE+trvi[i][1];
    //      c.g.stroke(0);
    //      c.g.point(tx,ty);
    //      c.g.stroke(trvi[i][2]);
    //      c.g.point(tx,ty);
    //      c.g.set((int)tx,(int)ty,trvi[i][2]);
    //    }
    for(int[] element:vi) {
      final float tx=MAG_SIZE+element[0],
        ty=MAG_SIZE+element[1];
      //      c.g.stroke(0);
      //      c.g.point(tx,ty);
      //      //      c.g.stroke(vi[i][2]|0xff000000);
      //      c.g.stroke(vi[i][2]);
      //      c.g.point(tx,ty);
      c.g.fill(0);
      c.g.rect(tx,ty,1,1);
      c.g.fill(element[2]);
      c.g.rect(tx,ty,1,1);
    }
  }
  public static boolean inBox(int a,int b,int x,int y,int w,int h) {
    return a>=x&&a<w&&b>=y&&b<h;
  }
  public WritableRaster getRaster() {
    return ((BufferedImage)((PGraphicsJava2D)p.cell.parent.layer).getImage()).getRaster();
  }
  //  public PGraphics getImage() {
  //    return p.cell.parent.layer;
  //  }
  //  public int[] getPixels() {
  //    return getImage().pixels;
  //  }
  public int getY() {
    return Math.round(p.cell.point.pos.y-p.cell.parent.getYInt()+borderSize);
  }
  public int getX() {
    return Math.round(p.cell.point.pos.x-p.cell.parent.getXInt()+borderSize);
  }
  @Override
  public void act() {
    refreshComponent();
  }
}
