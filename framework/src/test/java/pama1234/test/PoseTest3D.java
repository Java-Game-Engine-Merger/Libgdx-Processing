package pama1234.test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import org.junit.jupiter.api.Test;
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
    pose=new Pose3D(20,0,0,0, UtilMath.HALF_PI,0);
    noStroke();
  }

  @Override
  public void update() {

  }

  @Override
  public void display() {
  }

  @Override
  public void displayWithCam() {
    fill(0);
    pushMatrix();
    pose(poseO);
    scircle(0,0,3);
    popMatrix();

    fill(255,0,0);

    pushMatrix();
    pose(pose);
    scircle(0,0,3);
    popMatrix();

  }

  @Override
  public void frameResized() {

  }
}