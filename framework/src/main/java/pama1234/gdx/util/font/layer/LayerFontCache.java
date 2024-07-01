package pama1234.gdx.util.font.layer;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.utils.*;

import pama1234.gdx.util.font.FastGlyphLayout;

public class LayerFontCache extends BitmapFontCache{

  /** Vertex data per page. */
  private float[][] pageVertices;
  private float currentTint;
  /** Number of vertex data entries per page. */
  private int[] idx;
  /**
   * For each page, an array with a value for each glyph from that page, where the value is the
   * index of the character in the full text being cached.
   */
  private IntArray[] pageGlyphIndices;
  /** Used internally to ensure a correct capacity for multi-page font vertex data. */
  private int[] tempGlyphCount;
  private int glyphCount;

  private final Array<GlyphLayout> pooledLayouts=new Array<>();
  public LayerFontCache(BitmapFont font) {
    this(font,font.usesIntegerPositions());
  }
  public LayerFontCache(BitmapFont font,boolean integer) {
    super(font,integer);

    int pageCount=font.getRegions().size;
    if(pageCount==0) throw new IllegalArgumentException("The specified font must contain at least one texture page.");
    pageVertices=new float[pageCount][];
    idx=new int[pageCount];
    if(pageCount>1) {
      // Contains the indices of the glyph in the cache as they are added.
      pageGlyphIndices=new IntArray[pageCount];
      for(int i=0,n=pageGlyphIndices.length;i<n;i++) pageGlyphIndices[i]=new IntArray();
    }
    tempGlyphCount=new int[pageCount];
  }
  /**
   * Adds glyphs for the specified text.
   *
   * @see #addText(CharSequence, float, float, int, int, float, int, boolean, String)
   */
  public FastGlyphLayout addFastText(CharSequence str,float x,float y) {
    return addFastText(str,x,y,0,str.length(),0,Align.left,false,null);
  }
  /**
   * Adds glyphs for the specified text.
   *
   * @see #addText(CharSequence, float, float, int, int, float, int, boolean, String)
   */
  public FastGlyphLayout addFastText(CharSequence str,float x,float y,float targetWidth,int halign,boolean wrap) {
    return addFastText(str,x,y,0,str.length(),targetWidth,halign,wrap,null);
  }
  /**
   * Adds glyphs for the specified text.
   *
   * @see #addText(CharSequence, float, float, int, int, float, int, boolean, String)
   */
  public FastGlyphLayout addFastText(CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,
    boolean wrap) {
    return addFastText(str,x,y,start,end,targetWidth,halign,wrap,null);
  }
  /**
   * Adds glyphs for the the specified text.
   *
   * @param x           The x position for the left most character.
   * @param y           The y position for the top of most capital letters in the font (the
   *                    {@link BitmapFontData#capHeight cap height}).
   * @param start       The first character of the string to draw.
   * @param end         The last character of the string to draw (exclusive).
   * @param targetWidth The width of the area the text will be drawn, for wrapping or truncation.
   * @param halign      Horizontal alignment of the text, see {@link Align}.
   * @param wrap        If true, the text will be wrapped within targetWidth.
   * @param truncate    If not null, the text will be truncated within targetWidth with this
   *                    string appended. May be an empty string.
   * @return The glyph layout for the cached string (the layout's height is the distance from y to
   *         the baseline).
   */
  public FastGlyphLayout addFastText(CharSequence str,float x,float y,int start,int end,float targetWidth,int halign,
    boolean wrap,String truncate) {
    FastGlyphLayout layout=Pools.obtain(FastGlyphLayout.class);
    pooledLayouts.add(layout);
    layout.setText(getFont(),str,start,end,getColor(),targetWidth,halign,wrap,truncate);
    addText(layout,x,y);
    return layout;
  }
  /** Removes all glyphs in the cache. */
  public void clear() {
    super.clear();
    pooledLayouts.clear();
  }

  /**
   * Adds the specified glyphs.
   *
   * @param layout The cache keeps the layout until cleared or new text is set. The layout should
   *               not be modified before then.
   */
  public void addText(GlyphLayout layout,float x,float y) {
    addToCache(layout,x,y+getFont().getData().ascent);
  }

