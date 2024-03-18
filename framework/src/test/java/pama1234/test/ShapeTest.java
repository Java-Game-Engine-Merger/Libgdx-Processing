package pama1234.test;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;

import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.launcher.MainAppBase;

public class ShapeTest extends UtilScreen2D{
  public static void main(String[] args) {
    test0001();
  }

  @Test
  public static void test0001() {
    MainAppBase mab=new MainAppBase() {
      {
        var classArray=new Class[] {ShapeTest.class};
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

  }

  @Override
  public void update() {

  }

  @Override
  public void display() {

  }

  @Override
  public void displayWithCam() {

  }

  @Override
  public void frameResized() {

  }
}
