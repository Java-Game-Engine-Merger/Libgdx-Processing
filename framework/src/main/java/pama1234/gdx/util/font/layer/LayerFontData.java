package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

public class LayerFontData extends BitmapFontData{
  static private final int LOG2_PAGE_SIZE=9;
  static private final int PAGE_SIZE=1<<LOG2_PAGE_SIZE;
  static private final int PAGES=0x10000/PAGE_SIZE;

  public MultiLayerFont p;
  public LayerFontData(MultiLayerFont multiLayerFont) {
    p=multiLayerFont;
  }

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
    //    Glyph[] page=glyphs[ch/PAGE_SIZE];
    //    if(page!=null) return page[ch&PAGE_SIZE-1];
    //    return null;
  }
}
