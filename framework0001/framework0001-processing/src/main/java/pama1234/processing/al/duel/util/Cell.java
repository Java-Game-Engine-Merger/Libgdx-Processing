package pama1234.processing.al.duel.util;

import pama1234.math.physics.MassPoint;

public abstract class Cell{
  public static final float scoreDamping=1f/8;
  /**
   * this two value should always be integer
   */
  //  public static final float size=11,dist=size+1;
  public DuelCenter parent;
  public final MassPoint point;
  public float size,dist,life;
  public Cell(DuelCenter parent,float x,float y) {
    this.parent=parent;
    this.point=new MassPoint(x,y);
  }
  public void update() {
    point.update();
  }
  public void display() {
    //    parent.layer.fill(255);
    parent.layer.ellipse(
      point.pos.x+DuelCenter.borderSize,
      point.pos.y+DuelCenter.borderSize,
      size,size);
  }
  abstract void collide(Cell other);
}
