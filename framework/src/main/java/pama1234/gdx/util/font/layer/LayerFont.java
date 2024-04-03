package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import pama1234.gdx.util.font.BetterBitmapFont;

public class LayerFont extends BetterBitmapFont{
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
