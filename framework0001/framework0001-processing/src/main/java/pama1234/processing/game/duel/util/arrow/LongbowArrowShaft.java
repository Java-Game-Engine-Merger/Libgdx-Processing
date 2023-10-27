package pama1234.processing.game.duel.util.arrow;

import pama1234.processing.game.duel.Duel;

public final class LongbowArrowShaft extends LongbowArrowComponent{
  public LongbowArrowShaft(Duel duel) {
    super(duel);
  }
  @Override
  public void display() {
    duel.strokeWeight(5.0f);
    duel.stroke(0.0f);
    duel.fill(0.0f);
    duel.pushMatrix();
    duel.translate(xPosition,yPosition);
    duel.rotate(rotationAngle);
    duel.line(-halfLength,0.0f,halfLength,0.0f);
    duel.popMatrix();
    duel.strokeWeight(1.0f);
  }
}