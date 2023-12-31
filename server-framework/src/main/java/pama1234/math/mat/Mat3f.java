package pama1234.math.mat;

import pama1234.math.UtilMath;
import pama1234.math.gdx.temp.ServerAffine2;
import pama1234.math.vec.Vec2f;
import pama1234.math.vec.Vec3f;

/**
 * 直接从libgdx复制过来的
 */
public class Mat3f{
  public static final int M00=0;
  public static final int M01=3;
  public static final int M02=6;
  public static final int M10=1;
  public static final int M11=4;
  public static final int M12=7;
  public static final int M20=2;
  public static final int M21=5;
  public static final int M22=8;
  public float[] val=new float[9];
  private float[] tmp=new float[9];
  public Mat3f() {
    idt();
  }
  public Mat3f(Mat3f matrix) {
    set(matrix);
  }
  public Mat3f(float[] values) {
    this.set(values);
  }
  public Mat3f idt() {
    float[] val=this.val;
    val[M00]=1;
    val[M10]=0;
    val[M20]=0;
    val[M01]=0;
    val[M11]=1;
    val[M21]=0;
    val[M02]=0;
    val[M12]=0;
    val[M22]=1;
    return this;
  }
  public Mat3f mul(Mat3f m) {
    float[] val=this.val;
    float v00=val[M00]*m.val[M00]+val[M01]*m.val[M10]+val[M02]*m.val[M20];
    float v01=val[M00]*m.val[M01]+val[M01]*m.val[M11]+val[M02]*m.val[M21];
    float v02=val[M00]*m.val[M02]+val[M01]*m.val[M12]+val[M02]*m.val[M22];
    float v10=val[M10]*m.val[M00]+val[M11]*m.val[M10]+val[M12]*m.val[M20];
    float v11=val[M10]*m.val[M01]+val[M11]*m.val[M11]+val[M12]*m.val[M21];
    float v12=val[M10]*m.val[M02]+val[M11]*m.val[M12]+val[M12]*m.val[M22];
    float v20=val[M20]*m.val[M00]+val[M21]*m.val[M10]+val[M22]*m.val[M20];
    float v21=val[M20]*m.val[M01]+val[M21]*m.val[M11]+val[M22]*m.val[M21];
    float v22=val[M20]*m.val[M02]+val[M21]*m.val[M12]+val[M22]*m.val[M22];
    val[M00]=v00;
    val[M10]=v10;
    val[M20]=v20;
    val[M01]=v01;
    val[M11]=v11;
    val[M21]=v21;
    val[M02]=v02;
    val[M12]=v12;
    val[M22]=v22;
    return this;
  }
  public Mat3f mulLeft(Mat3f m) {
    float[] val=this.val;
    float v00=m.val[M00]*val[M00]+m.val[M01]*val[M10]+m.val[M02]*val[M20];
    float v01=m.val[M00]*val[M01]+m.val[M01]*val[M11]+m.val[M02]*val[M21];
    float v02=m.val[M00]*val[M02]+m.val[M01]*val[M12]+m.val[M02]*val[M22];
    float v10=m.val[M10]*val[M00]+m.val[M11]*val[M10]+m.val[M12]*val[M20];
    float v11=m.val[M10]*val[M01]+m.val[M11]*val[M11]+m.val[M12]*val[M21];
    float v12=m.val[M10]*val[M02]+m.val[M11]*val[M12]+m.val[M12]*val[M22];
    float v20=m.val[M20]*val[M00]+m.val[M21]*val[M10]+m.val[M22]*val[M20];
    float v21=m.val[M20]*val[M01]+m.val[M21]*val[M11]+m.val[M22]*val[M21];
    float v22=m.val[M20]*val[M02]+m.val[M21]*val[M12]+m.val[M22]*val[M22];
    val[M00]=v00;
    val[M10]=v10;
    val[M20]=v20;
    val[M01]=v01;
    val[M11]=v11;
    val[M21]=v21;
    val[M02]=v02;
    val[M12]=v12;
    val[M22]=v22;
    return this;
  }
  public Mat3f setToRotation(float degrees) {
    return setToRotationRad(UtilMath.degRad*degrees);
  }
  public Mat3f setToRotationRad(float radians) {
    float cos=(float)Math.cos(radians);
    float sin=(float)Math.sin(radians);
    float[] val=this.val;
    val[M00]=cos;
    val[M10]=sin;
    val[M20]=0;
    val[M01]=-sin;
    val[M11]=cos;
    val[M21]=0;
    val[M02]=0;
    val[M12]=0;
    val[M22]=1;
    return this;
  }
  public Mat3f setToRotation(Vec3f axis,float degrees) {
    return setToRotation(axis,UtilMath.cosDeg(degrees),UtilMath.sinDeg(degrees));
  }
  public Mat3f setToRotation(Vec3f axis,float cos,float sin) {
    float[] val=this.val;
    float oc=1.0f-cos;
    val[M00]=oc*axis.x*axis.x+cos;
    val[M01]=oc*axis.x*axis.y-axis.z*sin;
    val[M02]=oc*axis.z*axis.x+axis.y*sin;
    val[M10]=oc*axis.x*axis.y+axis.z*sin;
    val[M11]=oc*axis.y*axis.y+cos;
    val[M12]=oc*axis.y*axis.z-axis.x*sin;
    val[M20]=oc*axis.z*axis.x-axis.y*sin;
    val[M21]=oc*axis.y*axis.z+axis.x*sin;
    val[M22]=oc*axis.z*axis.z+cos;
    return this;
  }
  public Mat3f setToTranslation(float x,float y) {
    float[] val=this.val;
    val[M00]=1;
    val[M10]=0;
    val[M20]=0;
    val[M01]=0;
    val[M11]=1;
    val[M21]=0;
    val[M02]=x;
    val[M12]=y;
    val[M22]=1;
    return this;
  }
  public Mat3f setToTranslation(Vec2f translation) {
    float[] val=this.val;
    val[M00]=1;
    val[M10]=0;
    val[M20]=0;
    val[M01]=0;
    val[M11]=1;
    val[M21]=0;
    val[M02]=translation.x;
    val[M12]=translation.y;
    val[M22]=1;
    return this;
  }
  public Mat3f setToScaling(float scaleX,float scaleY) {
    float[] val=this.val;
    val[M00]=scaleX;
    val[M10]=0;
    val[M20]=0;
    val[M01]=0;
    val[M11]=scaleY;
    val[M21]=0;
    val[M02]=0;
    val[M12]=0;
    val[M22]=1;
    return this;
  }
  public Mat3f setToScaling(Vec2f scale) {
    float[] val=this.val;
    val[M00]=scale.x;
    val[M10]=0;
    val[M20]=0;
    val[M01]=0;
    val[M11]=scale.y;
    val[M21]=0;
    val[M02]=0;
    val[M12]=0;
    val[M22]=1;
    return this;
  }
  public String toString() {
    float[] val=this.val;
    return "["+val[M00]+"|"+val[M01]+"|"+val[M02]+"]\n"+"["+val[M10]+"|"+val[M11]+"|"+val[M12]+"]\n"+"["+val[M20]+"|"+val[M21]+"|"+val[M22]+"]";
  }
  public float det() {
    float[] val=this.val;
    return val[M00]*val[M11]*val[M22]+val[M01]*val[M12]*val[M20]+val[M02]*val[M10]*val[M21]
      -val[M00]*val[M12]*val[M21]-val[M01]*val[M10]*val[M22]-val[M02]*val[M11]*val[M20];
  }
  public Mat3f inv() {
    float det=det();
    if(det==0) throw new RuntimeException("Can't invert a singular matrix");
    float inv_det=1.0f/det;
    float[] tmp=this.tmp,val=this.val;
    tmp[M00]=val[M11]*val[M22]-val[M21]*val[M12];
    tmp[M10]=val[M20]*val[M12]-val[M10]*val[M22];
    tmp[M20]=val[M10]*val[M21]-val[M20]*val[M11];
    tmp[M01]=val[M21]*val[M02]-val[M01]*val[M22];
    tmp[M11]=val[M00]*val[M22]-val[M20]*val[M02];
    tmp[M21]=val[M20]*val[M01]-val[M00]*val[M21];
    tmp[M02]=val[M01]*val[M12]-val[M11]*val[M02];
    tmp[M12]=val[M10]*val[M02]-val[M00]*val[M12];
    tmp[M22]=val[M00]*val[M11]-val[M10]*val[M01];
    val[M00]=inv_det*tmp[M00];
    val[M10]=inv_det*tmp[M10];
    val[M20]=inv_det*tmp[M20];
    val[M01]=inv_det*tmp[M01];
    val[M11]=inv_det*tmp[M11];
    val[M21]=inv_det*tmp[M21];
    val[M02]=inv_det*tmp[M02];
    val[M12]=inv_det*tmp[M12];
    val[M22]=inv_det*tmp[M22];
    return this;
  }
  public Mat3f set(Mat3f mat) {
    System.arraycopy(mat.val,0,val,0,val.length);
    return this;
  }
  public Mat3f set(ServerAffine2 affine) {
    float[] val=this.val;
    val[M00]=affine.m00;
    val[M10]=affine.m10;
    val[M20]=0;
    val[M01]=affine.m01;
    val[M11]=affine.m11;
    val[M21]=0;
    val[M02]=affine.m02;
    val[M12]=affine.m12;
    val[M22]=1;
    return this;
  }
  public Mat3f set(Mat4f mat) {
    float[] val=this.val;
    val[M00]=mat.val[Mat4f.M00];
    val[M10]=mat.val[Mat4f.M10];
    val[M20]=mat.val[Mat4f.M20];
    val[M01]=mat.val[Mat4f.M01];
    val[M11]=mat.val[Mat4f.M11];
    val[M21]=mat.val[Mat4f.M21];
    val[M02]=mat.val[Mat4f.M02];
    val[M12]=mat.val[Mat4f.M12];
    val[M22]=mat.val[Mat4f.M22];
    return this;
  }
  public Mat3f set(float[] values) {
    System.arraycopy(values,0,val,0,val.length);
    return this;
  }
  public Mat3f trn(Vec2f vector) {
    val[M02]+=vector.x;
    val[M12]+=vector.y;
    return this;
  }
  public Mat3f trn(float x,float y) {
    val[M02]+=x;
    val[M12]+=y;
    return this;
  }
  public Mat3f trn(Vec3f vector) {
    val[M02]+=vector.x;
    val[M12]+=vector.y;
    return this;
  }
  public Mat3f translate(float x,float y) {
    float[] val=this.val;
    tmp[M00]=1;
    tmp[M10]=0;
    tmp[M20]=0;
    tmp[M01]=0;
    tmp[M11]=1;
    tmp[M21]=0;
    tmp[M02]=x;
    tmp[M12]=y;
    tmp[M22]=1;
    mul(val,tmp);
    return this;
  }
  public Mat3f translate(Vec2f translation) {
    float[] val=this.val;
    tmp[M00]=1;
    tmp[M10]=0;
    tmp[M20]=0;
    tmp[M01]=0;
    tmp[M11]=1;
    tmp[M21]=0;
    tmp[M02]=translation.x;
    tmp[M12]=translation.y;
    tmp[M22]=1;
    mul(val,tmp);
    return this;
  }
  public Mat3f rotate(float degrees) {
    return rotateRad(UtilMath.degRad*degrees);
  }
  public Mat3f rotateRad(float radians) {
    if(radians==0) return this;
    float cos=(float)Math.cos(radians);
    float sin=(float)Math.sin(radians);
    float[] tmp=this.tmp;
    tmp[M00]=cos;
    tmp[M10]=sin;
    tmp[M20]=0;
    tmp[M01]=-sin;
    tmp[M11]=cos;
    tmp[M21]=0;
    tmp[M02]=0;
    tmp[M12]=0;
    tmp[M22]=1;
    mul(val,tmp);
    return this;
  }
  public Mat3f scale(float scaleX,float scaleY) {
    float[] tmp=this.tmp;
    tmp[M00]=scaleX;
    tmp[M10]=0;
    tmp[M20]=0;
    tmp[M01]=0;
    tmp[M11]=scaleY;
    tmp[M21]=0;
    tmp[M02]=0;
    tmp[M12]=0;
    tmp[M22]=1;
    mul(val,tmp);
    return this;
  }
  public Mat3f scale(Vec2f scale) {
    float[] tmp=this.tmp;
    tmp[M00]=scale.x;
    tmp[M10]=0;
    tmp[M20]=0;
    tmp[M01]=0;
    tmp[M11]=scale.y;
    tmp[M21]=0;
    tmp[M02]=0;
    tmp[M12]=0;
    tmp[M22]=1;
    mul(val,tmp);
    return this;
  }
  public float[] getValues() {
    return val;
  }
  public Vec2f getTranslation(Vec2f position) {
    position.x=val[M02];
    position.y=val[M12];
    return position;
  }
  public Vec2f getScale(Vec2f scale) {
    float[] val=this.val;
    scale.x=(float)Math.sqrt(val[M00]*val[M00]+val[M01]*val[M01]);
    scale.y=(float)Math.sqrt(val[M10]*val[M10]+val[M11]*val[M11]);
    return scale;
  }
  public float getRotation() {
    return UtilMath.radDeg*(float)Math.atan2(val[M10],val[M00]);
  }
  public float getRotationRad() {
    return (float)Math.atan2(val[M10],val[M00]);
  }
  public Mat3f scl(float scale) {
    val[M00]*=scale;
    val[M11]*=scale;
    return this;
  }
  public Mat3f scl(Vec2f scale) {
    val[M00]*=scale.x;
    val[M11]*=scale.y;
    return this;
  }
  public Mat3f scl(Vec3f scale) {
    val[M00]*=scale.x;
    val[M11]*=scale.y;
    return this;
  }
  public Mat3f transpose() {
    float[] val=this.val;
    float v01=val[M10];
    float v02=val[M20];
    float v10=val[M01];
    float v12=val[M21];
    float v20=val[M02];
    float v21=val[M12];
    val[M01]=v01;
    val[M02]=v02;
    val[M10]=v10;
    val[M12]=v12;
    val[M20]=v20;
    val[M21]=v21;
    return this;
  }
  private static void mul(float[] mata,float[] matb) {
    float v00=mata[M00]*matb[M00]+mata[M01]*matb[M10]+mata[M02]*matb[M20];
    float v01=mata[M00]*matb[M01]+mata[M01]*matb[M11]+mata[M02]*matb[M21];
    float v02=mata[M00]*matb[M02]+mata[M01]*matb[M12]+mata[M02]*matb[M22];
    float v10=mata[M10]*matb[M00]+mata[M11]*matb[M10]+mata[M12]*matb[M20];
    float v11=mata[M10]*matb[M01]+mata[M11]*matb[M11]+mata[M12]*matb[M21];
    float v12=mata[M10]*matb[M02]+mata[M11]*matb[M12]+mata[M12]*matb[M22];
    float v20=mata[M20]*matb[M00]+mata[M21]*matb[M10]+mata[M22]*matb[M20];
    float v21=mata[M20]*matb[M01]+mata[M21]*matb[M11]+mata[M22]*matb[M21];
    float v22=mata[M20]*matb[M02]+mata[M21]*matb[M12]+mata[M22]*matb[M22];
    mata[M00]=v00;
    mata[M10]=v10;
    mata[M20]=v20;
    mata[M01]=v01;
    mata[M11]=v11;
    mata[M21]=v21;
    mata[M02]=v02;
    mata[M12]=v12;
    mata[M22]=v22;
  }
}
