package pama1234.gdx.util.app;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import hhs.gdx.hslib.tools.LoopThread;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.util.SharedResources;
import pama1234.gdx.util.cam.CameraController;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.input.UtilInputProcesser;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.listener.EntityNeoListener;
import pama1234.gdx.util.wrapper.*;
import pama1234.util.wrapper.Center;
import pama1234.util.wrapper.ServerEntityCenter;

/**
 * 此中间类主要放渲染相关的东东
 * 
 * @see UtilScreen2D
 * @see UtilScreen3D
 */
public abstract class UtilScreen extends UtilScreenRenderShape{
  public void createRenderUtil() {
    //    fontBatch=SharedResources.instance.fontBatch;
    //    font=SharedResources.instance.font;
    //    font.fontBatch=fontBatch;
    //    font.styleFast=fontStyle;
    textFont(SharedResources.instance.font);
    textColor=new Color(0,0,0,1);
    font.color(textColor);
    fillColor=new Color(1,1,1,1);
    strokeColor=new Color(0,0,0,1);
    //    rFill=SharedResources.instance.rFill;
    //    rStroke=SharedResources.instance.rStroke;
    //    rFill.setColor(fillColor);
    //    rStroke.setColor(strokeColor);
    pFill=SharedResources.instance.pFill;
    pFill.setColor(fillColor);

    shapeDrawer=SharedResources.instance.shapeDrawer;
  }
  public void createInputUtil() {
    vectorCache=new Vector3();
    mouse=new MouseInfo(this);
    for(int i=0;i<touches.length;i++) touches[i]=new TouchInfo(this);
    keyPressedArray=new IntArray(false,12);
    backgroundColor=new Color(1,1,1,0);
  }

  //---------------------------------------------------------------------------

  public void preInit() {
    screenCam=new OrthographicCamera();
    imageBatch=SharedResources.instance.imageBatch;
    tvgDrawer=SharedResources.instance.tvgDrawer;
    Gdx.input.setInputProcessor(inputProcessor=new UtilInputProcesser(this));
    // TODO
    serverCenter=new ServerEntityCenter<>(null);
    centerSys=new Center<>();
    //    center=new EntityCenter<>(this);
    center=createEntityCenter();
    center.list.add(cam=createCamera());
    centerSys.list.add(cam);
    // TOOD update顺序，centerScreen应当先于centerCam
    //    center.list.add(centerScreen=new EntityCenter<>(this));
    //    center.list.add(centerCam=new EntityCenter<>(this));
    center.list.add(centerScreen=createEntityCenter());
    center.list.add(centerCam=createEntityCenter());

    center.list.add(centerNeo=new EntityNeoCenter<>(this));

    screenStage=new Stage(screenViewport=new ScalingViewport(Scaling.fit,width,height,screenCam),imageBatch);
    camStage=new Stage(camViewport=new ScalingViewport(Scaling.fit,width,height,cam.camera),imageBatch);
    inputProcessor.sub.add.add(screenStage);
    inputProcessor.sub.add.add(camStage);
    center.list.add(new EntityListener() {
      @Override
      public void update() {
        screenStage.act();
        camStage.act();
      }
      @Override
      public void mousePressed(MouseInfo info) {
        screenStage.setKeyboardFocus(null);
        camStage.setKeyboardFocus(null);
      }
      @Override
      public void frameResized(int w,int h) {
        bu=pus*24;
        screenViewport.setWorldSize(width,height);
        screenViewport.update(width,height);
        camViewport.setWorldSize(width,height);
        camViewport.update(width,height);
      }
    });
    centerScreen.list.add(new EntityListener() {
      @Override
      public void display() {
        rendererEnd();//TODO unintuitive
        usedRenderer=screenStage.getBatch();
        //        endShape();
        screenStage.draw();
        //        beginShape();
        rendererEnd();//TODO ugly
      }
    });
    centerCam.list.add(new EntityListener() {
      @Override
      public void display() {
        rendererEnd();
        usedRenderer=camStage.getBatch();
        //        endShape();
        camStage.draw();
        //        beginShape();
        rendererEnd();
      }
    });

    auto=new AutoEntityManager<UtilScreen>(this);
  }
  public void postInit() {
    createRenderUtil();
    createInputUtil();
    withCam();
    center.refresh();
    innerResize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    if(threadedUpdate) {
      // Gdx.app.postRunnable(new LoopThread("DoUpdateLoopThread",60) {
      //   @Override
      //   public void loop() {
      //     doUpdate();
      //   }
      // });
      updateThread=new LoopThread("DoUpdateLoopThread",60) {
        @Override
        public void loop() {
          doUpdate();
          // System.out.println(frameRate);
        }
      };
      updateThread.start();
    }
  }
  public abstract CameraController createCamera();

