package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import pama1234.gdx.util.element.FontStyle;
import pama1234.gdx.util.font.FontUtil.UniFontDependent;

public class FontLayer{
  /** 字体文件数组 */
  public FileHandle[] fontFile;
  /** 字体文件数组长度 */
  public int length;
  /** 是否按需加载 */
  public boolean loadOnDemand=true;
  /** 位移量，用于计算字符位置 */
  public int digitShift;
  /** BitmapFont 数组 */
  public BitmapFont[] dataM;
  /** 是否使用距离场字体 */
  public boolean distanceField;
  /** 是否翻转字体 */
  public boolean flipped=true;
  /** 字体样式 */
  public FontStyle styleFast=new FontStyle();

  /** 是否使用平滑字体 */
  public boolean smoothFont=true;

  public float lineSizeScale;

  /**
   * 构造函数，初始化字体文件和 BitmapFont 数组
   *
   * @param fontFile 字体文件数组
   * @param length   字体文件数组长度
   */
  public FontLayer(FileHandle[] fontFile,int length) {
    this.fontFile=fontFile;
    this.length=length;
    this.dataM=new BitmapFont[length];
    this.digitShift=16-MathUtils.ceil(MathUtils.log2(length));
  }

  /**
   * 检查所有字体是否已加载
   *
   * @return 如果所有字体已加载则返回 true，否则返回 false
   */
  public boolean isAllLoaded() {
    for(int i=0;i<length;i++) {
      if(dataM[i]==null) {
        return true;
      }
    }
    return false;
  }

  // 加载指定位置的字体块
  public void loadChunk(int pos) {
    if(dataM[pos]!=null) {
      return;
    }
    loadFont(pos);
    loadOnDemand=isAllLoaded();
  }

  // 获取指定字符的 Glyph 对象
  public Glyph getGlyph(char ch) {
    int pos=getPosOfChar(ch);
    loadChunk(pos);
    return dataM[pos].getData().getGlyph(ch);
  }

  /**
   * 计算字符在字体数组中的位置
   *
   * @param ch 字符
   * @return 字符在字体数组中的位置
   */
  public int getPosOfChar(char ch) {
    return ch>>>digitShift;
  }

  /**
   * 加载指定位置的字体
   *
   * @param columns 字体位置
   */
  public void loadFont(int columns) {
    BitmapFont tf=createBitmapFont(fontFile[columns]);
    dataM[columns]=tf;
    BitmapFontData fontData=tf.getData();
    for(int i=0;i<fontData.glyphs.length;i++) {
      Glyph[] tgs=fontData.glyphs[i];
      if(tgs==null) {
        continue;
      }
    }
    fontData.setScale(lineSizeScale);
  }

  /**
   * 创建 BitmapFont 对象
   *
   * @param fontFile 字体文件
   * @return 创建的 BitmapFont 对象
   */
  public BitmapFont createBitmapFont(FileHandle fontFile) {
    BitmapFont out=fontFile==null?new BitmapFont(flipped):new BitmapFont(fontFile,flipped);
    Array<TextureRegion> regions=out.getRegions(); // Apply texture filter to all regions
    for(TextureRegion region:regions) {
      region.getTexture().setFilter(TextureFilter.Linear,smoothFont?TextureFilter.Linear:TextureFilter.Nearest);
    }
    BitmapFontData data=out.getData();

    @UniFontDependent
    int unit=(int)(styleFast.defaultSize/2);
    for(int i=0,end=out.getData().glyphs[0].length;i<end;i++) {
      Glyph g=data.glyphs[0][i];
      if(g==null) {
        continue;
      }
      int tl=g.xadvance/unit;
      g.xoffset+=(unit*tl-g.xadvance)/2;
      g.xadvance=unit*tl;
      g.kerning=null;
      g.fixedWidth=true;
    }
    return out;
  }
}
