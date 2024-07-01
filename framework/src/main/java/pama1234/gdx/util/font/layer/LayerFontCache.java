package pama1234.gdx.util.font.layer;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.*;

import pama1234.gdx.util.font.FastGlyphLayout;

public class LayerFontCache extends BitmapFontCache{

  @Deprecated
  private static final int NEED_REFACTOR=0;

  /** 每页的顶点数据。 */
  private float[][][] pageVertices;
  private float currentTint;

  /** 每页的顶点数据条目数。 */
  private int[][] idx;

  /** 对于每一页，一个数组，其中每个值对应该页中的每个字形， 该值是缓存的完整文本中字符的索引。 */
  private IntArray[][] pageGlyphIndices;

  /** 内部使用，用于确保多页字体顶点数据的正确容量。 */
  private int[][] tempGlyphCount;
  private int glyphCount;
  private CharSequence s;
  private MultiLayerFont font;

  private final Array<GlyphLayout> pooledLayouts=new Array<>();

  public LayerFontCache(MultiLayerFont font) {
    this(font,font.usesIntegerPositions());
  }
  public LayerFontCache(MultiLayerFont font,boolean integer) {
    super(font,integer);
    this.font=font;

    FontLayer fontLayer=font.fontLayers[0];
    int chunkLength=fontLayer.length;
    pageVertices=new float[chunkLength][][];
    idx=new int[chunkLength][];
    pageGlyphIndices=new IntArray[chunkLength][];
    tempGlyphCount=new int[chunkLength][];

    loadChunk(0);
  }

  public void loadChunk(int posOfChar) {
    FontLayer fontLayer=font.fontLayers[0];
    int i=posOfChar;

    fontLayer.loadChunk(posOfChar);

    int pageCount=fontLayer.dataM[i].getRegions().size;
    if(pageCount==0) throw new IllegalArgumentException("The specified font must contain at least one texture page.");
    pageVertices[i]=new float[pageCount][];
    idx[i]=new int[pageCount];
    if(pageCount>1) {
      pageGlyphIndices[i]=new IntArray[pageCount];
      for(int j=0,n=pageGlyphIndices[i].length;j<n;j++) pageGlyphIndices[i][j]=new IntArray();
    }
    tempGlyphCount[i]=new int[pageCount];
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
    s=str;
    FastGlyphLayout layout=Pools.obtain(FastGlyphLayout.class);
    pooledLayouts.add(layout);
    layout.setText(getFont(),str,start,end,getColor(),targetWidth,halign,wrap,truncate);
    addText(layout,x,y);
    return layout;
  }

  /**
   * Adds the specified glyphs.
   * 
   * @param layout The cache keeps the layout until cleared or new text is set. The layout should
   *               not be modified before then.
   */
  public void addText(GlyphLayout layout,float x,float y) {
    addToCache(layout,x,y+font.data.ascent);
  }

  private void addToCache(GlyphLayout layout,float x,float y) {
    int runCount=layout.runs.size;
    if(runCount==0) return;

    // Check if the number of font pages has changed.
    if(pageVertices.length<font.getRegions().size) setPageCount(font.getRegions().size);

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
    System.arraycopy(pageVertices[NEED_REFACTOR],0,newPageVertices,0,pageVertices[NEED_REFACTOR].length);
    pageVertices[NEED_REFACTOR]=newPageVertices;

    int[] newIdx=new int[pageCount];
    System.arraycopy(idx[NEED_REFACTOR],0,newIdx,0,idx[NEED_REFACTOR].length);
    idx[NEED_REFACTOR]=newIdx;

    IntArray[] newPageGlyphIndices=new IntArray[pageCount];
    int pageGlyphIndicesLength=0;
    if(pageGlyphIndices[NEED_REFACTOR]!=null) {
      pageGlyphIndicesLength=pageGlyphIndices[NEED_REFACTOR].length;
      System.arraycopy(pageGlyphIndices[NEED_REFACTOR],0,newPageGlyphIndices,0,pageGlyphIndices[NEED_REFACTOR].length);
    }
    for(int i=pageGlyphIndicesLength;i<pageCount;i++) newPageGlyphIndices[i]=new IntArray();
    pageGlyphIndices[NEED_REFACTOR]=newPageGlyphIndices;

    tempGlyphCount[NEED_REFACTOR]=new int[pageCount];
  }

