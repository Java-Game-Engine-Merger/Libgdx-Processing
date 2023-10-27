package pama1234.processing.lang.visual.util;

import processing.core.PGraphics;

public class UITools{
  public static int weight=1;
  public static void rectBorder(final PGraphics layer,final float x,final float y,float x2,float y2) {
    final int tc=layer.fillColor;
    layer.fill(0x80ffffff);
    layer.rect(x,y,x2,weight);
    layer.rect(x,y,weight,y2);
    layer.fill(0x80000000);
    layer.rect(x,y+y2-weight,x2,weight);
    layer.rect(x+x2-weight,y,weight,y2);
    layer.fill(tc);
  }
  public static void rectBorder(final PGraphics layer) {
    layer.pushMatrix();
    layer.resetMatrix();
    rectBorder(layer,0,0,layer.width,layer.height);
    layer.popMatrix();
  }
}
