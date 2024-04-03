package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;

import pama1234.gdx.util.element.FontStyle;
import pama1234.gdx.util.font.FontUtil.UniFontDependent;

public class FontLayer{
  /** 所有区块的文件 */
  public FileHandle[] fontFile;
  /** 区块大小，一般为2的幂 */
  public int length;
  /** 是否按需加载 */
  public boolean loadOnDemand;
  /** log2(length) */
  public int digitShift;
  /** 每个区块加载时会产生一个BitmapFont */
  public BitmapFont[] dataM;
  /** 是否是DistanceField字体 */
  public boolean distanceField;
  /** 一般为true */
  public boolean flipped=true;
  /** 所有字体层共用一个 */
  public FontStyle styleFast;

  public boolean load(char c) {
    //    int pos=c>>>digitShift;
    //
    //    loadFont(pos);
    //    loadOnDemand=isAllLoaded();
    return getGlyph(c)!=null;
  }
  public boolean isAllLoaded() {
    for(int i=0;i<length;i++) if(dataM[i]==null) return true;
    return false;
  }

  public void loadChunk(int pos) {
    if(dataM[pos]!=null) return;

    loadFont(pos);
    loadOnDemand=isAllLoaded();
  }

  public Glyph getGlyph(char ch) {
    int pos=ch>>>digitShift;
    loadChunk(pos);
    Glyph glyph=dataM[pos].getData().getGlyph(ch);
    return glyph;
  }
  public void loadFont(int in) {
    BitmapFont tf=createBitmapFont(fontFile[in]);
    dataM[in]=tf;
    for(int i=0;i<tf.getData().glyphs.length;i++) {//TODO
      Glyph[] tgs=tf.getData().glyphs[i];
      if(tgs==null) continue;
      for(int j=0;j<tgs.length;j++) {
        Glyph tg=tgs[j];
        if(tg!=null) tg.page=in;
      }
    }
  }
  public BitmapFont createBitmapFont(FileHandle fontFile) {
    BitmapFont out=fontFile==null?new BitmapFont(flipped):new BitmapFont(fontFile,flipped);
    out.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Nearest);
    out.getData().setScale(styleFast.size/styleFast.defaultSize);
    BitmapFontData data=out.getData();
    // int unit=UtilMath.max((int)(size/2),1);
    @UniFontDependent
    int unit=(int)(styleFast.defaultSize/2);
    for(int i=0,end=out.getData().glyphs[0].length;i<end;i++) {
      Glyph g=data.glyphs[0][i];
      if(g==null) continue;
      int tl=g.xadvance/unit;
      g.xoffset+=(unit*tl-g.xadvance)/2;
      g.xadvance=unit*tl;
      g.kerning=null;
      g.fixedWidth=true;
    }
    return out;
  }
}
