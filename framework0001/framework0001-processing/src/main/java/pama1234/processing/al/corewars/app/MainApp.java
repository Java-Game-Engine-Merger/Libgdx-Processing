package pama1234.processing.al.corewars.app;

import pama1234.processing.util.app.UtilApp;

public class MainApp extends UtilApp{
  TestEntity mans;
  @Override
  public void settings() {
    super.settings();
    noSmooth();
    background=color(0);
  }
  @Override
  public void init() {
    mans=new TestEntity(this);
    center.add.add(mans);
    strokeWeight(1/2f);
    noFill();
    textSize(4);
    textAlign(LEFT,BOTTOM);
  }
  @Override
  public void display() {}
  @Override
  public void update() {}
  @Override
  public void exitActual() {
    super.exitActual();
  }
}
