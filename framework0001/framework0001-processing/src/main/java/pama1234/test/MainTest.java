package pama1234.test;

import pama1234.processing.util.app.UtilApp;

/**
 * TODO Processing3和4的中frameResized的表现不同，需降低processing版本
 */
public class MainTest{
  public static void main(String[] args) {
    new UtilApp() {
      @Override
      public void init() {}
      @Override
      public void display() {
        background(255,0,0);
        rect(cam.mouseX,cam.mouseY,32,32);
      }
      @Override
      public void update() {}
      public void frameResized(int w,int h) {
        println(w,h,width,height);
      }
    }.run();
  }
}
