package pama1234.gdx.util.app;

import static pama1234.math.UtilMath.*;

import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.FlushablePool;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.Viewport;

import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;
import hhs.gdx.hslib.tools.LoopThread;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.cam.CameraController;
import pama1234.gdx.util.element.FontStyle;
import pama1234.gdx.util.font.BetterBitmapFont;
import pama1234.gdx.util.graphics.RendererWrapper;
import pama1234.gdx.util.graphics.UtilPolygonSpriteBatch;
import pama1234.gdx.util.graphics.UtilShapeRenderer;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.input.UtilInputProcesser;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.listener.EntityNeoListener;
import pama1234.gdx.util.listener.InputListener;
import pama1234.gdx.util.listener.SystemListener;
import pama1234.gdx.util.p3d.SpriteBatch3D;
import pama1234.gdx.util.wrapper.AutoEntityManager;
import pama1234.gdx.util.wrapper.DisplayEntity.DisplayWithCam;
import pama1234.gdx.util.wrapper.EntityCenterAbstract;
import pama1234.gdx.util.wrapper.EntityNeoCenter;
import pama1234.util.Annotations.SyntacticSugar;
import pama1234.util.UtilServer;
import pama1234.util.listener.LifecycleListener;
import pama1234.util.listener.ServerEntityListener;
import pama1234.util.wrapper.Center;
import pama1234.util.wrapper.ServerEntityCenter;
import space.earlygrey.shapedrawer.CapType;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * UtilScreen 太大了，因此抽离了一部分内容到此类，抽离的规则未确定
 * 
 * @see UtilScreen
 */
public abstract class UtilScreenCore implements Screen,InputListener,LifecycleListener,SystemListener{
  public final float fontGridSize=4;
  public boolean flip=true;
  public int width,height;
  public int frameCount;
  public float frameRate;
  /** 当屏幕刷新率不为60或其他情况时，使用新的线程来进行游戏的刷新，而游戏的渲染使用主线程 */
  public boolean threadedUpdate;
  public LoopThread updateThread;

  //---------------------------------------------------------------------------

  public boolean mouseMoved;
  public MouseInfo mouse;
  public Vector3 vectorCache;
  public int touchCount;
  public TouchInfo[] touches=new TouchInfo[16];
  public boolean grabCursor;

  //---------------------------------------------------------------------------

  public boolean is3d;
  public boolean depth;
  public FlushablePool<Model> modelPool;
  public ModelBuilder modelBuilder;

  public DecalBatch decalBatch;
  public ModelBatch modelBatch;

  //---------------------------------------------------------------------------

  public boolean keyPressed;
  /** normally "a" and "A" will be treat as 'A' */
  public char key;
  /** see {@link com.badlogic.gdx.Input.Keys gdx.Input.Keys} for keyCodes */
  public int keyCode;
  public boolean shift,ctrl,alt;
  public IntArray keyPressedArray;

  public boolean focus;

  public CameraController cam;
  public OrthographicCamera screenCam;
  public Camera usedCamera;
  /** 一般来说，这是一个SpriteBatch */
  public Batch imageBatch,imageBatchDefault;
  public TinyVGShapeDrawer tvgDrawer;
  public BetterBitmapFont font;

  // UtilShapeRenderer or SpriteBatch
  //TODO currentRendererType not used
  public int currentRendererType;
  public Object usedRenderer;
  public RendererWrapper rendererWrapper;

  //---------------------------------------------------------------------------

  public FontStyle fontStyle=new FontStyle();
  public Color textColor,fillColor,strokeColor;
  public boolean fill=true,stroke=true;

  public float defaultStrokeWeight,strokeWeight;
  public JoinType joinType=JoinType.POINTY;
  public CapType capType=CapType.ROUND;

  public ShapeDrawer shapeDrawer,shapeDrawerDefault;
  public UtilPolygonSpriteBatch pFill;

  public static ShapeDrawer shapeDrawer3d;
  public static SpriteBatch3D batch3d;

  public boolean background=true;
  public Color backgroundColor;

  /** {@link UtilScreenCore#centerScreen} 加上 {@link DisplayWithCam} */
  public EntityNeoCenter<UtilScreen,EntityNeoListener> centerNeo;
  /** 类似center但是存放的是ServerEntityListener */
  public ServerEntityCenter<UtilServer,ServerEntityListener> serverCenter;

  /**
   * 仅会执行存放在list中的所有实体的update方法和监听事件，不会执行display方法
   *
   * @webref UtilScreen:center
   * @webBrief EntityCenter containing EntityListeners
   */
  public EntityCenterAbstract<UtilScreen,EntityListener,?> center;
  /** 执行update和display方法，以相机视角为坐标变幻标准 */
  public EntityCenterAbstract<UtilScreen,EntityListener,?> centerCam;
  /** 执行update和display方法，以屏幕为坐标变幻标准 */
  public EntityCenterAbstract<UtilScreen,EntityListener,?> centerScreen;

