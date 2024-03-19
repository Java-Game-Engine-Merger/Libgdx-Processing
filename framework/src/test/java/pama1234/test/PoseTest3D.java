package pama1234.test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import org.junit.jupiter.api.Test;
import pama1234.Tools;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.launcher.MainAppBase;
import pama1234.math.UtilMath;
import pama1234.math.transform.Pose3D;

import java.util.ArrayList;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

public class PoseTest3D extends UtilScreen3D{
  public static void main(String[] args) {
    test0001();
  }

  @Test
  public static void test0001() {
    MainAppBase mab=new MainAppBase() {
      {
        var classArray=new Class[] {PoseTest3D.class};
        screenClassList=new ArrayList<>(classArray.length);
        for(int i=0;i<classArray.length;i++) {
          screenClassList.add(i,classArray[i]);
        }
      }

    };
    new Lwjgl3Application(mab,getDefaultConfiguration(mab,"ShapeTest"));
  }
  Pose3D poseO,pose;
  @Override
  public void setup() {
    poseO=new Pose3D();
    //    pose=new Pose3D(20,0,0,0,UtilMath.HALF_PI,0);
    pose=new Pose3D(20,0,0);
    noStroke();
  }

  @Override
  public void update() {

  }

  @Override
  public void display() {
    text(
      Tools.cutToLastDigitString(UtilMath.deg(pose.rx()))+
        " "+
        Tools.cutToLastDigitString(UtilMath.deg(pose.ry()))+
        " "+
        Tools.cutToLastDigitString(UtilMath.deg(pose.rz())));
  }

  @Override
  public void displayWithCam() {
    fill(0);
    pushMatrix();
    pose(poseO);
    scircle(0,0,3);
    popMatrix();

    fill(255,127,0);

    pushMatrix();
    rotateToCam(pose);
    pose(pose);
    scircle(0,0,3);
    popMatrix();

  }

  @Override
  public void frameResized() {

  }

  Vector3 tmp=new Vector3(),tmp2=new Vector3(),dir=new Vector3(),tmp3=new Vector3();
  Quaternion rotation=new Quaternion();
  Vector3 up=new Vector3(0,1,0);
  Vector3 dirX=new Vector3(1,0,0).nor(),
    dirY=new Vector3(0,1,0),
    dirZ=new Vector3(0,0,1);
  public void rotateToCam(Pose3D pose) {
    var pos=cam3d.camera.position;
    //    var up=cam.up;

    //        Decal d=null;
    //        d.lookAt(cam.position,cam.up);

    //    var tmp=new Vector3();
    //    var tmp2=new Vector3();
    //    var dir=new Vector3();
    //    var rotation=new Quaternion();

    //    var tmp3=new Vector3(pose.pos.x,pose.pos.y,pose.pos.z);
    tmp3.set(
      pose.pos.x,
      pose.pos.y,
      pose.pos.z);

    dir.set(pos).sub(tmp3).nor();

    tmp.set(up).crs(dir).nor();
    tmp2.set(dir).crs(tmp).nor();

    rotation.setFromAxes(true,
      tmp.x,tmp2.x,dir.x,
      tmp.y,tmp2.y,dir.y,
      tmp.z,tmp2.z,dir.z);

    pose.rotate.set(rotation.x,rotation.y,rotation.z,rotation.w);

    //    pose.rotate.x=getAngleAround(dirX);
    //    pose.rotate.y=getAngleAround(dirY);
    //    pose.rotate.z=getAngleAround(dirZ);
    //    println(pose.rotate);
  }

  private float getAngleAround(Vector3 dir) {
    return rotation.getAngleAroundRad(dir);
  }
}