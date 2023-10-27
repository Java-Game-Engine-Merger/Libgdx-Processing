package pama1234.processing.game.duel.util.player;

import pama1234.processing.game.duel.Duel;
import pama1234.processing.game.duel.util.actor.PlayerActor;
import pama1234.processing.game.duel.util.actor.ShortbowArrow;
import pama1234.processing.game.duel.util.input.AbstractInputDevice;
import processing.core.PApplet;
import processing.core.PConstants;

public final class DrawShortbowPlayerActorState extends DrawBowPlayerActorState{
  private final Duel duel;
  public DrawShortbowPlayerActorState(Duel duel) {
    this.duel=duel;
  }
  final int fireIntervalFrameCount=PApplet.parseInt(Duel.IDEAL_FRAME_RATE*0.2f);
  @Override
  public void aim(PlayerActor parentActor,AbstractInputDevice input) {
    parentActor.aimAngle=getEnemyPlayerActorAngle(parentActor);
  }
  @Override
  public void fire(PlayerActor parentActor) {
    ShortbowArrow newArrow=new ShortbowArrow(this.duel);
    final float directionAngle=parentActor.aimAngle;
    newArrow.xPosition=parentActor.xPosition+24.0f*PApplet.cos(directionAngle);
    newArrow.yPosition=parentActor.yPosition+24.0f*PApplet.sin(directionAngle);
    newArrow.rotationAngle=directionAngle;
    newArrow.setVelocity(directionAngle,24.0f);
    parentActor.group.addArrow(newArrow);
  }
  @Override
  public void displayEffect(PlayerActor parentActor) {
    duel.line(0.0f,0.0f,70.0f*PApplet.cos(parentActor.aimAngle),70.0f*PApplet.sin(parentActor.aimAngle));
    duel.noFill();
    duel.arc(0.0f,0.0f,100.0f,100.0f,parentActor.aimAngle-PConstants.QUARTER_PI,parentActor.aimAngle+PConstants.QUARTER_PI);
  }
  @Override
  public PlayerActorState entryState(PlayerActor parentActor) {
    return this;
  }
  @Override
  public boolean buttonPressed(AbstractInputDevice input) {
    return input.shotButtonPressed;
  }
  @Override
  public boolean triggerPulled(PlayerActor parentActor) {
    return duel.frameCount%fireIntervalFrameCount==0;
  }
}