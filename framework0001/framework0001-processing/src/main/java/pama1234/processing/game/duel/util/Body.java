package pama1234.processing.game.duel.util;

import processing.core.PApplet;

public abstract class Body{
  public float xPosition,yPosition;
  public float xVelocity,yVelocity;
  public float directionAngle;
  public float speed;
  public void update() {
    xPosition+=xVelocity;
    yPosition+=yVelocity;
  }
  public abstract void display();
  public void setVelocity(float dir,float spd) {
    directionAngle=dir;
    speed=spd;
    xVelocity=speed*PApplet.cos(dir);
    yVelocity=speed*PApplet.sin(dir);
  }
  public float getDistance(Body other) {
    return PApplet.dist(this.xPosition,this.yPosition,other.xPosition,other.yPosition);
  }
  public float getDistancePow2(Body other) {
    return PApplet.sq(other.xPosition-this.xPosition)+PApplet.sq(other.yPosition-this.yPosition);
  }
  public float getAngle(Body other) {
    return PApplet.atan2(other.yPosition-this.yPosition,other.xPosition-this.xPosition);
  }
}