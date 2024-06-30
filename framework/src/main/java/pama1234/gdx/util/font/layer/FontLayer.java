package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.math.MathUtils;

import pama1234.gdx.util.element.FontStyle;
import pama1234.gdx.util.font.FontUtil.UniFontDependent;

public class FontLayer{
  public FileHandle[] fontFile;
  public int length;
  public boolean loadOnDemand=true;
  public int digitShift;
  public BitmapFont[] dataM;
  public boolean distanceField;
  public boolean flipped=true;
  public FontStyle styleFast=new FontStyle();

  public FontLayer(FileHandle[] fontFile,int length) {
    this.fontFile=fontFile;
    dataM=new BitmapFont[length];
    digitShift=16-MathUtils.ceil(MathUtils.log2(length));
  }

  public boolean isAllLoaded() {
    for(int i=0;i<length;i++) {
      if(dataM[i]==null) {
        return true;
      }
    }
    return false;
  }

  public void loadChunk(int pos) {
    if(dataM[pos]!=null) {
      return;
    }

    loadFont(pos);
    loadOnDemand=isAllLoaded();
  }

  public Glyph getGlyph(char ch) {
    int pos=getPosOfChar(ch);
    loadChunk(pos);
    return dataM[pos].getData().getGlyph(ch);
  }

  public int getPosOfChar(char ch) {
    return ch>>>digitShift;
  }

  public void loadFont(int columns) {
    BitmapFont tf=createBitmapFont(fontFile[columns]);
    dataM[columns]=tf;
    for(int i=0;i<tf.getData().glyphs.length;i++) {
      Glyph[] tgs=tf.getData().glyphs[i];
      if(tgs==null) {
        continue;
      }
//      for(int j=0;j<tgs.length;j++) {
//        Glyph tg=tgs[j];
//        if(tg!=null) {
//          tg.page=columns;
//        }
//      }
    }
  }

  public BitmapFont createBitmapFont(FileHandle fontFile) {
    BitmapFont out=fontFile==null?new BitmapFont(flipped):new BitmapFont(fontFile,flipped);
    out.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Nearest);
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
