package pama1234.gdx.util.element;

import com.badlogic.gdx.graphics.Color;
import pama1234.gdx.util.font.FontUtil.UniFontDependent;

public class FontStyle{
  @UniFontDependent
  public float defaultSize;
  public float size;
  public Color foreground;
  public Color background;
  public float scale;
  {
    defaultSize=16;
    size=defaultSize;
    foreground=Color.WHITE;
    background=null;
    scale=1;
  }
}
