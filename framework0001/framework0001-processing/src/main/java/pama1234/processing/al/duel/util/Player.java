package pama1234.processing.al.duel.util;

public class Player extends Cell{
  public Player(DuelCenter parent,float x,float y) {
    super(parent,x,y);
    size=12;
    dist=12;
    life=255;
  }
  @Override
  public void update() {
    super.update();
    life+=0.5f;
  }
  @Override
  public void display() {
    parent.layer.fill(life);
    parent.layer.stroke(255);
    super.display();
    parent.layer.noStroke();
  }
  @Override
  void collide(Cell other) {}
}
