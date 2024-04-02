package pama1234.test.font;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.tools.hiero.BMFontUtil;
import com.badlogic.gdx.tools.hiero.HieroSettings;
import com.badlogic.gdx.tools.hiero.unicodefont.UnicodeFont;
import com.badlogic.gdx.tools.hiero.unicodefont.effects.ConfigurableEffect.Value;
import com.badlogic.gdx.tools.hiero.unicodefont.effects.DistanceFieldEffect;

import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.launcher.MainAppBase;

public class FontGenerator03 extends UtilScreen2D{
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

  public HieroSettings hieroSettings;
  public UnicodeFont unicodeFont;

  @Override
  public void setup() {
    hieroSettings=new HieroSettings();
    hieroSettings.setFontSize(32);
    hieroSettings.setGlyphText("abcABC123!@#$%^&*()_+`-=[]\\{}|;':,./<>?\"");
    DistanceFieldEffect distanceFieldEffect=new DistanceFieldEffect();
    var list=new ArrayList<Value>();
    list.add(new BasicValue("Color",Color.WHITE));
    list.add(new BasicValue("Scale",32));
    list.add(new BasicValue("Spread",3));
    distanceFieldEffect.setValues(list);
    hieroSettings.getEffects().add(distanceFieldEffect);
    unicodeFont=new UnicodeFont(Font.getFont("Maple Mono SC NF"),hieroSettings);
    String pathAndName="MapleMono.fnt";

    try {
      new BMFontUtil(unicodeFont).save(new File(pathAndName));
    }catch(IOException e) {
      throw new RuntimeException(e);
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

  public static class BasicValue implements Value{
    public String name;
    public Object object;

    public BasicValue(String name,Object object) {
      this.name=name;
      this.object=object;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public void setString(String value) {

    }

    @Override
    public String getString() {
      return "null";
    }

    @Override
    public Object getObject() {
      return object;
    }

    @Override
    public void showDialog() {

    }
  }
}