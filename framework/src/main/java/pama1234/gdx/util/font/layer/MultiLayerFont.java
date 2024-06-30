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

/**
 * 将这个字体类想象为一个立方形，横轴是一个字体的多个区块，纵轴是多个不同字符范围的字体，Z轴是一个字体区块的多个Texture（png文件），每次调用时都会按照优先级从上往下寻找最合适的字体（找不到的话有unifont兜底）
 */
public class MultiLayerFont extends BetterBitmapFont{
  public static boolean debug;
  public static final int useCR=0,showCR=1,ignoreCR=2;

  public FontLayer[] fontLayers;
  // TODO
  public boolean loadOnDemand;

  /**
   * x是以字符宽度的角度记录的
   * </p>
   * y是行数
   * </p>
   * z是这一行目前的字符数量
   */
  public Vec3i posI=new Vec3i();
  public Vec2f cacheV=new Vec2f(),inPos=new Vec2f();
  public boolean useLF=true,useTab=true;
  public int stateCR=ignoreCR;
  // TODO
  @UniFontDependent
  public float lineSize=20,charWidth=8,backgroundXOffset=1;
  public float tabSize=16;
  public TextureRegion backgroundAlt=createBlankTextureRegion();

  public LayerFontCache cache;

  public MultiLayerFont(FontLayer[] fontLayers) {
    this.fontLayers=fontLayers;

    cache=new LayerFontCache(this);

    fontBatch().setShader(new DistanceFieldShader());
  }

  @Override
  public float textWidthNoScale(CharSequence in) {
    float out=0;
    //    @UniFontDependent
    //    float out=2;
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
    x+=glyph.xadvance;
    return x;
  }
  @Override
  public Glyph getGlyph(char ch) {
    return load(ch);
  }

  public Glyph load(char c) {
    for(int i=0;i<fontLayers.length;i++) {
      var font=fontLayers[i];
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
    if(tc=='	'&&useTab) {
      posI.x+=tabSize/charWidth;
      posI.z+=1;
      v.x+=tabSize*styleFast.scale;
      return;
    }
    FontLayer fontLayer=fontLayers[0];//TODO
    int posOfChar=fontLayer.getPosOfChar(tc);
    Array<TextureRegion> regions=fontLayer.dataM[posOfChar].getRegions();
    Glyph glyph=getGlyph(tc);
    if(glyph==null) {
      if(debug) System.err.println("char=<"+tc+"> char(int)="+(int)tc+"is not in used font");
      return;
    }
//    System.out.println("char=<"+tc+"> char(int)="+(int)tc+" posOfChar="+posOfChar);

    Texture texture=regions.get(glyph.page).getTexture();
    if(style!=null) {
      fontBatch().setColor(style.background(posI.z,posI.y,i));
      fontBatch().draw(backgroundAlt,
        v.x+backgroundXOffset*styleFast.scale,
        v.y,
        glyph.xadvance*styleFast.scale,
        lineSize*styleFast.scale);
      fontBatch().setColor(style.foreground(posI.z,posI.y,i));
      drawChar(v,glyph,texture);
    }else {
      if(styleFast.background!=null) {
        fontBatch().setColor(styleFast.background);
        fontBatch().draw(backgroundAlt,
          v.x+backgroundXOffset*styleFast.scale,
          v.y,
          glyph.xadvance*styleFast.scale,
          lineSize*styleFast.scale);
        fontBatch().setColor(styleFast.foreground);
      }
      drawChar(v,glyph,texture);
    }
    posI.x+=glyph.xadvance/charWidth;
    posI.z+=1;
    v.x+=glyph.xadvance*styleFast.scale;
  }
  private void drawCharNewLine(Vec2f v) {
    posI.x=0;
    posI.z=0;
    posI.y+=1;
    v.x=inPos.x;
    v.y+=lineSize;
  }
  private void drawChar(Vec2f v,Glyph glyph,Texture texture) {
    fontBatch().draw(texture,
      v.x+glyph.xoffset*styleFast.scale,
      v.y+glyph.yoffset*styleFast.scale,
      glyph.width*styleFast.scale,
      glyph.height*styleFast.scale,
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
