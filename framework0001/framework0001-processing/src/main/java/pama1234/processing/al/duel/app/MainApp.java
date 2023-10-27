package pama1234.processing.al.duel.app;

import pama1234.processing.al.duel.Duel;
import pama1234.processing.util.app.UtilApp;

public class MainApp extends UtilApp{
  Duel d;
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
    d=new Duel(this);
    center.add.add(d);
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
