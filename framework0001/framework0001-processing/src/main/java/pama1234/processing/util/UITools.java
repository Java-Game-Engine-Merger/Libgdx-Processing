package pama1234.processing.util;

import processing.core.PGraphics;

public class UITools{
  public static int weight=1;
  //  public static final PStyle style=new PStyle();
  //  static {
  //    style.stroke=false;
  //  }
  public static void reversedBorder(final PGraphics l,final float x,final float y,float w,float h) {
    border(l,x,y,w,h,0x80000000,0x80ffffff);
  }
  public static void border(final PGraphics l,final float x,final float y,float w,float h,int a,int b) {
    final int tc=l.fillColor;
    final boolean ts=l.stroke;
    l.noStroke();
    l.fill(a);
    l.rect(x,y,w,weight);
    l.rect(x,y,weight,h);
    l.fill(b);
    l.rect(x,y+h-weight,w,weight);
    l.rect(x+w-weight,y,weight,h);
    l.fill(tc);
    l.stroke=ts;
  }
  public static void border(final PGraphics l,final float x,final float y,float w,float h) {
    border(l,x,y,w,h,0x80ffffff,0x80000000);
  }
  public static void rectBorder(final PGraphics l) {
    border(l,0,0,l.width,l.height);
  }
  public static void cross(final PGraphics l,float x,float y,float a,float b) {
    l.line(x-a,y,x+a,y);
    l.line(x,y-b,x,y+b);
  }
}
