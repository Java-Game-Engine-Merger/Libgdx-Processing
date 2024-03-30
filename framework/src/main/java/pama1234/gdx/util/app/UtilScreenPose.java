package pama1234.gdx.util.app;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.tools.GdxMath;
import pama1234.math.UtilMath;
import pama1234.math.transform.Pose3D;
import pama1234.math.vec.Vec3f;
import pama1234.math.vec.Vec4f;

public abstract class UtilScreenPose extends UtilScreenCore{
  public static final Matrix4 cleanMat4=new Matrix4();

  /**
   *
   * @return the current transform matrix
   */
  public Matrix4 matrix() {
    if(matrixStackPointer<0) return cleanMat4;
    return matrixStack[matrixStackPointer];
  }
  public void pushMatrix() {
    Matrix4 tmat=matrixStack[matrixStackPointer+1];
    Matrix4 matIn=matrixStackPointer<0?cleanMat4:matrixStack[matrixStackPointer];
    matrixStack[matrixStackPointer+1]=tmat==null?matIn.cpy():tmat.set(matIn);
    matrixStackPointer++;
    setTransformMatrix(matrix());
  }
  public void popMatrix() {
    matrixStackPointer--;
    setTransformMatrix(matrix());
  }
  public void clearMatrix() {
    matrixStackPointer=-1;
    // Arrays.fill(matrixStack,null);
    setProjectionMatrix(usedCamera.projection);
    setTransformMatrix(matrix());
  }
  public void copyMatrix(int i) {
    setTransformMatrix(matrix().set(matrixStack[i]));
  }
  public void copyMatrix(Matrix4 m) {
    setTransformMatrix(matrix().set(m));
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
    setTransformMatrix(matrix);
  }
  public void rotate(float rad) {
    Matrix4 matrix=matrix();
    matrix.rotate(0,0,1,UtilMath.deg(rad));
    setTransformMatrix(matrix);
  }
  public void scale(float in) {
    Matrix4 matrix=matrix();
    matrix.scale(in,in,in);
    setTransformMatrix(matrix);
  }

  //---------------------------------------------------------------------------

  public void translate(float dx,float dy,float dz) {
    Matrix4 matrix=matrix();
    matrix.translate(dx,dy,dz);
    setTransformMatrix(matrix);
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
    setTransformMatrix(matrix);
  }
  /** 没写完 */
  @Deprecated
  public void rotateDeg(float yaw,float pitch,float deg) {
    Matrix4 matrix=matrix();
    setTransformMatrix(matrix);
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
    setTransformMatrix(matrix);
  }

  public void rotate(float rx,float ry,float rz,float rw) {
    Matrix4 matrix=matrix();
    tq.set(rx,ry,rz,rw);
    matrix.rotate(tq);
    setTransformMatrix(matrix);
  }
  public void rotate(Quaternion q) {
    Matrix4 matrix=matrix();
    matrix.rotate(q);
    setTransformMatrix(matrix);
  }
  Quaternion tq=new Quaternion();
  public void rotate(Vec4f q) {
    Matrix4 matrix=matrix();
    tq.set(q.x,q.y,q.z,q.w);
    matrix.rotate(tq);
    setTransformMatrix(matrix);
  }
  public void rotateAxis(Vec3f v,float in) {
    Matrix4 matrix=matrix();
    matrix.rotate(v.x,v.y,v.z,UtilMath.deg(in));
    setTransformMatrix(matrix);
  }
  public void rotateAxis(float axisX,float axisY,float axisZ,float in) {
    Matrix4 matrix=matrix();
    matrix.rotate(axisX,axisY,axisZ,UtilMath.deg(in));
    setTransformMatrix(matrix);
  }
  public void rotateX(float in) {
    Matrix4 matrix=matrix();
    matrix.rotate(1,0,0,UtilMath.deg(in));
    setTransformMatrix(matrix);
  }
  public void rotateY(float in) {
    Matrix4 matrix=matrix();
    matrix.rotate(0,1,0,UtilMath.deg(in));
    setTransformMatrix(matrix);
  }
  public void rotateZ(float in) {
    Matrix4 matrix=matrix();
    matrix.rotate(0,0,1,UtilMath.deg(in));
    setTransformMatrix(matrix);
  }
  public void rotate(Vec3f vec) {
    rotate(vec.x,vec.y,vec.z);
  }

  public void rotateToCam(float x,float y,float z) {
    Vec3f up=GdxMath.up;
    rotateToCam(x,y,z,up.x,up.y,up.z);

  }
  public void rotateToCam(
    float x,float y,float z,
    float upX,float upY,float upZ) {
    var des=usedCamera.position;
    Vec4f v=GdxMath.rotateToFace(
      x,y,z,
      des.x,des.y,des.z,
      upX,upY,upZ);

    rotate(v.x,v.y,v.z,v.w);

  }
  public void scale(float sx,float sy,float sz) {
    Matrix4 matrix=matrix();
    matrix.scale(sx,sy,sz);
    setTransformMatrix(matrix);
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
