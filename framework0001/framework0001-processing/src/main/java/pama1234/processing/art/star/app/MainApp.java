package pama1234.processing.art.star.app;

import pama1234.processing.art.star.StarBox;
import pama1234.processing.util.app.UtilApp;

public class MainApp extends UtilApp{
  public static final int cam_box_r=720;
  @Override
  public void settings() {
    super.settings();
    fullScreen();
    noSmooth();
    background=color(0);
  }
  @Override
  public void init() {
    strokeWeight(1/2f);
    noFill();
    textSize(4);
    textAlign(LEFT,BOTTOM);
    center.add.add(new StarBox(this));
  }
  @Override
  public void update() {
    cam.point.des.setInBox(-cam_box_r,-cam_box_r,cam_box_r,cam_box_r);
  }
  @Override
  public void display() {}
}
