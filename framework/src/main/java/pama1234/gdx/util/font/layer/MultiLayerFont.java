package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import pama1234.gdx.util.font.BetterBitmapFont;

/**
 * 将这个字体类想象为一个长方形，横轴是一个字体的多个区块，纵轴是多个不同字符范围的字体，每次调用时都会按照优先级从上往下寻找最合适的字体（找不到的话有unifont兜底）
 */
public class MultiLayerFont extends BetterBitmapFont{
  public FontLayer[] fontLayers;

  public MultiLayerFont() {}

  @Override
  public float textWidthNoScale(CharSequence in) {
    return 0;
  }

  @Override
  public void size(float in) {

  }

  @Override
  public void load(int chunk) {

  }

  @Override
  public void loadAll(String s) {

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

  }
}
