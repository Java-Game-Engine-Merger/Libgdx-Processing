package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;

public class LayerFontCache extends BitmapFontCache{
  public LayerFontCache(BitmapFont font) {
    super(font);
  }
  public LayerFontCache(BitmapFont font,boolean integer) {
    super(font,integer);
  }
}
