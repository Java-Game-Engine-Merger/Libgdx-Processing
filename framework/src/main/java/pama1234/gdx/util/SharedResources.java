package pama1234.gdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.lyze.gdxtinyvg.drawers.TinyVGShapeDrawer;
import pama1234.gdx.util.font.BetterBitmapFont;
import pama1234.gdx.util.font.layer.FontLayer;
import pama1234.gdx.util.font.layer.MultiLayerFont;
import pama1234.gdx.util.graphics.UtilPolygonSpriteBatch;
import pama1234.gdx.util.graphics.UtilShapeRenderer;
import pama1234.util.listener.Disposable;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * UtilScreen的共享绘制工具
 */
public class SharedResources implements Disposable{

  public static SharedResources instance=new SharedResources();
  public BetterBitmapFont font;
  public SpriteBatch imageBatch;
  public TinyVGShapeDrawer tvgDrawer;
  public UtilShapeRenderer rFill;
  public UtilShapeRenderer rStroke;
  public UtilPolygonSpriteBatch pFill;
  public ShapeDrawer shapeDrawer;

  {
    imageBatch=createSpriteBatch();
    font=createFont();
    rFill=new UtilShapeRenderer();
    rStroke=new UtilShapeRenderer();
    pFill=new UtilPolygonSpriteBatch();
    tvgDrawer=new TinyVGShapeDrawer(imageBatch);
    shapeDrawer=new ShapeDrawer(imageBatch);
  }

  public static SpriteBatch createSpriteBatch() {
    return new SpriteBatch(1000,ShaderUtil.createDefaultShader());
  }

  public static BetterBitmapFont createFont() {
    return createFont(true);
  }

  public static BetterBitmapFont createFont(boolean flip) {
    FileHandle[] fileHandles=new FileHandle[16];
    for(int i=0;i<16;i++) {
      fileHandles[i]=Gdx.files.internal("font/mapleMono/"+i+"/MapleMonoRegular-"+i+".fnt");
    }
    return new MultiLayerFont(new FontLayer[] {
      new FontLayer(fileHandles,16)
    });
  }

  @Override
  public void dispose() {
    font.dispose();
    imageBatch.dispose();
    tvgDrawer.dispose();
    rFill.dispose();
    rStroke.dispose();
    pFill.dispose();
  }
}
