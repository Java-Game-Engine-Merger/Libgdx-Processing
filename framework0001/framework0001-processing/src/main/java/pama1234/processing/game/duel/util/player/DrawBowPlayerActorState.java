package pama1234.processing.game.duel.util.player;

import pama1234.processing.game.duel.util.actor.PlayerActor;
import pama1234.processing.game.duel.util.input.AbstractInputDevice;

public abstract class DrawBowPlayerActorState extends PlayerActorState{
  public PlayerActorState moveState;
  @Override
  public void act(PlayerActor parentActor) {
    final AbstractInputDevice input=parentActor.engine.controllingInputDevice;
    aim(parentActor,input);
    parentActor.addVelocity(0.25f*input.horizontalMoveButton,0.25f*input.verticalMoveButton);
    if(triggerPulled(parentActor)) fire(parentActor);
    if(!buttonPressed(input)) {
      parentActor.state=moveState.entryState(parentActor);
    }
  }
  public abstract void aim(PlayerActor parentActor,AbstractInputDevice input);
  public abstract void fire(PlayerActor parentActor);
  public abstract boolean buttonPressed(AbstractInputDevice input);
  public abstract boolean triggerPulled(PlayerActor parentActor);
}