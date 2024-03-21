package pama1234.math.transform;

import pama1234.math.vec.Vec3f;
import pama1234.math.vec.Vec4f;

public class Pose3D{
  public Vec3f pos;
  //  /** in rad format */
  //  public Vec3f rotate;
  public Vec4f rotate;
  public Vec3f scale;
  public Pose3D() {
    this(0,0,0);
  }
  public Pose3D(float dx,float dy,float dz) {
    pos=new Vec3f(dx,dz,dy);
    rotate=new Vec4f();
    scale=new Vec3f();
    scale=new Vec3f(1,1,1);
  }
  public Pose3D(
    float dx,float dy,float dz,
    float rx,float ry,float rz) {
    pos=new Vec3f(dx,dz,dy);
    rotate=new Vec4f(rx,ry,rz,0);
    scale=new Vec3f(1,1,1);
  }
  public Pose3D(
    float dx,float dy,float dz,
    float rx,float ry,float rz,
    float sx,float sy,float sz) {
    pos=new Vec3f(dx,dz,dy);
    rotate=new Vec4f(rx,ry,rz,0);
    scale=new Vec3f(sx,sy,sz);
  }
  public float dx() {
    return pos.x;
  }
  public float dy() {
    return pos.y;
  }
  public float dz() {
    return pos.z;
  }
  public float rx() {
    return rotate.x;
  }
  public float ry() {
    return rotate.y;
  }
  public float rz() {
    return rotate.z;
  }
  public float sx() {
    return scale.x;
  }
  public float sy() {
    return scale.y;
  }
  public float sz() {
    return scale.z;
  }

}
