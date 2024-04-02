package pama1234.test.font;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.SkylineStrategy;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.tools.bmfont.BitmapFontWriter;
import com.badlogic.gdx.tools.bmfont.BitmapFontWriter.FontInfo;
import com.badlogic.gdx.tools.distancefield.DistanceFieldGenerator;

import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.element.RangeChar;
import pama1234.gdx.util.launcher.MainAppBase;

public class FontGenerator extends UtilScreen2D{
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

  public String outDir=System.getProperty("user.dir")+"/fontOut/";
  public String nameFont="MapleMonoRegular";
  public int width=1280,height=1280;
  public int charInPage=4096;
  public FreeTypeFontGenerator generator;
  public boolean distanceField=true;

  public int posX;

  public void saveFont(String dir,String name,int i,int from,int to) {
    FontInfo info;
    FreeTypeFontParameter param;
    info=new FontInfo(nameFont,16);
    info.charset="utf-8";
    param=new FreeTypeFontParameter();
    param.size=64;
    param.renderCount=1;
    param.padTop=1;
    param.padRight=1;
    param.padBottom=1;
    param.padLeft=1;
    // param.spaceX=-2;
    // param.spaceY=-2;
    param.characters=new StringBuffer(new RangeChar(from,to)).toString();
    param.packer=new PixmapPacker(width,height,Format.Alpha,0,false,new SkylineStrategy());
    FreeTypeBitmapFontData data=generator.generateData(param);
    String[] out=BitmapFontWriter.writePixmaps(param.packer.getPages(),Gdx.files.absolute(dir),name);
    BitmapFontWriter.writeFont(data,out,Gdx.files.absolute(dir+name+".fnt"),info,width,height);
  }

  @Override
  public void setup() {
    String fontName="MapleMono-SC-NF/MapleMono-SC-NF-Regular.ttf";
    generator=new FreeTypeFontGenerator(Gdx.files.absolute(System.getProperty("user.dir")+"/doc/font/"+fontName));
    if(distanceField) {
      DistanceFieldGenerator distanceFieldGenerator=new DistanceFieldGenerator();
      distanceFieldGenerator.setDownscale(16);
      distanceFieldGenerator.setSpread(4);
    }

    new Thread(()-> {
      int c=0;
      int size=charInPage;

      for(int i=0;i<Character.MAX_VALUE;i+=size) {
        posX=i;
        try {
          saveFont(outDir+"/charInPage-"+charInPage+"/"+c+"/",nameFont+"-"+c,c,i,i+size);
        }catch(RuntimeException e) {
          System.out.println(i+" "+e);
        }
        c++;
      }
      Gdx.app.exit();
    }).start();
  }

  @Override
  public void update() {

  }

  @Override
  public void display() {
    text("generating "+posX);
  }

  @Override
  public void displayWithCam() {

  }

  @Override
  public void frameResized() {

  }
}