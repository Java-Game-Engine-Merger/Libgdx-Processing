package pama1234.gdx.util.app;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import pama1234.math.UtilMath;
import pama1234.math.transform.Pose3D;
import pama1234.math.vec.Vec3f;
import pama1234.math.vec.Vec4f;

public abstract class UtilScreenPose extends UtilScreenCore{
  public Matrix4 matrix() {
    if(matrixStackPointer<0) return usedCamera.combined;
    return matrixStack[matrixStackPointer];
  }
  public void pushMatrix() {
    Matrix4 tmat=matrixStack[matrixStackPointer+1];
    Matrix4 matIn=matrixStackPointer<0?usedCamera.combined:matrixStack[matrixStackPointer];
    matrixStack[matrixStackPointer+1]=tmat==null?matIn.cpy():tmat.set(matIn);
    matrixStackPointer++;
    setMatrix(matrix());
  }
  public void popMatrix() {
    matrixStackPointer--;
    setMatrix(matrix());
  }
  public void clearMatrix() {
    matrixStackPointer=-1;
    // Arrays.fill(matrixStack,null);
    setMatrix(usedCamera.combined);
  }
  public void copyMatrix(int i) {
    setMatrix(matrix().set(matrixStack[i]));
  }
  public void copyMatrix(Matrix4 m) {
    setMatrix(matrix().set(m));
  }
  //---------------------------------------------------------------------------
  // @Deprecated
  // public void pushMatrix(float dx,float dy,float deg) {
  //   pushMatrix();
  //   translate(dx,dy);
  //   rotate(deg);
  // }
  // @Deprecated
  // public void drawWithMatrix(float dx,float dy,float deg,ExecuteFunction e) {
  //   pushMatrix(dx,dy,deg);
  //   e.execute();
  //   popMatrix();
  // }
  @Deprecated
  public void pushStyle() {
    //TODO
  }
  @Deprecated
  public void popStyle() {
    //TODO
  }
  @Deprecated
  public void clearStyle() {
    //TODO
  }
  public void translate(float dx,float dy) {
    Matrix4 matrix=matrix();
    matrix.translate(dx,dy,0);
    setMatrix(matrix);
  }
  public void rotate(float rad) {
    Matrix4 matrix=matrix();
    matrix.rotate(0,0,1,UtilMath.deg(rad));
    setMatrix(matrix);
  }
  public void scale(float in) {
    Matrix4 matrix=matrix();
    matrix.scale(in,in,in);
    setMatrix(matrix);
  }
  //---------------------------------------------------------------------------
  public void translate(float dx,float dy,float dz) {
    Matrix4 matrix=matrix();
    matrix.translate(dx,dy,dz);
    setMatrix(matrix);
  }
  public void translate(Vec3f vec) {
    translate(vec.x,vec.y,vec.z);
  }
  /** 有bug */
  @Deprecated
  public void rotate(float rx,float ry,float rz) {
    Matrix4 matrix=matrix();
    matrix.rotate(1,0,0,UtilMath.deg(rx));
    matrix.rotate(0,1,0,UtilMath.deg(ry));
    matrix.rotate(0,0,1,UtilMath.deg(rz));
    setMatrix(matrix);
  }
  /** 有bug */
  @Deprecated
  public void rotateDeg(float rx,float ry,float rz) {
    Matrix4 matrix=matrix();
    matrix.rotate(1,0,0,rx);
    matrix.rotate(0,1,0,ry);
    matrix.rotate(0,0,1,rz);
    setMatrix(matrix);
  }
  /** 暂时替代 */
  Vector3 tempDir=new Vector3();
  @Deprecated
  public void rotateTempFun(float rx,float ry,float rz) {
    Matrix4 matrix=matrix();
    matrix.rotate(0,1,0,UtilMath.deg(ry));
    tempDir.set(1,0,0);
    tempDir.rotate(ry,0,1,0);
    matrix.rotate(tempDir,UtilMath.deg(rx));
    tempDir.rotate(tempDir,rx);
    matrix.rotate(tempDir,UtilMath.deg(rz));
    //    tempDir.rotate(tempDir,rz);
    setMatrix(matrix);
  }

  public void rotate(float rx,float ry,float rz,float rw) {
    Matrix4 matrix=matrix();
    tq.set(rx,ry,rz,rw);
    matrix.rotate(tq);
    setMatrix(matrix);
  }
  public void rotate(Quaternion q) {
    Matrix4 matrix=matrix();
    matrix.rotate(q);
    setMatrix(matrix);
  }
  Quaternion tq=new Quaternion();
  public void rotate(Vec4f q) {
    Matrix4 matrix=matrix();
    tq.set(q.x,q.y,q.z,q.w);
    matrix.rotate(tq);
    setMatrix(matrix);
  }
  public void rotateX(float in) {
    Matrix4 matrix=matrix();
    matrix.rotate(1,0,0,UtilMath.deg(in));
    setMatrix(matrix);
  }
  public void rotateY(float in) {
    Matrix4 matrix=matrix();
    matrix.rotate(0,1,0,UtilMath.deg(in));
    setMatrix(matrix);
  }
  public void rotateZ(float in) {
    Matrix4 matrix=matrix();
    matrix.rotate(0,0,1,UtilMath.deg(in));
    setMatrix(matrix);
  }
  public void rotate(Vec3f vec) {
    rotate(vec.x,vec.y,vec.z);
  }
  public void scale(float sx,float sy,float sz) {
    Matrix4 matrix=matrix();
    matrix.scale(sx,sy,sz);
    setMatrix(matrix);
  }
  public void scale(Vec3f vec) {
    scale(vec.x,vec.y,vec.z);
  }
  //  Quaternion tq=new Quaternion();
  public void pose(Pose3D pose) {
    translate(pose.pos);
    //    tq.set(pose.rotate.x,pose.rotate.y,pose.rotate.z,pose.rotate.w);
    //    rotate(tq);
    rotate(pose.rotate);
    scale(pose.scale);
  }
  //---------------------------------------------------------------------------
  public boolean customOrigin;
  @Deprecated
  public void origin(boolean horizontal,boolean vertical) {
    if(customOrigin) {
      popMatrix();
      pushMatrix();
    }else {
      pushMatrix();
      customOrigin=true;
    }
    // font.isFlipped();
    scale(horizontal?-1:1,vertical?-1:1,1);
    translate(horizontal?-height:0,vertical?-width:0,0);
    // popMatrix();
  }
  @Deprecated
  public void cleanOrigin() {
    if(customOrigin) {
      popMatrix();
      customOrigin=false;
    }
  }
}
