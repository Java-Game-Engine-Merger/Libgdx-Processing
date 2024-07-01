package pama1234.gdx.util.font.layer;

import static space.earlygrey.shapedrawer.ShapeDrawer.createBlankTextureRegion;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import pama1234.gdx.util.font.BetterBitmapFont;
import pama1234.gdx.util.font.DistanceFieldShader;
import pama1234.gdx.util.font.FastGlyphLayout;
import pama1234.gdx.util.font.FontUtil.UniFontDependent;
import pama1234.math.vec.Vec2f;
import pama1234.math.vec.Vec3i;
import pama1234.util.function.GetFloat;

/**
 * MultiLayerFont 类表示一个多层字体系统。 横轴表示一个字体的多个区块，纵轴表示多个不同字符范围的字体，Z轴表示一个字体区块的多个Texture（png文件）。
 * 每次调用时都会按照优先级从上往下寻找最合适的字体（找不到的话有unifont兜底）。
 */
public class MultiLayerFont extends BetterBitmapFont{
  public static float lineSizeConst=20;
  public static float smoothConst=0.25f;
  public static boolean debug;
  public static final int useCR=0,showCR=1,ignoreCR=2;

  public FontLayer[] fontLayers;
  public boolean loadOnDemand;

  /**
   * posI 的 x 表示字符宽度，y 表示行数，z 表示这一行目前的字符数量。
   */
  public Vec3i posI=new Vec3i();
  public Vec2f cacheV=new Vec2f(),inPos=new Vec2f();
  public boolean useLF=true,useTab=true;
  public int stateCR=ignoreCR;

  // TODO lineSize在不同字体中是不一样的
  @UniFontDependent
  public float lineSize=lineSizeConst,charWidth=8,backgroundXOffset=1;
  public float lineSizeScale=0.5f;
  public float tabSize=16;
  public TextureRegion backgroundAlt=createBlankTextureRegion();

  public LayerFontCache cache;
  public DistanceFieldShader distanceFieldShader;
  public GetFloat camScale;

  public MultiLayerFont(FontLayer[] fontLayers) {
    this.fontLayers=fontLayers;
    for(FontLayer fontLayer:fontLayers) {
      fontLayer.lineSizeScale=lineSizeScale;
    }
    this.cache=new LayerFontCache(this);
    this.distanceFieldShader=new DistanceFieldShader();
  }

  @Override
  public float textWidthNoScale(CharSequence in) {
    float out=0;
    for(int i=0;i<in.length();i++) {
      char tc=in.charAt(i);
      load(tc);
      out=addCharWidth(out,tc);
    }
    return out;
  }

  public float addCharWidth(float x,char tc) {
    var glyph=getGlyph(tc);
    if(glyph==null) {
      System.err.println("MultiLayerFont.addCharWidth char=<"+tc+"> char(int)="+(int)tc);
      return x;
    }
    x+=glyph.xadvance*lineSizeScale;
    return x;
  }

  @Override
  public Glyph getGlyph(char ch) {
    return load(ch);
  }

  public Glyph load(char c) {
    for(FontLayer font:fontLayers) {
      var g=font.getGlyph(c);
      if(g!=null) return g;
    }
    return null;
  }

  @Override
  public void loadAll(String in) {
    for(int i=0;i<in.length();i++) {
      char tc=in.charAt(i);
      load(tc);
    }
  }

  @Override
  public void text(String in,float x,float y) {
    // 调整平滑参数的计算方式
    float scale=getData().scaleX*camScale.get();
    float smoothing=smoothConst/scale;

    //    temp_test_smooth_var="getData().scaleX: "+getData().scaleX+", camScale.get(): "+camScale.get()+", Scale: "+scale+", Smoothing: "+smoothing;

    Batch batch=fontBatch();
    batch.flush();
    var shader=batch.getShader();
    // 保存当前颜色和混合模式
    //    Color originalColor = batch.getColor().cpy();
    //    int srcFunc = batch.getBlendSrcFunc();
    //    int dstFunc = batch.getBlendDstFunc();

    distanceFieldShader.bind();
    distanceFieldShader.setSmoothing(smoothing);
    batch.setShader(distanceFieldShader);

    super.text(in,x,y);

    shader.bind();
    batch.setShader(shader);
    //    batch.setColor(originalColor);
    //    batch.setBlendFunction(srcFunc,dstFunc);
  }

