package pama1234.processing.lang.visual.app;

import pama1234.processing.lang.visual.Sandbox;
import pama1234.processing.util.app.UtilApp;

public class MainApp extends UtilApp{
  Sandbox sandbox;
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
    //---
    sandbox=new Sandbox(this);
    center.add.add(sandbox);
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
    new MainApp().runSketch();
  }
}
