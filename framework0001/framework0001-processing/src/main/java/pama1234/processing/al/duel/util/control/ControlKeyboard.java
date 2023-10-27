package pama1234.processing.al.duel.util.control;

import pama1234.processing.util.app.UtilApp;
import processing.core.PConstants;

public class ControlKeyboard extends Keyboard{
  public ControlKeyboard(UtilApp p,int x,int y) {
    super(p,x,y,3,3);
    c.add.add(new KeyBar(this,new IntKey(PConstants.UP),g,new KeyBarStyle(),0,0,1,1) {
      @Override
      public void tick() {}
      @Override
      public void keyReleased() {}
      @Override
      public void keyPressed() {}
    });
    c.refresh();
    initGraphics();
    refresh();
  }
  public static void main(String[] args) {
    new UtilApp() {
      ControlKeyboard c;
      @Override
      public void settings() {
        super.settings();
        background=color(0);
        noSmooth();
      }
      @Override
      public void init() {
        stroke(0xc0ffffff);
        c=new ControlKeyboard(this,0,0);
        c.refresh();
        center.add.add(c);
      }
      @Override
      public void display() {}
      @Override
      public void update() {}
    }.run();
  }
}