  private void requireGlyphs(GlyphLayout layout) {
    if(pageVertices.length==1) {
      // Simple if we just have one page.
      requirePageGlyphs(0,layout.glyphCount);
    }else {
      int[] tempGlyphCount=this.tempGlyphCount[NEED_REFACTOR];
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
    if(pageGlyphIndices[NEED_REFACTOR]!=null) {
      if(glyphCount>pageGlyphIndices[NEED_REFACTOR][page].items.length) pageGlyphIndices[NEED_REFACTOR][page].ensureCapacity(glyphCount-pageGlyphIndices[NEED_REFACTOR][page].size);
    }

    int vertexCount=idx[NEED_REFACTOR][page]+glyphCount*20;
    float[] vertices=pageVertices[NEED_REFACTOR][page];
    if(vertices==null) {
      pageVertices[NEED_REFACTOR][page]=new float[vertexCount];
    }else if(vertices.length<vertexCount) {
      float[] newVertices=new float[vertexCount];
      System.arraycopy(vertices,0,newVertices,0,idx[NEED_REFACTOR][page]);
      pageVertices[NEED_REFACTOR][page]=newVertices;
    }
  }

  private void addGlyph(Glyph glyph,float x,float y,float color) {
    final float scaleX=font.data.scaleX,scaleY=font.data.scaleY;
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
    int idx=this.idx[NEED_REFACTOR][page];
    this.idx[NEED_REFACTOR][page]+=20;

    if(pageGlyphIndices[NEED_REFACTOR]!=null) pageGlyphIndices[NEED_REFACTOR][page].add(glyphCount++);

    final float[] vertices=pageVertices[NEED_REFACTOR][page];
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

  /** Removes all glyphs in the cache. */
  public void clear() {
    super.clear();

    Pools.freeAll(pooledLayouts,true);
    pooledLayouts.clear();
    for(int j=0;j<pageGlyphIndices.length;j++) {
      if(pageGlyphIndices[j]==null) continue;
      for(int i=0,n=pageGlyphIndices[j].length;i<n;i++) {
        if(pageGlyphIndices[j][i]!=null) pageGlyphIndices[j][i].clear();
      }
    }
    for(int j=0;j<idx.length;j++) {
      if(idx[j]==null) continue;
      for(int i=0,n=idx[j].length;i<n;i++) {
        idx[j][i]=0;
      }
    }
  }

  @Override
  public void draw(Batch spriteBatch) {
    Array<TextureRegion> regions=font.getRegions();
    for(int i=0;i<pageVertices.length;i++) {
      if(pageVertices[i]!=null) for(int j=0,n=pageVertices[i].length;j<n;j++) {
        if(idx[i][j]>0) { // ignore if this texture has no glyphs
          float[] vertices=pageVertices[i][j];
          spriteBatch.draw(regions.get(j).getTexture(),vertices,0,idx[i][j]);
        }
      }
    }
  }

  @Override
  public void draw(Batch spriteBatch,int start,int end) {
    for(int j=0;j<pageVertices.length;j++) {
      if(pageVertices==null) continue;

      if(pageVertices[j].length==1) { // 1 page.
        spriteBatch.draw(font.getRegion().getTexture(),pageVertices[j][0],start*20,(end-start)*20);
        return;
      }

      // Determine vertex offset and count to render for each page. Some pages might not need to be rendered at all.
      Array<TextureRegion> regions=font.getRegions();
      for(int i=0,pageCount=pageVertices[j].length;i<pageCount;i++) {
        int offset=-1,count=0;

        // For each set of glyph indices, determine where to begin within the start/end bounds.
        IntArray glyphIndices=pageGlyphIndices[j][i];
        for(int ii=0,n=glyphIndices.size;ii<n;ii++) {
          int glyphIndex=glyphIndices.get(ii);

          // Break early if the glyph is out of bounds.
          if(glyphIndex>=end) break;

          // Determine if this glyph is within bounds. Use the first match of that for the offset.
          if(offset==-1&&glyphIndex>=start) offset=ii;

          // Determine the vertex count by counting glyphs within bounds.
          if(glyphIndex>=start) count++;
        }

        // Page doesn't need to be rendered.
        if(offset==-1||count==0) continue;

        // Render the page vertex data with the offset and count.
        spriteBatch.draw(regions.get(i).getTexture(),pageVertices[j][i],offset*20,count*20);
      }
    }
  }
}
