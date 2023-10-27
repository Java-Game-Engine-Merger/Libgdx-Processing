package pama1234.processing.art.star.util;

public class Star extends Cell{
  public Star(CellCenter parent,float x,float y) {
    super(parent,x,y);
    size=3;
    //    dist=size/2;
    dist=-1;
    life=1;
    //    point.f=1;
  }
  @Override
  public void update() {
    super.update();
  }
  @Override
  void collide(Cell other) {}
}
