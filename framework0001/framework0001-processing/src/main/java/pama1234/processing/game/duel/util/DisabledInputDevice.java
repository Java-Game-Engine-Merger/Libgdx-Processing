package pama1234.processing.game.duel.util;

import pama1234.processing.game.duel.util.input.AbstractInputDevice;

public final class DisabledInputDevice extends AbstractInputDevice{
  @Override
  public void operateMoveButton(int horizontal,int vertical) {}
  @Override
  public void operateShotButton(boolean pressed) {}
  @Override
  public void operateLongShotButton(boolean pressed) {}
}