  private void addToCache(GlyphLayout layout,float x,float y) {
    int runCount=layout.runs.size;
    if(runCount==0) return;

    // Check if the number of font pages has changed.
    if(pageVertices.length<getFont().getRegions().size) setPageCount(getFont().getRegions().size);

    getLayouts().add(layout);
    requireGlyphs(layout);

    IntArray colors=layout.colors;
    int colorsIndex=0,nextColorGlyphIndex=0,glyphIndex=0;
    float lastColorFloatBits=0;
    for(int i=0;i<runCount;i++) {
      GlyphRun run=layout.runs.get(i);
      Object[] glyphs=run.glyphs.items;
      float[] xAdvances=run.xAdvances.items;
      float gx=x+run.x,gy=y+run.y;
      for(int ii=0,nn=run.glyphs.size;ii<nn;ii++) {
        if(glyphIndex++==nextColorGlyphIndex) {
          lastColorFloatBits=NumberUtils.intToFloatColor(colors.get(++colorsIndex));
          nextColorGlyphIndex=++colorsIndex<colors.size?colors.get(colorsIndex):-1;
        }
        gx+=xAdvances[ii];
        addGlyph((Glyph)glyphs[ii],gx,gy,lastColorFloatBits);
      }
    }

    currentTint=Color.WHITE_FLOAT_BITS; // Cached glyphs have changed, reset the current tint.
  }

  private void setPageCount(int pageCount) {
    float[][] newPageVertices=new float[pageCount][];
    System.arraycopy(pageVertices,0,newPageVertices,0,pageVertices.length);
    pageVertices=newPageVertices;

    int[] newIdx=new int[pageCount];
    System.arraycopy(idx,0,newIdx,0,idx.length);
    idx=newIdx;

    IntArray[] newPageGlyphIndices=new IntArray[pageCount];
    int pageGlyphIndicesLength=0;
    if(pageGlyphIndices!=null) {
      pageGlyphIndicesLength=pageGlyphIndices.length;
      System.arraycopy(pageGlyphIndices,0,newPageGlyphIndices,0,pageGlyphIndices.length);
    }
    for(int i=pageGlyphIndicesLength;i<pageCount;i++) newPageGlyphIndices[i]=new IntArray();
    pageGlyphIndices=newPageGlyphIndices;

    tempGlyphCount=new int[pageCount];
  }

  private void requireGlyphs(GlyphLayout layout) {
    if(pageVertices.length==1) {
      // Simple if we just have one page.
      requirePageGlyphs(0,layout.glyphCount);
    }else {
      int[] tempGlyphCount=this.tempGlyphCount;
      Arrays.fill(tempGlyphCount,0);
      // Determine # of glyphs in each page.
      for(int i=0,n=layout.runs.size;i<n;i++) {
        Array<Glyph> glyphs=layout.runs.get(i).glyphs;
        Object[] glyphItems=glyphs.items;
        for(int ii=0,nn=glyphs.size;ii<nn;ii++) tempGlyphCount[((Glyph)glyphItems[ii]).page]++;
      }
      // Require that many for each page.
      for(int i=0,n=tempGlyphCount.length;i<n;i++) requirePageGlyphs(i,tempGlyphCount[i]);
    }
  }

  private void requirePageGlyphs(int page,int glyphCount) {
    if(pageGlyphIndices!=null) {
      if(glyphCount>pageGlyphIndices[page].items.length) pageGlyphIndices[page].ensureCapacity(glyphCount-pageGlyphIndices[page].size);
    }

    int vertexCount=idx[page]+glyphCount*20;
    float[] vertices=pageVertices[page];
    if(vertices==null) {
      pageVertices[page]=new float[vertexCount];
    }else if(vertices.length<vertexCount) {
      float[] newVertices=new float[vertexCount];
      System.arraycopy(vertices,0,newVertices,0,idx[page]);
      pageVertices[page]=newVertices;
    }
  }

  private void addGlyph(Glyph glyph,float x,float y,float color) {
    final float scaleX=getFont().getData().scaleX,scaleY=getFont().getData().scaleY;
    x+=glyph.xoffset*scaleX;
    y+=glyph.yoffset*scaleY;
    float width=glyph.width*scaleX,height=glyph.height*scaleY;
    final float u=glyph.u,u2=glyph.u2,v=glyph.v,v2=glyph.v2;

    if(usesIntegerPositions()) {
      x=Math.round(x);
      y=Math.round(y);
      width=Math.round(width);
      height=Math.round(height);
    }
    final float x2=x+width,y2=y+height;

    final int page=glyph.page;
    int idx=this.idx[page];
    this.idx[page]+=20;

    if(pageGlyphIndices!=null) pageGlyphIndices[page].add(glyphCount++);

    final float[] vertices=pageVertices[page];
    vertices[idx++]=x;
    vertices[idx++]=y;
    vertices[idx++]=color;
    vertices[idx++]=u;
    vertices[idx++]=v;

    vertices[idx++]=x;
    vertices[idx++]=y2;
    vertices[idx++]=color;
    vertices[idx++]=u;
    vertices[idx++]=v2;

    vertices[idx++]=x2;
    vertices[idx++]=y2;
    vertices[idx++]=color;
    vertices[idx++]=u2;
    vertices[idx++]=v2;

    vertices[idx++]=x2;
    vertices[idx++]=y;
    vertices[idx++]=color;
    vertices[idx++]=u2;
    vertices[idx]=v;
  }
}
