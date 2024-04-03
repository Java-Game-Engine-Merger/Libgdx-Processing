package pama1234.gdx.util.font.layer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontLayer{

  public FileHandle[] fontFile;
  /** 区块大小，一般为2的幂 */
  public int length;
  /** 是否按需加载 */
  public boolean loadOnDemand;
  /** log2(length) */
  public int digitShift;
  /** 每个区块加载时会产生一个BitmapFont */
  public BitmapFont[] dataM;
}