  /** 自动注册和删除实体 */
  public AutoEntityManager<UtilScreen> auto;
  /** 监听系统事件 */
  public Center<SystemListener> centerSys;
  public UtilInputProcesser inputProcessor;
  public Random rng=new Random();
  /**
   * 按钮和其他UI的基本单位长度
   */
  public float u;
  /**
   * 文本的大小
   */
  public float pu;
  /**
   * 文本的放大倍数，为了清晰显示因此只能是整数
   */
  // TODO 电脑端Android调试模式下无法在init环节初始化pus值，调整执行顺序
  public int pus=1;
  public boolean stop;

  //---------------------------------------------------------------------------

  public boolean isAndroid=Gdx.app.getType()==ApplicationType.Android;

  //---------------------------------------------------------------------------

  public Matrix4[] matrixStack=new Matrix4[10];
  public int matrixStackPointer=-1;

  //---------------------------------------------------------------------------

  public Stage screenStage,camStage;
  public Viewport screenViewport,camViewport;

  public float multDist=1;
  public Button<?>[] buttons;
  public TextButton<?>[] textButtons;
  /** button unit */
  public int bu;
  // TODO
  public boolean fullSettings;

  //---------------------------------------------------------------------------

  /**
   * 检查指定的键是否被按下
   * 
   * @param in 键码
   * @return 如果键被按下则返回 true，否则返回 false
   */
  public boolean isKeyPressed(int in) {
    return keyPressedArray.contains(in);
  }

  /**
   * 开始绘制形状
   */
  public void beginShape() {
    // rFill.begin(ShapeType.Filled);
    // rStroke.begin(ShapeType.Line);
  }

  /**
   * 结束绘制形状
   */
  public void endShape() {
    endRenderer(); // TODO
    // rFill.end();
    // rStroke.end();
  }

  //---------------------------------------------------------------------------

  /**
   * 开始混合模式
   */
  public void beginBlend() {
    Gdx.gl.glEnable(GL20.GL_BLEND);
    // Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
    // Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
  }

  /**
   * 结束混合模式
   */
  public void endBlend() {
    Gdx.gl.glDisable(GL20.GL_BLEND);
  }

  /**
   * 切换到指定的渲染器
   * 
   * @param renderer 渲染器对象
   */
  public void renderer(Object renderer,RendererWrapper wrapperIn) {
    boolean flag=wrapperIn==null;
    if(!flag&&rendererWrapper==wrapperIn) return;
    if(flag&&usedRenderer==renderer) return;

    if(usedRenderer!=null) {
      if(usedRenderer instanceof Batch batch&&batch.isDrawing()) {
        batch.end();
      }else if(usedRenderer instanceof ModelBatch mb) {
        mb.end();
      }else if(usedRenderer instanceof UtilShapeRenderer r) {
        if(r.isDrawing()) r.flush();
      }
      if(rendererWrapper!=null) rendererWrapper.to();
    }
    usedRenderer=renderer;
    rendererWrapper=wrapperIn;

    if(usedRenderer!=null) {
      if(rendererWrapper!=null) rendererWrapper.from();
      if(usedRenderer instanceof Batch batch) {
        batch.begin();
      }else if(usedRenderer instanceof ModelBatch mb) {
        mb.begin(usedCamera);
      }else if(usedRenderer instanceof UtilShapeRenderer r) {
        beginBlend();
      }
    }
  }

  @SyntacticSugar
  public void renderer(Object renderer) {
    renderer(renderer,null);
  }

  /**
   * 结束当前渲染器
   */
  public void endRenderer() {
    renderer(null);
  }

  /**
   * 设置相机
   * 
   * @param in 相机对象
   */
  public void setCamera(Camera in) {
    if(usedCamera!=in) usedCamera=in;
    else return;
    setProjectionMatrix(in.combined);
  }

  /**
   * 设置投影矩阵
   * 
   * @param projection 投影矩阵
   */
  public void setProjectionMatrix(Matrix4 projection) {
    imageBatch.setProjectionMatrix(projection);
    pFill.setProjectionMatrix(projection);
  }

  /**
   * 设置变换矩阵
   * 
   * @param transform 变换矩阵
   */
  public void setTransformMatrix(Matrix4 transform) {
    imageBatch.setTransformMatrix(transform);
    pFill.setTransformMatrix(transform);
  }

  @Override
  public void init() {}

  /**
   * 初始化设置
   */
  public abstract void setup();

  /**
   * 更新逻辑
   */
  public abstract void update();

