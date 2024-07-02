package pama1234.gdx.util.app;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.FlushablePool;

import pama1234.gdx.util.cam.CameraController;
import pama1234.gdx.util.cam.CameraController3D;

/**
 * 3D 工具屏幕类，继承自 UtilScreen。 提供了 3D 渲染和相机控制的基础功能。
 */
public abstract class UtilScreen3D extends UtilScreen{
  public CameraController3D cam3d;
  public CameraGroupStrategy cameraGroupStrategy;

  public Ray rayCache=new Ray();
  public Plane planeCache=new Plane();
  public Vector3 intersectionCache=new Vector3();

  {
    is3d=true;
  }

  @Override
  public void show() {
    preInit();
    init();
    postInit();
    setup();
  }

  @Override
  public void createRenderUtil() {
    super.createRenderUtil();
    cameraGroupStrategy=new CameraGroupStrategy(cam.camera);
    decalBatch=new DecalBatch(cameraGroupStrategy);
    modelBatch=new ModelBatch();

    modelPool=new FlushablePool<Model>() {
      @Override
      protected Model newObject() {
        return new Model();
      }

      @Override
      public Model obtain() {
        Model obtain=super.obtain();
        // TODO: 添加模型初始化逻辑
        return obtain;
      }
    };
    modelBuilder=new ModelBuilder();
  }

  @Override
  public CameraController createCamera() {
    return cam3d=new CameraController3D(this,0,0,0,1,0,
      Gdx.app.getType()==ApplicationType.Desktop?640:160);
  }

  @Override
  public void withCam() {
    setCamera(cam.camera);
    textScale(1);
    strokeWeight(defaultStrokeWeight=1);
  }

  /**
   * 将屏幕坐标转换为世界坐标。
   * 
   * @param x 屏幕坐标 x
   * @param y 屏幕坐标 y
   * @return 转换后的世界坐标
   */
  @Override
  public Vector3 screenToWorld(float x,float y) {
    Vector3 out=screenToWorld(x,y,0);
    if(out==null) {
      return vectorCache;
    }
    return out;
  }

  /**
   * 将屏幕坐标转换为世界坐标，指定 z 平面。
   * 
   * @param x      屏幕坐标 x
   * @param y      屏幕坐标 y
   * @param zPlain 指定的 z 平面
   * @return 转换后的世界坐标
   */
  public Vector3 screenToWorld(float x,float y,float zPlain) {
    vectorCache.set(x,y,0);
    cam.camera.unproject(vectorCache);
    var pos=cam3d.point.pos;
    rayCache.set(pos.x,pos.y,pos.z,vectorCache.x-pos.x,vectorCache.y-pos.y,vectorCache.z-pos.z);
    planeCache.set(0,0,zPlain+1,zPlain*(zPlain+1));
    if(Intersector.intersectRayPlane(rayCache,planeCache,intersectionCache)) {
      return intersectionCache;
    }else {
      return null;
    }
  }

  /**
   * 启用深度测试。
   */
  public void enableDepth() {
    Gdx.gl.glDepthMask(true);
    Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
    Gdx.gl.glClearDepthf(1f);
    Gdx.gl.glDepthFunc(GL20.GL_LESS);
  }

  /**
   * 添加一个 Decal 到批处理中。
   * 
   * @param in 要添加的 Decal
   */
  public void decal(Decal in) {
    decalBatch.add(in);
  }

  /**
   * 添加一个 Decal 到批处理中并立即刷新。
   * 
   * @param in 要添加的 Decal
   */
  public void decalFlush(Decal in) {
    decal(in);
    flushDecal();
  }

  /**
   * 刷新 Decal 批处理。
   */
  @Deprecated
  public void flushDecal() {
    decalBatch.flush();
  }

  @Override
  public void setProjectionMatrix(Matrix4 projection) {
    super.setProjectionMatrix(projection);
  }

  @Override
  public void dispose() {
    super.dispose();
    decalBatch.dispose();
  }
}
