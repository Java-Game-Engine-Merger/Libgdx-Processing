package pama1234.processing.al.sandbox.util.organ;

import pama1234.processing.al.corewars.util.LightComponent;
import pama1234.processing.al.sandbox.util.Player;
import pama1234.processing.util.UITools;

public abstract class Organ{
  public Player p;
  public LightComponent c;
  public float[] perception,action;
  public int perceptionPos,actionPos;
  public Organ(Player parent,int x,int y,int w,int h,int inputSize,int outputSize,int perceptionPos,int actionPos) {
    this.p=parent;
    c=new LightComponent(p.p,x,y,w,h);
    c.initGraphics();
    perception=new float[inputSize];
    action=new float[outputSize];
    this.perceptionPos=perceptionPos;
    this.actionPos=actionPos;
  }
  //  public abstract void playerToWorldUpdate();
  //  public abstract void worldToPlayerUpdate();
  public abstract void perceive();
  public abstract void act();
  public void setPerception() {
    for(int i=0;i<perception.length;i++) p.content.setInput(perceptionPos+i,perception[i]);
  }
  public void getAction() {
    for(int i=0;i<action.length;i++) action[i]=p.content.getOutput(actionPos+i);
  }
  public void refreshComponent() {
    c.g.beginDraw();
    c.g.background(127);
    UITools.rectBorder(c.g);
    draw();
    c.g.endDraw();
  }
  public abstract void draw();
  public void display() {
    c.display();
  }
}
