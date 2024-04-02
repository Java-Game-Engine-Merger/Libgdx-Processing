package pama1234.test.font;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.tools.hiero.Hiero;

import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.launcher.MainAppBase;

public class FontGenerator02 extends UtilScreen2D{
  public static void main(String[] args) {
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
  public String userDir=System.getProperty("user.dir");

  @Override
  public void setup() {
    int size=4096;
    String name="MapleMonoRegular";
    for(int i=0;i<Character.MAX_VALUE;i+=size) {
      try {
        Hiero.main(new String[] {
          "--input \""+userDir+"/framework/src/test/resources/data/pama1234/MapleMono.hiero\"",
          "--output \""+userDir+"/"+size+"CharInPage/"+i+"/"+name+"-"+i+".fnt\"",
          "--batch"});
      }catch(Exception e) {
        throw new RuntimeException(e);
      }
    }
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