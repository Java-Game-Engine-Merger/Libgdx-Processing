package pama1234.processing.al.sandbox.app;

import pama1234.processing.al.sandbox.Sandbox;
import pama1234.processing.util.app.UtilApp;

public class MainApp extends UtilApp{
  Sandbox sb;
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
    sb=new Sandbox(this);
    center.add.add(sb);
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
