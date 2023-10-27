package pama1234.processing.art.star;

import pama1234.processing.art.star.app.MainApp;
import pama1234.processing.art.star.util.Attractor;
import pama1234.processing.art.star.util.CellCenter;
import pama1234.processing.art.star.util.Star;
import pama1234.processing.util.app.UtilApp;
import processing.core.PApplet;
import processing.core.PConstants;

public class StarBox extends CellCenter{
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new MainApp().run();
  }
  public StarBox(UtilApp p) {
    super(p);
    layer[0].colorMode(PConstants.HSB);
    //    p.colorMode(PConstants.HSB);
    final float size=320;
    int l=640;
    for(int i=0;i<l;i++) {
      final float deg=PApplet.radians((float)i/l*360);
      c.add.add(new Star(this,w/2+PApplet.sin(deg)*size,h/2+PApplet.cos(deg)*size));
    }
    for(int i=0;i<32;i++) c.add.add(new Attractor(this,p.random(w),p.random(h)));
  }
}