  /**
   * 渲染显示
   */
  public abstract void display();

  /**
   * 使用相机渲染显示
   */
  public abstract void displayWithCam();

  /**
   * 处理窗口大小调整
   */
  public abstract void frameResized();

  /**
   * 将屏幕坐标转换为世界坐标
   * 
   * @param x 屏幕X坐标
   * @param y 屏幕Y坐标
   * @return 世界坐标
   */
  public abstract Vector3 screenToWorld(float x,float y);

  @Override
  public void resize(int w,int h) {
    innerResize(w,h);
    frameResized();
  }

  /**
   * 内部处理窗口大小调整
   * 
   * @param w 宽度
   * @param h 高度
   */
  public void innerResize(int w,int h) {
    width=w;
    height=h;
    if(isAndroid) u=min(w,h)/8f;
    else u=min(w,h)/16f;
    pus=max(1,floor(u/16f));
    pu=pus*16;
    cam.preResizeEvent(w,h);
    screenCam.setToOrtho(flip,w,h);

    center.frameResized(w,h);
  }

  @Override
  public void pause() {
    center.pause();
  }

  @Override
  public void resume() {
    center.resume();
    serverCenter.resume();
  }

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stop=true;
    center.dispose();
    serverCenter.dispose();
    if(threadedUpdate) updateThread.stop=true;
  }

  //---------------------------------------------------------------------------

  /**
   * 加载纹理
   * 
   * @param in 纹理文件路径
   * @return 纹理对象
   */
  @Deprecated
  public Texture loadTexture(String in) {
    return new Texture(Gdx.files.internal(in));
  }

  /**
   * 线程休眠
   * 
   * @param i 休眠时间（毫秒）
   */
  public void sleep(long i) {
    try {
      Thread.sleep(i);
    }catch(InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 生成随机数
   * 
   * @param max 最大值
   * @return 随机数
   */
  public float random(float max) {
    return rng.nextFloat()*max;
  }

  /**
   * 生成随机数
   * 
   * @param min 最小值
   * @param max 最大值
   * @return 随机数
   */
  public float random(float min,float max) {
    max-=min;
    return rng.nextFloat()*max+min;
  }

  /**
   * 打印字符串
   * 
   * @param in 字符串
   */
  public void println(String in) {
    Gdx.app.log("print S",in);
  }

  /**
   * 打印整数
   * 
   * @param in 整数
   */
  public void println(int in) {
    Gdx.app.log("print I",Integer.toString(in));
  }

  /**
   * 打印浮点数
   * 
   * @param in 浮点数
   */
  public void println(float in) {
    Gdx.app.log("print F",Float.toString(in));
  }

  /**
   * 打印多个对象
   * 
   * @param ins 对象数组
   */
  public void println(Object... ins) {
    StringBuilder sb=new StringBuilder();
    for(Object i:ins) sb.append(i).append(" ");
    Gdx.app.log("print[A",sb.toString());
  }

  /**
   * 调试输出
   * 
   * @param <T> 输出类型
   * @param out 输出对象
   * @return 输出对象
   */
  public <T> T debug(T out) {
    System.out.println(out);
    return out;
  }

  //---------------------------------------------------------------------------

  /**
   * 获取按钮单位长度
   * 
   * @return 按钮单位长度
   */
  public int getButtonUnitLength() {
    return bu;
  }

  //---------------------------------------------------------------------------

  /**
   * 切换鼠标抓取状态
   */
  public void changeGrab() {
    Gdx.input.setCursorCatched(grabCursor=!grabCursor);
  }

  /**
   * 启用鼠标抓取
   */
  public void doGrab() {
    Gdx.input.setCursorCatched(grabCursor=true);
  }

  /**
   * 禁用鼠标抓取
   */
  public void noGrab() {
    Gdx.input.setCursorCatched(grabCursor=false);
  }

  //---------------------------------------------------------------------------

  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void mouseReleased(MouseInfo info) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(float x,float y) {}
  @Override
  public void keyPressed(char key,int keyCode) {}
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
  @Override
  public void touchStarted(TouchInfo info) {}
  @Override
  public void touchEnded(TouchInfo info) {}
  @Override
  public void touchMoved(TouchInfo info) {}
  @Override
  public void focusGained() {}
  @Override
  public void focusLost() {}

  //---------------------------------------------------------------------------

  /**
   * 外部调用焦点获取
   */
  public void focusGainedOuter() {
    focus=true;
    for(var e:centerSys.list) e.focusGained();
    focusGained();
  }

  /**
   * 外部调用焦点失去
   */
  public void focusLostOuter() {
    focus=false;
    for(var e:centerSys.list) e.focusLost();
    focusLost();
  }
}
