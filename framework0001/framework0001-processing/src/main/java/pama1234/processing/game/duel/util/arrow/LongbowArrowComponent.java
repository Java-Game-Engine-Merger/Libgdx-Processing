package pama1234.processing.game.duel.util.arrow;

import pama1234.math.Tools;
import pama1234.processing.game.duel.Duel;
import pama1234.processing.game.duel.util.graphics.Particle;
import processing.core.PConstants;

public abstract class LongbowArrowComponent extends AbstractArrowActor{
  public final Duel duel;
  public LongbowArrowComponent(Duel duel) {
    super(16.0f,16.0f);
    this.duel=duel;
  }
  @Override
  public void act() {
    final float particleDirectionAngle=this.directionAngle+PConstants.PI+duel.random(-PConstants.HALF_PI,PConstants.HALF_PI);
    for(int i=0;i<5;i++) {
      final float particleSpeed=duel.random(2.0f,4.0f);
      final Particle newParticle=duel.system.commonParticleSet.builder
        .type(Particle.square)
        .position(this.xPosition,this.yPosition)
        .polarVelocity(particleDirectionAngle,particleSpeed)
        .particleSize(4.0f)
        .particleColor(Tools.color(64))
        .lifespanSecond(1.0f)
        .build();
      duel.system.commonParticleSet.particleList.add(newParticle);
    }
  }
  @Override
  public boolean isLethal() {
    return true;
  }
}