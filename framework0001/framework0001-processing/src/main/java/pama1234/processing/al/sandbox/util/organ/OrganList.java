package pama1234.processing.al.sandbox.util.organ;

import java.util.ArrayList;

public class OrganList extends ArrayList<Organ>{
  /**
   *
   */
  private static final long serialVersionUID=-9212641501866047767L;
  public void act() {
    for(Organ o:this) o.act();
  }
  public void perceive() {
    for(Organ o:this) o.perceive();
  }
  public void setPerception() {
    for(Organ o:this) o.setPerception();
  }
  public void getAction() {
    for(Organ o:this) o.getAction();
  }
  public void display() {
    for(Organ o:this) o.display();
  }
  public void update() {
    for(Organ o:this) o.c.point.update();
  }
}
