package pama1234.gdx.util.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import pama1234.gdx.util.app.UtilScreenCore;
import pama1234.gdx.util.element.FontStyle;
import pama1234.gdx.util.font.FontUtil.UniFontDependent;
import pama1234.util.Annotations.RedundantCache;

/**
 * 提供两种渲染文本的方式，一种不支持换行符号等，一种支持换行
 */
public abstract class BetterBitmapFont extends BitmapFont{
  /** @see BetterBitmapFont#textMode */
  public static final int fastText=0,fullText=1;

  @RedundantCache
  public SpriteBatch fontBatch;
  @RedundantCache
  public FontStyle styleFast;

  public TextStyleSupplier style;

  public int textMode;

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

  public void color(Color in) {
    styleFast.foreground=in;
    fontBatch.setColor(styleFast.foreground);
  }

  public float textWidth(CharSequence in) {
    return textWidthNoScale(in)*styleFast.scale;
  }
  public abstract float textWidthNoScale(CharSequence in);

  public void textScale(float in) {
    styleFast.scale=in;
  }

  public float size() {
    return styleFast.size;
  }
  //TODO
  public void size(int in) {
    size((float)in);
  }
  public abstract void size(float in);

  /**
   * 全称fastText，比libgdx的text方法更快一些，无法绘制多色或有换行的文字
   *
   * @param in 文本
   * @param x  坐标
   * @param y  坐标
   */
  public abstract void fastText(String in,float x,float y);

  public abstract GlyphLayout drawF(Batch batch, CharSequence str, float x, float y);
  public abstract GlyphLayout drawF(Batch batch,CharSequence str,float x,float y,float targetWidth,int halign,boolean wrap);
  /**
   * used by TextArea and TextField
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
  public abstract GlyphLayout drawF(Batch batch,CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap);
  /**
   * used by TextField
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
  public abstract GlyphLayout drawF(Batch batch,CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,boolean wrap,String truncate);

  public abstract void setFullTextColor(Color color);

  public void text(String in,float x,float y) {
    if(textMode==fastText) {
      fastText(in,x,y);
    }else if(textMode==fullText) {
      drawF(fontBatch,in,x,y);
    }
  }
}
