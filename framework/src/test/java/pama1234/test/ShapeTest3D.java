package pama1234.test;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.entity.EntityNeo;
import pama1234.gdx.util.launcher.MainAppBase;

public class ShapeTest3D extends UtilScreen3D{
  public static void main(String[] args) {
    test0001();
  }

  @Test
  public static void test0001() {
    MainAppBase mab=new MainAppBase() {
      {
        var classArray=new Class[] {ShapeTest3D.class};
        screenClassList=new ArrayList<>(classArray.length);
        for(int i=0;i<classArray.length;i++) {
          screenClassList.add(i,classArray[i]);
        }
      }

    };
    new Lwjgl3Application(mab,getDefaultConfiguration(mab,"ShapeTest"));
  }

  @Override
  public void setup() {
    fill(ColorUtil.clas);
    stroke(ColorUtil.keyword);

    centerNeo.add(new EntityNeo<>(this) {
      @Override
      public void displayCam() {
        sline(50,0,100,50);
        scircle(0,0,32);
      }
    });
  }

  @Override
  public void update() {

  }

  @Override
  public void display() {}

  @Override
  public void displayWithCam() {

    //    line(0,0,50,50);

    //    sline(50,0,100,50);
    //
    //    scircle(0,0,32);
  }

  @Override
  public void frameResized() {

  }

  public interface ColorUtil{

    Color background=color("DDF4C4");

    /** we all know this means class */
    Color clas=color("FB6104");
    /** we all know this means interface */
    Color interfase=color("6D5CB4");
    Color keyword=color("248888");

    Color data=color("D53569");
    Color function=color("005984");

    Color number=color("2A00EC");
    Color string=color("7F4794");
    /** 例如/t */
    Color stringToken=color("D37ADB");

    Color comment=color("008000");
    Color doc=color("3F5FBF");

    Color cursorLine=color("6FEDFB");
    Color select=color("FFCC00");

    Color unused=color("808080");

  }

}
