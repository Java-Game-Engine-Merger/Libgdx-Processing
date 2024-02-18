package pama1234.gdx.util.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BetterBitmapFont extends BitmapFont{
  public BetterBitmapFont() {}
  public BetterBitmapFont(boolean flip) {
    super(flip);
  }
  public BetterBitmapFont(FileHandle fontFile,TextureRegion region) {
    super(fontFile,region);
  }
  public BetterBitmapFont(FileHandle fontFile,TextureRegion region,boolean flip) {
    super(fontFile,region,flip);
  }
  public BetterBitmapFont(FileHandle fontFile) {
    super(fontFile);
  }
  public BetterBitmapFont(FileHandle fontFile,boolean flip) {
    super(fontFile,flip);
  }
  public BetterBitmapFont(FileHandle fontFile,FileHandle imageFile,boolean flip) {
    super(fontFile,imageFile,flip);
  }
  public BetterBitmapFont(FileHandle fontFile,FileHandle imageFile,boolean flip,boolean integer) {
    super(fontFile,imageFile,flip,integer);
  }
  public BetterBitmapFont(BitmapFontData data,TextureRegion region,boolean integer) {
    super(data,region,integer);
  }
  public BetterBitmapFont(BitmapFontData data,Array<TextureRegion> pageRegions,boolean integer) {
    super(data,pageRegions,integer);
  }

}
