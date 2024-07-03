package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

public class LayerFontData extends BitmapFontData{
  private static final int LOG2_PAGE_SIZE=9;
  private static final int PAGE_SIZE=1<<LOG2_PAGE_SIZE;
  private static final int PAGES=0x10000/PAGE_SIZE;

  //  public float scaleX_01,scaleY_01;

  public MultiLayerFont p;

  public LayerFontData(MultiLayerFont multiLayerFont) {
    p=multiLayerFont;
  }

  //  @Override
  //  public void setScale(float scaleX,float scaleY) {
  //    this.scaleX=scaleX;
  //    this.scaleY=scaleY;
  //    
  //    setScale01(scaleX*p.lineSizeScale_02,scaleY*p.lineSizeScale_02);
  //  }
  //
  //  public void setScale01(float scaleX,float scaleY) {
  //    if(scaleX_01==0) throw new IllegalArgumentException("scaleX cannot be 0.");
  //    if(scaleY_01==0) throw new IllegalArgumentException("scaleY cannot be 0.");
  //    float x=scaleX_01/this.scaleX_01;
  //    float y=scaleY_01/this.scaleY_01;
  //    lineHeight*=y;
  //    spaceXadvance*=x;
  //    xHeight*=y;
  //    capHeight*=y;
  //    ascent*=y;
  //    descent*=y;
  //    down*=y;
  //    padLeft*=x;
  //    padRight*=x;
  //    padTop*=y;
  //    padBottom*=y;
  //    this.scaleX_01=scaleX;
  //    this.scaleY_01=scaleY;
  //  }

  @Override
  public void getGlyphs(GlyphRun run,CharSequence str,int start,int end,Glyph lastGlyph) {
    int max=end-start;
    if(max==0) return;
    boolean markupEnabled=this.markupEnabled;
    float scaleX=this.scaleX;
    Array<Glyph> glyphs=run.glyphs;
    FloatArray xAdvances=run.xAdvances;

    // Guess at number of glyphs needed.
    glyphs.ensureCapacity(max);
    run.xAdvances.ensureCapacity(max+1);

    do {
      char ch=str.charAt(start++);
      if(ch=='\r') continue; // Ignore.
      Glyph glyph=getGlyph(ch);
      if(glyph==null) {
        if(missingGlyph==null) continue;
        glyph=missingGlyph;
      }
      glyphs.add(glyph);
      xAdvances.add(lastGlyph==null // First glyph on line, adjust the position so it isn't drawn left of 0.
        ?(glyph.fixedWidth?0:-glyph.xoffset*scaleX-padLeft)
        :(lastGlyph.xadvance+lastGlyph.getKerning(ch))*scaleX);
      lastGlyph=glyph;

      // "[[" is an escaped left square bracket, skip second character.
      if(markupEnabled&&ch=='['&&start<end&&str.charAt(start)=='[') start++;
    }while(start<end);
    if(lastGlyph!=null) {
      float lastGlyphWidth=lastGlyph.fixedWidth?lastGlyph.xadvance*scaleX
        :(lastGlyph.width+lastGlyph.xoffset)*scaleX-padRight;
      xAdvances.add(lastGlyphWidth);
    }
  }
  /**
   * Returns the glyph for the specified character, or null if no such glyph exists. Note that
   * {@link #getGlyphs(GlyphRun, CharSequence, int, int, Glyph)} should be be used to shape a
   * string of characters into a list of glyphs.
   */
  public Glyph getGlyph(char ch) {
    return p.getGlyph(ch);
  }
}
