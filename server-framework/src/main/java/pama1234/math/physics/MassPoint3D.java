package pama1234.math.physics;

import java.nio.ByteBuffer;

import pama1234.data.nio.ByteBufferData;
import pama1234.math.UtilMath;
import pama1234.math.geometry.CubeI;
import pama1234.math.vec.Vec3f;

/**
 * 众所周知，带有“位置和速度”，每“刷新一次”就“移动一定距离”然后“因为摩擦力而减速”，是一种很合理的效果 点的力学实现
 *
 * @see Point 位置信息
 * @see PathPoint 缓动效果的实现
 */
public class MassPoint3D extends Point3D implements ByteBufferData{
  public static final int buffer_size=Vec3f.buffer_size*2+FLOAT_SIZE;
  /**
   * 存储速度的信息的二维矢量
   */
  public Vec3f vel;
  {
    f=0.8f;
  }
  @Deprecated
  public MassPoint3D() {}
  public MassPoint3D(Vec3f pos,Vec3f vel) {
    this.pos=pos;
    this.vel=vel;
  }
  public MassPoint3D(Vec3f pos) {
    vel=new Vec3f();
    this.pos=pos;
  }
  public MassPoint3D(float a,float b,float c,Vec3f vel) {
    pos=new Vec3f(a,b,c);
    this.vel=vel;
  }
  public MassPoint3D(float a,float b,float c) {
    pos=new Vec3f(a,b,c);
    vel=new Vec3f();
  }
  public MassPoint3D(float a,float b,float c,float d,float e,float f) {
    pos=new Vec3f(a,b,c);
    vel=new Vec3f(d,e,f);
  }
  @Override
  public void update() {
    if(step==1) {
      pos.add(vel);
      if(f!=1) vel.scale(f);
    }else {
      pos.add(vel.x*step,vel.y*step,vel.z*step);
      if(f!=1) {
        float tf=(f-1)*step;
        vel.add(vel.x*tf,vel.y*tf,vel.z*tf);
      }
    }
  }
  public float dx() {
    return vel.x*step;
  }
  public float dy() {
    return vel.y*step;
  }
  @Override
  public void add(float x,float y,float z) {
    vel.add(x,y,z);
  }
  public void setInBox(int x1,int y1,int z1,int x2,int y2,int z2) {
    if(pos.x<x1) {
      pos.x=x1;
      vel.x*=-f;
    }else if(pos.x>x2) {
      pos.x=x2;
      vel.x*=-f;
    }
    if(pos.y<y1) {
      pos.y=y1;
      vel.y*=-f;
    }else if(pos.y>y2) {
      pos.y=y2;
      vel.y*=-f;
    }
    if(pos.z<z1) {
      pos.z=z1;
      vel.z*=-f;
    }else if(pos.z>z2) {
      pos.z=z2;
      vel.z*=-f;
    }
  }
  public void moveInBox(int x1,int y1,int z1,int x2,int y2,int z2) {
    x2-=x1;
    y2-=y1;
    pos.sub(x1,y1,z1);
    {
      float tx=(int)Math.floor(pos.x/x2)*x2;
      float ty=(int)Math.floor(pos.y/y2)*y2;
      pos.sub(tx,ty,z1);
    }
    pos.add(x1,y1,z1);
  }
  public void setOutBox(CubeI rect) {
    setOutBox(
      rect.x(),rect.y(),rect.z(),
      rect.w(),rect.h(),rect.l());
  }
  public void setOutBox(float x,float y,float z,float w,float h,float l) {

    float leftDistance=UtilMath.abs(x-pos.x);
    float rightDistance=UtilMath.abs(pos.x-(x+w));

    float topDistance=UtilMath.abs(y-pos.y);
    float bottomDistance=UtilMath.abs(pos.y-(y+h));

    float neerDistance=UtilMath.abs(z-pos.z);
    float farDistance=UtilMath.abs(pos.z-(z+l));

    float minDistance=UtilMath.min(Math.min(leftDistance,rightDistance),Math.min(topDistance,bottomDistance),Math.min(neerDistance,farDistance));
    if(minDistance==leftDistance) {
      pos.x=x;
      vel.x*=-f;
    }else if(minDistance==rightDistance) {
      pos.x=x+w;
      vel.x*=-f;
    }else if(minDistance==topDistance) {
      pos.y=y;
      vel.y*=-f;
    }else if(minDistance==bottomDistance) {
      pos.y=y+h;
      vel.y*=-f;
    }else if(minDistance==neerDistance) {
      pos.z=z;
      vel.z*=-f;
    }else if(minDistance==farDistance) {
      pos.z=z+l;
      vel.z*=-f;
    }else {
      pos.y=y+h;
      vel.y*=-f;
    }
  }
  // TODO 评估必要性
  @Deprecated
  public void cloneFrom(MassPoint in) {
    set(in);
  }
  public void set(MassPoint in) {
    pos.set(in.pos);
    vel.set(in.vel);
    f=in.f;
    step=in.step;
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    f=in.getFloat(offset);
    pos.fromData(in,offset+=FLOAT_SIZE,offset+=pos.bufferSize());
    vel.fromData(in,offset,offset+=vel.bufferSize());
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putFloat(offset,f);
    pos.toData(in,offset+=FLOAT_SIZE);
    vel.toData(in,offset+=pos.bufferSize());
    return in;
  }
  @Override
  public int bufferSize() {
    return buffer_size;
  }
}
