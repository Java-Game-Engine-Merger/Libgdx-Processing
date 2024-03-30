package pama1234.gdx.util.tools;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import pama1234.math.vec.Vec3f;
import pama1234.math.vec.Vec4f;

public class GdxMath{
  public static Vector3 tmp=new Vector3(),tmp2=new Vector3(),dir=new Vector3(),tmp3=new Vector3();
  public static Quaternion rotation=new Quaternion();
  public static Vec3f up=new Vec3f(0,1,0);

  public static Vec4f outputCache=new Vec4f();
  public static Vec4f rotateToFace(Vec3f pos,Vec3f des) {
    return rotateToFace(pos,des,up);
  }

  public static Vec4f rotateToFace(
          float posX,float posY,float posZ,
          float desX,float desY,float desZ) {
    return rotateToFace(posX,posY,posZ,desX,desY,desZ,up.x,up.y,up.z);
  }

  public static Vec4f rotateToFace(
          float posX,float posY,float posZ,
          float desX,float desY,float desZ,
          float upX,float upY,float upZ) {

    tmp3.set(-posX,-posY,-posZ);

    dir.set(-desX,-desY,-desZ).sub(tmp3).nor();

    tmp.set(upX,upY,upZ).crs(dir).nor();
    tmp2.set(dir).crs(tmp).nor();

    rotation.setFromAxes(true,
            tmp.x,tmp2.x,dir.x,
            tmp.y,tmp2.y,dir.y,
            tmp.z,tmp2.z,dir.z);

    outputCache.set(rotation.x,rotation.y,rotation.z,rotation.w);
    return outputCache;
  }

  public static Vec4f rotateToFace(Vec3f pos,Vec3f des,Vec3f upIn) {

    tmp3.set(-pos.x,-pos.y,-pos.z);

    dir.set(-des.x,-des.y,-des.z).sub(tmp3).nor();

    tmp.set(upIn.x,upIn.y,upIn.z).crs(dir).nor();
    tmp2.set(dir).crs(tmp).nor();

    rotation.setFromAxes(true,
      tmp.x,tmp2.x,dir.x,
      tmp.y,tmp2.y,dir.y,
      tmp.z,tmp2.z,dir.z);

    outputCache.set(rotation.x,rotation.y,rotation.z,rotation.w);
    return outputCache;
  }

  public static Vec4f rotateToFace02(Vec3f pos,Vec3f des,Vec3f upIn) {
    tmp3.set(pos.x,pos.y,pos.z);

    dir.set(des.x,des.y,des.z).sub(tmp3).nor();

    tmp.set(upIn.x,upIn.y,upIn.z).crs(dir).nor();
    tmp2.set(dir).crs(tmp).nor();

    rotation.setFromAxes(true,
      tmp.x,tmp2.x,dir.x,
      tmp.y,tmp2.y,dir.y,
      tmp.z,tmp2.z,dir.z);

    outputCache.set(rotation.x,rotation.y,rotation.z,rotation.w);
    return outputCache;
  }
}
