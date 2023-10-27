package pama1234.processing.art.star.util;

public class Attractor extends Cell{
  public Attractor(CellCenter parent,float x,float y) {
    super(parent,x,y);
    size=4;
    //    dist=size/2+2;
    dist=-1;
    life=1;
  }
  @Override
  public void update() {
    super.update();
  }
  @Override
  void collide(Cell other) {}
}
