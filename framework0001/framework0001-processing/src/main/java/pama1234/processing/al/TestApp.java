package pama1234.processing.al;

import pama1234.processing.al.autometa.particle.ParticleAutomata;
import pama1234.processing.util.app.UtilApp;

public class TestApp extends UtilApp{
  ParticleAutomata pa;
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
    pa=new ParticleAutomata(this);
    center.add.add(pa);
  }
  @Override
  public void display() {}
  @Override
  public void update() {}
  @Override
  public void frameResized(int w,int h) {
    // System.out.println("TestApp.frameResized()");
  }
  @Override
  public void exitActual() {
    super.exitActual();
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new TestApp().runSketch();
  }
}
