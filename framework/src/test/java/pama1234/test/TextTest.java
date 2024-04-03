package pama1234.test;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;

import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.launcher.MainAppBase;

public class TextTest extends UtilScreen2D{
  public static void main(String[] args) {
    test0001();
  }

  @Test
  public static void test0001() {
    Class<?> clas=MethodHandles.lookup().lookupClass();
    MainAppBase mab=new MainAppBase() {
      {
        var classArray=new Class[] {clas};
        screenClassList=new ArrayList<>(classArray.length);
        for(int i=0;i<classArray.length;i++) {
          screenClassList.add(i,classArray[i]);
        }
      }

    };
    new Lwjgl3Application(mab,getDefaultConfiguration(mab,clas.getSimpleName()));
  }

  @Override
  public void setup() {
    noStroke();
    centerCamAddAll(new Entity<>(this) {
      @Override
      public void display() {}
    });
    //    addCamTextFields(new TextField("file.name()",new ColorTextFieldStyle(this,null,null,UtilScreen.color(216)),new RectF(()->0,()->0-26,()->250-120,()->18),()->1));
    //
    //    textMode(BetterBitmapFont.fullText);

//    depth(true);
  }

  @Override
  public void update() {

  }

  @Override
  public void display() {}

  @Override
  public void displayWithCam() {
    //    textColor(0);
    //    depth(true);

    text("public static void main");
    fullText("TextTest.java使用了未经检查或不安全的操作。",0,20);
    text("TextTest.java\n使用了\n\n未经检查或不安全的操作。",0,40);
  }

  @Override
  public void frameResized() {

  }
}
