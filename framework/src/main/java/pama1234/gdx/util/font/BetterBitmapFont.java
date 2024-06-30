package pama1234.gdx.util.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import pama1234.gdx.util.element.FontStyle;
import pama1234.util.Annotations.RedundantCache;
import pama1234.util.Annotations.SyntacticSugar;
import pama1234.util.function.Get;

/**
 * BitmapFont并不是一种很好的接口
 */
public abstract class BetterBitmapFont extends BitmapFont{
  /**
   * @see BetterBitmapFont#textMode
   */
  public static final int fastText=0,fullText=1;

  @RedundantCache
  public Get<Batch> fontBatch;
  @RedundantCache
  public FontStyle styleFast;

  public TextStyleSupplier style;

  public int textMode=fastText;

  //---------------------------------------------------------------------------

  public BetterBitmapFont() {}

  public BetterBitmapFont(boolean flip) {
    super(flip);
  }

  public BetterBitmapFont(FileHandle fontFile,TextureRegion region) {
    super(fontFile,region);
  }

  public BetterBitmapFont(FileHandle fontFile,TextureRegion region,boolean flip) {
    super(fontFile,region,flip);
  }

  public BetterBitmapFont(FileHandle fontFile) {
    super(fontFile);
  }

  public BetterBitmapFont(FileHandle fontFile,boolean flip) {
    super(fontFile,flip);
  }

  public BetterBitmapFont(FileHandle fontFile,FileHandle imageFile,boolean flip) {
    super(fontFile,imageFile,flip);
  }

  public BetterBitmapFont(FileHandle fontFile,FileHandle imageFile,boolean flip,boolean integer) {
    super(fontFile,imageFile,flip,integer);
  }

  public BetterBitmapFont(BitmapFontData data,TextureRegion region,boolean integer) {
    super(data,region,integer);
  }

  public BetterBitmapFont(BitmapFontData data,Array<TextureRegion> pageRegions,boolean integer) {
    super(data,pageRegions,integer);
  }

  //---------------------------------------------------------------------------
  @Deprecated
  public abstract void setFullTextColor(Color color);

  public void color(Color in) {
    styleFast.foreground=in;
    //    fontBatch().setColor(styleFast.foreground);
  }
  @SyntacticSugar
  public Batch fontBatch() {
    return fontBatch.get();
  }

  public float textWidth(CharSequence in,boolean scale) {
    if(!scale) return textWidthNoScale(in);
    else return textWidthNoScale(in)*styleFast.scale;
  }

  public abstract float textWidthNoScale(CharSequence in);

  public void textScale(float in) {
    styleFast.scale=in;
  }

  public float size() {
    return styleFast.size;
  }

  //TODO
  @SyntacticSugar
  public void size(int in) {
    size((float)in);
  }

  public void size(float in) {
//    if(styleFast.size==in) return;
    styleFast.size=in;
    //    for(int i=0;i<fontFile.length;i++) if(dataM[i]!=null) dataM[i].getData().setScale(styleFast.size/styleFast.defaultSize);
  }

  public abstract Glyph getGlyph(char ch);

  //  @Deprecated
//  public abstract void load(int chunk);
  public abstract void loadAll(String s);
  public void markupEnabled(boolean in) {
    getData().markupEnabled=in;
  }

  //---------------------------------------------------------------------------

  public void text(String in,float x,float y) {
    if(textMode==fastText) {
      fontBatch().setColor(styleFast.foreground);
      fastText(in,x,y);
    }else if(textMode==fullText) {
      fontBatch().setColor(getColor());
      draw(fontBatch.get(),in,x,y);
    }
  }

  /**
   * 全称fastText，比libgdx的text方法更快一些，无法绘制多色或有换行的文字
   *
   * @param in 文本
   * @param x  坐标
   * @param y  坐标
   */
  public abstract void fastText(String in,float x,float y);

  //---------------------------------------------------------------------------

  /**
   * @see BitmapFont#draw(Batch, CharSequence, float, float)
   * 
   * @param batch
   * @param str
   * @param x
   * @param y
   * @return
   */
  @Override
  public abstract GlyphLayout draw(Batch batch,CharSequence str,float x,float y);

  /**
   * @see BitmapFont#draw(Batch, CharSequence, float, float, float, int, boolean)
   * @param batch
   * @param str
   * @param x
   * @param y
   * @param targetWidth
   * @param halign
   * @param wrap
   * @return
   */
  @Override
  public abstract GlyphLayout draw(Batch batch,CharSequence str,float x,float y,float targetWidth,int halign,boolean wrap);

  /**
   * used by TextArea and TextField
   * 
   * @see BitmapFont#draw(Batch, CharSequence, float, float, int, int, float, int, boolean)
   * 
   * @param batch
   * @param str
   * @param x
   * @param y
   * @param start
   * @param end
   * @param targetWidth
   * @param halign
   * @param wrap
   * @return
   */
  @Override
  public abstract GlyphLayout draw(Batch batch,CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap);

  /**
   * used by TextField
   * 
   * @see BitmapFont#draw(Batch, CharSequence, float, float, int, int, float, int, boolean,
   *      String)
   *
   * @param batch
   * @param str
   * @param x
   * @param y
   * @param start
   * @param end
   * @param targetWidth
   * @param halign
   * @param wrap
   * @param truncate
   * @return
   */
  @Override
  public abstract GlyphLayout draw(Batch batch,CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap,String truncate);
}
