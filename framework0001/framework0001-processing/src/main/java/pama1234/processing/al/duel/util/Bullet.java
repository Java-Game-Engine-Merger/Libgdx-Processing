package pama1234.processing.al.duel.util;

public class Bullet extends Cell{
  public Bullet(DuelCenter parent,float x,float y) {
    super(parent,x,y);
    size=2;
    dist=2;
    life=30;
    point.f=1f;
  }
  @Override
  public void update() {
    super.update();
    life--;
    if(life<=0) parent.center.remove.add(this);
  }
  @Override
  public void display() {
    parent.layer.fill(0xffff0000);
    super.display();
  }
  @Override
  void collide(Cell other) {
    if(other instanceof Player player) {
      player.life-=4;
    }else if(other instanceof Bullet bullet) {
      bullet.parent.center.remove.add(bullet);
      parent.center.remove.add(this);
    }
  }
}