package pama1234.processing.art.star.util;

import pama1234.math.physics.MassPoint;
import processing.core.PGraphics;

public abstract class Cell{
  public CellCenter parent;
  public final MassPoint point;
  public float size,dist,life;
  public Cell(CellCenter parent,float x,float y) {
    this.parent=parent;
    this.point=new MassPoint(x,y);
  }
  public void update() {
    point.update();
  }
  public void display(PGraphics g) {
    //    parent.layer.fill(255);
    g.ellipse(
      point.pos.x+CellCenter.borderSize,
      point.pos.y+CellCenter.borderSize,
      size,size);
  }
  abstract void collide(Cell other);
}
