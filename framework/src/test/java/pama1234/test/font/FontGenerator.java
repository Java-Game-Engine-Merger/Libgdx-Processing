package pama1234.test.font;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.Page;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.SkylineStrategy;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.tools.distancefield.DistanceFieldGenerator;
import com.badlogic.gdx.utils.Array;

import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.element.RangeChar;
import pama1234.gdx.util.launcher.MainAppBase;
import pama1234.test.font.BitmapFontWriter.FontInfo;

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
  public int charInPage=2048;
  public FreeTypeFontGenerator generator;
  public boolean distanceField=true;
  public DistanceFieldGenerator distanceFieldGenerator;
  public int charSize=64;

  public int posX;
  public int spread=3;

  public void saveFont(String dir,String name,int i,int from,int to) {
    FontInfo info;
    FreeTypeFontParameter param;
    info=new FontInfo(nameFont,charSize);
    info.charset="utf-8";
    param=new FreeTypeFontParameter();
    param.size=charSize;
    param.renderCount=1;
    if(distanceField) {
      param.padTop=spread+1;
      param.padRight=spread+1;
      param.padBottom=spread+1;
      param.padLeft=spread+1;
      param.spaceX=-spread*2;
      param.spaceY=-spread*2;
    }else {
      param.padTop=1;
      param.padRight=1;
      param.padBottom=1;
      param.padLeft=1;
      //      param.spaceX=-1;
      //      param.spaceY=-1;
    }
    param.characters=new StringBuffer(new RangeChar(from,to)).toString();
    param.packer=new PixmapPacker(width,height,Format.Alpha,0,false,new SkylineStrategy());
    FreeTypeBitmapFontData data=generator.generateData(param);

    Array<Page> pages=param.packer.getPages();
    //    for(int j=0;j<pages.size;j++) {
    //      Page page=pages.get(j);
    //      Pixmap pixmap=page.getPixmap();
    //      BufferedImage inImage=(BufferedImage)new ImageIcon(pixmap.getPixels().array()).getImage();
    //      BufferedImage bufferedImage=distanceFieldGenerator.generateDistanceField(inImage);
    ////      pages.set(j,new Page(bufferedImage));
    ////      page.getPixmap();
    //    }

    String[] out=BitmapFontWriter.writePixmaps(pages,Gdx.files.absolute(dir),name,distanceFieldGenerator);
    System.out.println("saved: "+Arrays.toString(out));
    if(distanceField) {
      for(int j=0;j<pages.size;j++) {
        Page page=pages.get(j);
        Pixmap pixmap=page.getPixmap();
        BufferedImage inImage=(BufferedImage)new ImageIcon(pixmap.getPixels().array()).getImage();
        BufferedImage bufferedImage=distanceFieldGenerator.generateDistanceField(inImage);
      }
    }
    BitmapFontWriter.writeFont(data,out,Gdx.files.absolute(dir+name+".fnt"),info,width,height,distanceFieldGenerator);
  }

  @Override
  public void setup() {
    String fontName="MapleMono-SC-NF/MapleMono-SC-NF-Regular.ttf";
    generator=new FreeTypeFontGenerator(Gdx.files.absolute(System.getProperty("user.dir")+"/doc/font/"+fontName));
    if(distanceField) {
      distanceFieldGenerator=new DistanceFieldGenerator();
      // set between 8 and 32
      distanceFieldGenerator.setDownscale(32);
      // set between 2 and 4
      distanceFieldGenerator.setSpread(spread);
      //      distanceFieldGenerator.setColor(Color.WHITE);
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