  //---------------------------------------------------------------------------

  @Override
  public void render(float delta) {
    frameRate=delta;
    // textScale(pus);
    if(!threadedUpdate) doUpdate();
    doDraw();
    frameCount++;
  }
  public void doUpdate() {
    mouse.update(this);
    for(TouchInfo i:touches) i.update(this);
    inputProcessor.update();
    center.update();
    serverCenter.update();
    update();
  }
  public void doDraw() {
    //    beginShape();
    if(background) background(backgroundColor);
    withCam();
    serverCenter.display();
    centerCam.display();
    centerNeo.displayCam();
    displayWithCam();
    camOverlay();
    withScreen();
    centerScreen.display();
    centerNeo.display();
    display();
    inputProcessor.display();
    //    endShape();
  }
  public void camOverlay() {}

  //---------------------------------------------------------------------------

  public void withScreen() {
    setCamera(screenCam);
    textScale(pus);
    strokeWeight(defaultStrokeWeight=u);
  }
  public abstract void withCam();

  //---------------------------------------------------------------------------

  public void centerCamAddAll(EntityListener... in) {
    for(EntityListener i:in) centerCam.add.add(i);
  }
  public void centerCamRemoveAll(EntityListener... in) {
    for(EntityListener i:in) centerCam.remove.add(i);
  }
  public void centerScreenAddAll(EntityListener... in) {
    for(EntityListener i:in) centerScreen.add.add(i);
  }
  public void centerScreenRemoveAll(EntityListener... in) {
    for(EntityListener i:in) centerScreen.remove.add(i);
  }
  public void centerNeoAddAll(EntityNeoListener... in) {
    for(EntityNeoListener i:in) centerNeo.add.add(i);
  }
  public void centerNeoRemoveAll(EntityNeoListener... in) {
    for(EntityNeoListener i:in) centerNeo.remove.add(i);
  }
  public <T extends EntityListener> void centerCamAddAll(Iterable<T> in) {
    for(EntityListener i:in) centerCam.add.add(i);
  }
  public <T extends EntityListener> void centerCamRemoveAll(Iterable<T> in) {
    for(EntityListener i:in) centerCam.remove.add(i);
  }
  public <T extends EntityListener> void centerScreenAddAll(Iterable<T> in) {
    for(EntityListener i:in) centerScreen.add.add(i);
  }
  public <T extends EntityListener> void centerScreenRemoveAll(Iterable<T> in) {
    for(EntityListener i:in) centerScreen.remove.add(i);
  }
  public <T extends EntityNeoListener> void centerNeoAddAll(Iterable<T> in) {
    for(EntityNeoListener i:in) centerNeo.add.add(i);
  }
  public <T extends EntityNeoListener> void centerNeoRemoveAll(Iterable<T> in) {
    for(EntityNeoListener i:in) centerNeo.remove.add(i);
  }

  //---------------------------------------------------------------------------

  public void addScreenTextFields(TextField... in) {
    for(TextField e:in) screenStage.addActor(e);
  }
  public void addCamTextFields(TextField... in) {
    for(TextField e:in) camStage.addActor(e);
  }
  public void removeScreenTextFields(TextField... in) {
    Group root=screenStage.getRoot();
    for(TextField e:in) root.removeActor(e);
  }
  public void removeCamTextFields(TextField... in) {
    Group root=camStage.getRoot();
    for(TextField e:in) root.removeActor(e);
  }

  public void addScreenTextFields(Array<TextField> in) {
    for(TextField e:in) screenStage.addActor(e);
  }
  public void addCamTextFields(Array<TextField> in) {
    for(TextField e:in) camStage.addActor(e);
  }
  public void removeScreenTextFields(Array<TextField> in) {
    Group root=screenStage.getRoot();
    for(TextField e:in) root.removeActor(e);
  }
  public void removeCamTextFields(Array<TextField> in) {
    Group root=camStage.getRoot();
    for(TextField e:in) root.removeActor(e);
  }

