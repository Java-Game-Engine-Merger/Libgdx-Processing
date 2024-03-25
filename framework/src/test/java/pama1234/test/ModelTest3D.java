package pama1234.test;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import pama1234.Tools;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.launcher.MainAppBase;
import pama1234.math.transform.Pose3D;

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

    Texture texture=loadTexture("icon/icon16.png");
    TextureRegion tr=new TextureRegion(texture,0,0,64,64);

    ModelBuilder modelBuilder=new ModelBuilder();
    model=new Model[100];
    for(int i=0;i<model.length;i++) {
      //      model[i]=modelBuilder.createBox(5f,50f,5f,
      //        new Material(ColorAttribute.createDiffuse(colorFromInt(Tools.hsbColor((int)((float)i/model.length*255),255,255)))),
      //        Usage.Position|Usage.Normal);
      float w=50,h=50;

      model[i]=modelBuilder.createRect(
        0,0,0,
        0,h,0,
        w,h,0,
        w,0,0,

        0,0,0,

        //        new Material(ColorAttribute.createDiffuse(colorFromInt(Tools.hsbColor((int)((float)i/model.length*255),255,255)))),
        new Material(
          new BlendingAttribute(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA),
          ColorAttribute.createDiffuse(colorFromInt(Tools.hsbColor((int)((float)i/model.length*255),255,255))),
          TextureAttribute.createDiffuse(tr),
          IntAttribute.createCullFace(GL20.GL_NONE)),
        //          new DepthTestAttribute(false)),
        VertexAttributes.Usage.Position|VertexAttributes.Usage.TextureCoordinates);

      //      i++;

      //      model[i]=modelBuilder.createRect(
      //        w,h,0,
      //        0,h,0,
      //        0,0,0,
      //        w,0,0,
      //
      //        0,0,0,
      //
      //        new Material(
      //          ColorAttribute.createDiffuse(colorFromInt(Tools.hsbColor((int)((float)i/model.length*255),255,255))),
      //          IntAttribute.createCullFace(GL20.GL_NONE),
      //          new DepthTestAttribute(false)),
      //        VertexAttributes.Usage.Position);
    }
    instance=new ModelInstance[model.length];
    int range=50;
    for(int i=0;i<model.length;i++) {
      ModelInstance e=new ModelInstance(model[i]);
      //      i++;
      //      ModelInstance e2=new ModelInstance(model[i]);
      e.transform.translate(random(-range,range),random(-range,range),random(-range,range));
      e.transform.rotate(0,0,1,random(-180,180));
      e.transform.rotate(0,1,0,random(-180,180));
      e.transform.rotate(1,0,0,random(-180,180));
      //      e2.transform.set(e.transform);
      instance[i]=e;
      //      instance[i]=e2;
    }
    //new Renderable();
    var a=imageBatch;
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
      ModelInstance mi=instance[i];
      Array<Renderable> array=new Array<>();
      Pool<Renderable> pool=new Pool<Renderable>() {
        @Override
        protected Renderable newObject() {
          return new Renderable();
        }

        @Override
        public Renderable obtain() {
          Renderable renderable=super.obtain();
          renderable.environment=null;
          renderable.material=null;
          renderable.meshPart.set("",null,0,0,0);
          renderable.shader=null;
          renderable.userData=null;
          return renderable;
        }

      };
      mi.getRenderables(array,pool);
      Renderable rr=array.get(0);
      //      System.out.println("bones: "+rr.bones);
      //      System.out.println("environment: "+rr.environment);

      //      System.out.println("material: "+rr.material);
      //      System.out.println("material id and size: "+rr.material.id+" "+rr.material.size());
      //      System.out.println("material getMask: "+Long.toHexString(rr.material.getMask()));
      //      System.out.println("material Attribute: "+rr.material.get(0));
      //
      //      System.out.println("meshPart: "+rr.meshPart);
      //      System.out.println("meshPart id: "+rr.meshPart.id);
      //      System.out.println("meshPart mesh: "+rr.meshPart.mesh);
      System.out.println("meshPart mesh getVertexAttributes: "+rr.meshPart.mesh.getVertexAttributes());
      System.out.println("meshPart mesh getVertexSize: "+rr.meshPart.mesh.getVertexSize());
      System.out.println("meshPart mesh getNumVertices: "+rr.meshPart.mesh.getNumVertices());
      System.out.println("meshPart mesh getVertices: "+Arrays.toString(rr.meshPart.mesh.getVertices(new float[60])));
      short[] sa;
      rr.meshPart.mesh.getIndices(sa=new short[rr.meshPart.mesh.getNumIndices()]);
      System.out.println("meshPart mesh getIndices: "+Arrays.toString(sa));
      //      System.out.println("meshPart offset: "+rr.meshPart.offset);
      //      System.out.println("meshPart size: "+rr.meshPart.size);
      //      System.out.println("meshPart primitiveType: "+Integer.toHexString(rr.meshPart.primitiveType));

      //      System.out.println("shader: "+rr.shader);
      //      System.out.println("userData: "+rr.userData);
      //      System.out.println("worldTransform: "+rr.worldTransform);
      System.out.println();
      model(mi);
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