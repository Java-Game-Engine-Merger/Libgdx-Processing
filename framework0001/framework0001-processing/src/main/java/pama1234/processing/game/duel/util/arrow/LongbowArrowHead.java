package pama1234.processing.game.duel.util.arrow;

import pama1234.processing.game.duel.Duel;

public final class LongbowArrowHead extends LongbowArrowComponent{
  public final float halfHeadLength=24.0f;
  public final float halfHeadWidth=24.0f;
  public LongbowArrowHead(Duel duel) {
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
    duel.line(-halfLength,0.0f,0.0f,0.0f);
    duel.quad(
      0.0f,0.0f,
      -halfHeadLength,-halfHeadWidth,
      +halfHeadLength,0.0f,
      -halfHeadLength,+halfHeadWidth);
    duel.popMatrix();
    duel.strokeWeight(1.0f);
  }
}