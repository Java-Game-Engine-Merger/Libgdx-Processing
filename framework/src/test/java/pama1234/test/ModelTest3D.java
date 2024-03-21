package pama1234.test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
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

public class ModelTest3D extends UtilScreen3D{
  public static void main(String[] args) {
    test0001();
  }

  @Test
  public static void test0001() {
    MainAppBase mab=new MainAppBase() {
      {
        var classArray=new Class[] {ModelTest3D.class};
        screenClassList=new ArrayList<>(classArray.length);
        for(int i=0;i<classArray.length;i++) {
          screenClassList.add(i,classArray[i]);
        }
      }

    };
    new Lwjgl3Application(mab,getDefaultConfiguration(mab,"ShapeTest"));
  }

  public Model[] model;
  public ModelInstance[] instance;

  @Override
  public void setup() {
    noStroke();

    ModelBuilder modelBuilder=new ModelBuilder();
    model=new Model[100];
    for(int i=0;i<model.length;i++) {
      model[i]=modelBuilder.createBox(5f,50f,5f,
        new Material(ColorAttribute.createDiffuse(colorFromInt(Tools.hsbColor((int)((float)i/model.length*255),255,255)))),
        Usage.Position|Usage.Normal);
    }
    instance=new ModelInstance[model.length];
    int range=50;
    for(int i=0;i<model.length;i++) {
      ModelInstance e=new ModelInstance(model[i]);
      e.transform.translate(random(-range,range),random(-range,range),random(-range,range));
      e.transform.rotate(0,0,1,random(-180,180));
      e.transform.rotate(0,1,0,random(-180,180));
      e.transform.rotate(1,0,0,random(-180,180));
      instance[i]=e;
    }

  }

  @Override
  public void update() {

  }

  @Override
  public void display() {
    //    modelBatch.begin(cam);
    //    modelBatch.render(instance);
    //    modelBatch.end();

  }

  @Override
  public void displayWithCam() {

    for(int i=0;i<instance.length;i++) {
      model(instance[i]);
    }
  }

  @Override
  public void frameResized() {

  }

  @Override
  public void dispose() {
    super.dispose();

    for(int i=0;i<model.length;i++) {
      model[i].dispose();
    }
  }

  Vector3 tmp=new Vector3(),tmp2=new Vector3(),dir=new Vector3(),tmp3=new Vector3();
  Quaternion rotation=new Quaternion();
  Vector3 up=new Vector3(0,1,0);
  Vector3 dirX=new Vector3(1,0,0).nor(),
    dirY=new Vector3(0,1,0),
    dirZ=new Vector3(0,0,1);
  public void rotateToCam(Pose3D pose) {
    var pos=cam3d.camera.position;
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

  }

  private float getAngleAround(Vector3 dir) {
    return rotation.getAngleAroundRad(dir);
  }
}