  //---------------------------------------------------------------------------

  public void drawCursor() {
    drawCursor(0,255);
  }
  public void drawCursor(int a,int b) {
    beginBlend();
    // final int a=0,b=255;
    fill(mouse.left?a:b,mouse.center?a:b,mouse.right?a:b,127);
    rect(mouse.x-4,mouse.y-0.5f,8,1);
    rect(mouse.x-0.5f,mouse.y-4,1,8);
    endBlend();
  }

  //---------------------------------------------------------------------------

  public static String[] loadStrings(FileHandle file) {
    return file.readString("UTF-8").replace("\r","").split("\n");
  }
  public static String[] match(String str,String regexp) {
    Pattern p=matchPattern(regexp);
    Matcher m=p.matcher(str);
    if(m.find()) {
      int count=m.groupCount()+1;
      String[] groups=new String[count];
      for(int i=0;i<count;i++) {
        groups[i]=m.group(i);
      }
      return groups;
    }
    return null;
  }
  public static LinkedHashMap<String,Pattern> matchPatterns;
  public static Pattern matchPattern(String regexp) {
    Pattern p=null;
    if(matchPatterns==null) {
      matchPatterns=new LinkedHashMap<>(16,0.75f,true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String,Pattern> eldest) {
          // Limit the number of match patterns at 10 most recently used
          return size()==10;
        }
      };
    }else {
      p=matchPatterns.get(regexp);
    }
    if(p==null) {
      p=Pattern.compile(regexp,Pattern.MULTILINE|Pattern.DOTALL);
      matchPatterns.put(regexp,p);
    }
    return p;
  }

  //---------------------------------------------------------------------------

  public void refreshAllCenter() {
    center.refresh();
    centerCam.refresh();
    centerScreen.refresh();
    centerNeo.refresh();
    serverCenter.refresh();

    // centerSys.refresh();
  }
  public void centerAddContainer(ScreenContentContainer in) {
    center.add.add(in.center);
    centerCam.add.add(in.centerCam);
    centerScreen.add.add(in.centerScreen);
    centerNeo.add.add(in.centerNeo);
    serverCenter.add.add(in.serverCenter);

    // for(var e:in.screenStage.getChildren()) screenStage.addActor(e);
    // for(var e:in.camStage.getChildren()) camStage.addActor(e);

    // for(var e:in.screenStage.list) screenStage.addActor(e);
    // for(var e:in.camStage.list) camStage.addActor(e);

    // screenStage.addActor(in.screenStage);
    // camStage.addActor(in.camStage);
  }
  public void centerRemoveContainer(ScreenContentContainer in) {
    center.remove.add(in.center);
    centerCam.remove.add(in.centerCam);
    centerScreen.remove.add(in.centerScreen);
    centerNeo.remove.add(in.centerNeo);
    serverCenter.remove.add(in.serverCenter);

    // Group tr1=screenStage.getRoot();
    // for(var e:in.screenStage.getChildren()) tr1.removeActor(e);
    // Group tr2=camStage.getRoot();
    // for(var e:in.camStage.getChildren()) tr2.removeActor(e);

    // Group tr1=screenStage.getRoot();
    // tr1.removeActor(in.screenStage);
    // // for(var e:in.screenStage.list) tr1.removeActor(e);
    // Group tr2=camStage.getRoot();
    // tr2.removeActor(in.camStage);
    // // for(var e:in.camStage.list) tr2.removeActor(e);
  }
  public void copyToContainer(ScreenContentContainer in) {
    in.center=center;
    in.centerCam=centerCam;
    in.centerScreen=centerScreen;
    in.centerNeo=centerNeo;
    in.serverCenter=serverCenter;

    // in.screenStage=screenStage.getRoot();
    // in.camStage=camStage.getRoot();

  }
  public void copyFromContainer(ScreenContentContainer in) {
    center=in.center;
    centerCam=in.centerCam;
    centerScreen=in.centerScreen;
    centerNeo=in.centerNeo;
    serverCenter=in.serverCenter;

    // screenStage.setRoot(in.screenStage);
    // camStage.setRoot(in.camStage);

  }
  public EntityCenterAbstract<UtilScreen,EntityListener,?> createEntityCenter() {
    return new EntityCenterConcurrent<>(this);
  }
}
