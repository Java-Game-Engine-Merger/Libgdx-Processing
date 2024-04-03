package pama1234.gdx.util.font.chunk;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;

import pama1234.gdx.util.font.FastGlyphLayout;
import pama1234.gdx.util.font.FontUtil.UniFontDependent;

/**
 * 按需加载多个存储在资源目录中的字体区块png图片文件
 *
 * 默认使用经过转换的unifont
 */
@UniFontDependent
public class MultiChunkFont extends MultiChunkFontCore{

  public MultiChunkFont(FileHandle[] fontFile,boolean flip,boolean loadOnDemand) {
    super(fontFile,flip,loadOnDemand);
  }

  @Override
  public FastGlyphLayout draw(Batch batch, CharSequence str, float x, float y) {
    cacheM.clear();
    FastGlyphLayout layout=cacheM.addFastText(str,x,y);
    cacheM.draw(batch);
    return layout;
  }
  public void draw(Batch batch, FastGlyphLayout layout, float x, float y) {
    cacheM.clear();
    cacheM.addText(layout,x,y);
    cacheM.draw(batch);
  }
  @Override
  public FastGlyphLayout draw(Batch batch, CharSequence str, float x, float y, float targetWidth, int halign, boolean wrap) {
    cacheM.clear();
    FastGlyphLayout layout=cacheM.addFastText(str,x,y,targetWidth,halign,wrap);
    cacheM.draw(batch);
    return layout;
  }
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
  @Override
  public FastGlyphLayout draw(Batch batch, CharSequence str, float x, float y, int start, int end, float targetWidth, int halign, boolean wrap) {
    cacheM.clear();
    FastGlyphLayout layout=cacheM.addFastText(str,x,y,start,end,targetWidth,halign,wrap);
    cacheM.draw(batch);
    return layout;
  }
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
  @Override
  public FastGlyphLayout draw(Batch batch, CharSequence str, float x, float y, int start, int end, float targetWidth, int halign, boolean wrap, String truncate) {
    cacheM.clear();
    FastGlyphLayout layout=cacheM.addFastText(str,x,y,start,end,targetWidth,halign,wrap,truncate);
    cacheM.draw(batch);
    return layout;
  }
}