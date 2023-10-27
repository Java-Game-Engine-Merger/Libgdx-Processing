package pama1234.processing.al.sandbox.util.organ;

import pama1234.processing.al.autometa.particle.util.Cell;
import pama1234.processing.al.sandbox.util.Player;
import processing.core.PApplet;

public class Motion extends Organ{
  public static final int X=0,Y=1,SCORE=2,DIR=0,MAG=1;
  public float vx,vy;
  public Motion(Player parent,int x,int y) {
    super(parent,x,y,9,9,3,2,Vision.INPUT_SIZE,1);
  }
  @Override
  public void perceive() {
    perception[X]=p.cell.point.pos.x;
    perception[Y]=p.cell.point.pos.y;
    perception[SCORE]=p.cell.score.pos;
  }
  @Override
  public void act() {
    float mag=PApplet.constrain(action[MAG],-Cell.dist/16,Cell.dist/16);
    p.cell.point.vel.x+=vx=PApplet.sin(action[DIR])*mag;
    p.cell.point.vel.y+=vy=PApplet.cos(action[DIR])*mag;
    refreshComponent();
  }
  @Override
  public void draw() {
    //    c.g.point(2,2);
    c.g.line(4,4,4+vx*32,4+vy*32);
  }
}
