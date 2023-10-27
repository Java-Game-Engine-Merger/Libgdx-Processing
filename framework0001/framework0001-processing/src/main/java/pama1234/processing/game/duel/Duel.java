package pama1234.processing.game.duel;

import pama1234.processing.game.duel.util.input.KeyInput;
import pama1234.processing.util.app.UtilApp;
import processing.core.PFont;

/**
 * Title: Duel
 * </p>
 * Author: FAL ( https://www.fal-works.com/ )
 * </p>
 * Made with Processing 3.3.6
 * </p>
 * </p>
 * Change log: Ver. 0.1 (30. Sep. 2017) First version. Ver. 0.2 ( 1. Oct. 2017) Bug fix
 * (unintended change of strokeWeight), minor update (enabled to hide instruction window). Ver.
 * 0.3 (10. Feb. 2018) Minor fix (lack of semicolon). Ver. 0.4 (12. Feb. 2018) Enabled scaling.
 * </p>
 * </p>
 * Modified by: Pama1234 (https://space.bilibili.com/646050693)
 * </p>
 * Altered with self-made framework v0.0.1
 * </p>
 * </p>
 *
 * The font "Unifont" https://unifoundry.com/unifont/ is part of the GNU Project.
 */
public class Duel extends UtilApp{
  public static final float IDEAL_FRAME_RATE=60.0f;
  public static final int INTERNAL_CANVAS_SIDE_LENGTH=640;
  public KeyInput currentKeyInput;
  public GameSystem system;
  public PFont smallFont,largeFont;
  public int smallFontSize=16,largeFontSize=128;
  public boolean paused;
  public int canvasSideLength=INTERNAL_CANVAS_SIDE_LENGTH;
  {
    loadFont=true;
  }
  @Override
  public void settings() {
    size(canvasSideLength,canvasSideLength);
  }
  @Override
  public void init() {
    frameRate(IDEAL_FRAME_RATE);
    smallFont=createFont(fontPath,smallFontSize,true);
    largeFont=createFont(fontPath,largeFontSize,true);
    textFont(largeFont,largeFontSize);
    textAlign(CENTER,CENTER);
    rectMode(CENTER);
    ellipseMode(CENTER);
    currentKeyInput=new KeyInput();
    newGame(true,true); // demo play (computer vs computer), shows instruction window
    //---
    cam.point.des.set(canvasSideLength/2f,canvasSideLength/2f);
    cam.point.pos.set(cam.point.des);
    cam.drag=false;
  }
  @Override
  public void display() {
    background(255);
    system.display();
  }
  @Override
  public void update() {
    system.update();
  }
  public void newGame(boolean demo,boolean instruction) {
    system=new GameSystem(this,demo,instruction);
  }
  @Override
  public void mousePressed() {
    if(mouseButton==LEFT) system.showsInstructionWindow=!system.showsInstructionWindow;
  }
  @Override
  public void keyPressed() {
    currentKeyInput.keyPressedEvent(this,key,keyCode);
  }
  public void doPause() {
    if(paused) loop();
    else noLoop();
    paused=!paused;
  }
  @Override
  public void keyReleased() {
    currentKeyInput.keyReleased(this,key,keyCode);
  }
  public static void main(String[] passedArgs) {
    new Duel().run();
  }
}