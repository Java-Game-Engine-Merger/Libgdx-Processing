package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pools;

import pama1234.gdx.util.font.FastGlyphLayout;

public class LayerFontCache extends BitmapFontCache{

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

  /** Removes all glyphs in the cache. */
  public void clear() {
    super.clear();

    Pools.freeAll(pooledLayouts,true);
    pooledLayouts.clear();
    for(int j=0;j<pageGlyphIndices.length;j++) {
      if(idx[j]==null) continue;
      for(int i=0,n=idx[j].length;i<n;i++) {
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
