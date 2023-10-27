package pama1234.processing.al.sandbox.util.organ.copy;

import pama1234.processing.util.app.UtilApp;

public class TestApp extends UtilApp{
  Motion motion;
  @Override
  public void settings() {
    super.settings();
    noSmooth();
    background=color(0);
  }
  @Override
  public void init() {
    strokeWeight(1/2f);
    noFill();
    textSize(4);
    textAlign(LEFT,BOTTOM);
    motion=new Motion(this,0,0,null);
    center.add.add(motion);
  }
  @Override
  public void display() {}
  @Override
  public void update() {}
  @Override
  public void exitActual() {
    super.exitActual();
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new TestApp().runSketch();
  }
}
