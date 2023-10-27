package pama1234.processing.al.autometa.particle.app;

import pama1234.processing.al.autometa.particle.ParticleAutomata;
import pama1234.processing.util.app.UtilApp;

public class MainApp extends UtilApp{
  public static final int cam_box_r=720;
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
    center.add.add(new ParticleAutomata(this));
  }
  @Override
  public void update() {
    cam.point.des.setInBox(-cam_box_r,-cam_box_r,cam_box_r,cam_box_r);
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new MainApp().runSketch();
  }
  @Override
  public void display() {}
}
