package pama1234.processing.game.duel.util.graphics;

import static pama1234.processing.game.duel.Duel.INTERNAL_CANVAS_SIDE_LENGTH;

import pama1234.processing.game.duel.Duel;

public final class VerticalLine extends BackgroundLine{
  public final Duel duel;
  public VerticalLine(Duel duel) {
    super(duel.random(INTERNAL_CANVAS_SIDE_LENGTH));
    this.duel=duel;
  }
  @Override
  public void display() {
    duel.line(position,0.0f,position,INTERNAL_CANVAS_SIDE_LENGTH);
  }
  @Override
  public float getMaxPosition() {
    return INTERNAL_CANVAS_SIDE_LENGTH;
  }
}