package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import pama1234.gdx.util.font.BetterBitmapFont;

/**
 * 将这个字体类想象为一个立方形，横轴是一个字体的多个区块，纵轴是多个不同字符范围的字体，Z轴是一个字体区块的多个Texture（png文件），每次调用时都会按照优先级从上往下寻找最合适的字体（找不到的话有unifont兜底）
 */
public class MultiLayerFont extends BetterBitmapFont{
  public FontLayer[] fontLayers;

  public boolean loadOnDemand;

  public MultiLayerFont(FontLayer[] fontLayers) {
    this.fontLayers=fontLayers;
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
  public float addCharWidth(float out,char tc) {
    return out;
  }

  public void load(char c) {
    for(int i=0;i<fontLayers.length;i++) {
      var font=fontLayers[i];
      if(font.load(c)) break;
    }
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

  }

  @Override
  public GlyphLayout draw(Batch batch,CharSequence str,float x,float y) {
    return null;
  }

  @Override
  public GlyphLayout draw(Batch batch,CharSequence str,float x,float y,float targetWidth,int halign,boolean wrap) {
    return null;
  }

  @Override
  public GlyphLayout draw(Batch batch,CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap) {
    return null;
  }

  @Override
  public GlyphLayout draw(Batch batch,CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap,String truncate) {
    return null;
  }

  @Override
  public void setFullTextColor(Color color) {
    setColor(color);
  }
}