  //  public static String temp_test_smooth_var;

  @Override
  public void fastText(String in,float x,float y) {

    posI.set(0,0,0);
    cacheV.set(x,y);
    inPos.set(cacheV);
    if(style!=null) style.text(in);
    for(int i=0;i<in.length();i++) {
      char tc=in.charAt(i);
      load(tc);
      drawChar(cacheV,tc,i);
    }
  }

  public void drawChar(Vec2f v,char tc,int i) {
    float scaleLineSize=styleFast.scale*lineSizeScale;
    if(tc=='\r') {
      switch(stateCR) {
        case useCR:
          drawCharNewLine(v);
          break;
        case ignoreCR:
          return;
      }
    }
    if(tc=='\n'&&useLF) {
      drawCharNewLine(v);
      return;
    }
    if(tc=='\t'&&useTab) {
      posI.x+=tabSize/charWidth;
      posI.z+=1;
      v.x+=tabSize*scaleLineSize;
      return;
    }
    FontLayer fontLayer=fontLayers[0]; // TODO: 需要改进
    int posOfChar=fontLayer.getPosOfChar(tc);
    Array<TextureRegion> regions=fontLayer.dataM[posOfChar].getRegions();
    Glyph glyph=getGlyph(tc);
    if(glyph==null) {
      if(debug) System.err.println("char=<"+tc+"> char(int)="+(int)tc+" is not in used font");
      return;
    }

    Texture texture=regions.get(glyph.page).getTexture();
    Batch batch=fontBatch();
    if(style!=null) {
      batch.setColor(style.background(posI.z,posI.y,i));
      batch.draw(backgroundAlt,
        v.x+backgroundXOffset*scaleLineSize,
        v.y,
        glyph.xadvance*scaleLineSize,
        lineSize*scaleLineSize);
      batch.setColor(style.foreground(posI.z,posI.y,i));
      drawChar(v,glyph,texture);
    }else {
      if(styleFast.background!=null) {
        batch.setColor(styleFast.background);
        batch.draw(backgroundAlt,
          v.x+backgroundXOffset*scaleLineSize,
          v.y,
          glyph.xadvance*scaleLineSize,
          lineSize*scaleLineSize);
        batch.setColor(styleFast.foreground);
      }
      drawChar(v,glyph,texture);
    }
    posI.x+=glyph.xadvance/charWidth;
    posI.z+=1;
    v.x+=glyph.xadvance*styleFast.scale*lineSizeScale;
  }

  private void drawCharNewLine(Vec2f v) {
    posI.x=0;
    posI.z=0;
    posI.y+=1;
    v.x=inPos.x;
    v.y+=lineSize;
  }

  private void drawChar(Vec2f v,Glyph glyph,Texture texture) {
    float scaleLineSize=styleFast.scale*lineSizeScale;
    fontBatch().draw(texture,
      v.x+glyph.xoffset*scaleLineSize,
      v.y+glyph.yoffset*scaleLineSize,
      glyph.width*scaleLineSize,
      glyph.height*scaleLineSize,
      glyph.u,glyph.v,
      glyph.u2,glyph.v2);
  }

  @Override
  public GlyphLayout draw(Batch batch,CharSequence str,float x,float y) {
    cache.clear();
    FastGlyphLayout layout=cache.addFastText(str,x,y);
    cache.draw(batch);
    return layout;
  }

  @Override
  public GlyphLayout draw(Batch batch,CharSequence str,float x,float y,float targetWidth,int halign,boolean wrap) {
    cache.clear();
    FastGlyphLayout layout=cache.addFastText(str,x,y,targetWidth,halign,wrap);
    cache.draw(batch);
    return layout;
  }

  @Override
  public GlyphLayout draw(Batch batch,CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap) {
    cache.clear();
    FastGlyphLayout layout=cache.addFastText(str,x,y,start,end,targetWidth,halign,wrap);
    cache.draw(batch);
    return layout;
  }

  @Override
  public GlyphLayout draw(Batch batch,CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap,String truncate) {
    cache.clear();
    FastGlyphLayout layout=cache.addFastText(str,x,y,start,end,targetWidth,halign,wrap,truncate);
    cache.draw(batch);
    return layout;
  }

  @Override
  public void setFullTextColor(Color color) {
    setColor(color);
  }
}
