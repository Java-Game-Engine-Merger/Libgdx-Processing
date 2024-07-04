package pama1234.gdx.android;

import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import pama1234.gdx.ImageLoadUtil;

public class AndroidImageLoadUtil implements ImageLoadUtil{
  public static Texture loadWebPAsTexture(InputStream inputStream) throws IOException {
    // 使用BitmapFactory读取WebP文件
    Bitmap bitmap=BitmapFactory.decodeStream(inputStream);

    // 将Bitmap转换为Pixmap
    Pixmap pixmap=convertBitmapToPixmap(bitmap);

    // 创建LibGDX的Texture
    return new Texture(pixmap);
  }

  private static Pixmap convertBitmapToPixmap(Bitmap bitmap) {
    int width=bitmap.getWidth();
    int height=bitmap.getHeight();
    Pixmap pixmap=new Pixmap(width,height,Format.RGBA8888);

    for(int y=0;y<height;y++) {
      for(int x=0;x<width;x++) {
        int argb=bitmap.getPixel(x,y);
        int rgba=((argb&0xFF000000)>>>24)|((argb&0x00FF0000)<<16)|((argb&0x0000FF00)<<16)|((argb&0x000000FF)<<16);
        pixmap.drawPixel(x,y,rgba);
      }
    }

    return pixmap;
  }

  @Override
  public Texture loadWebPAsTexture(FileHandle fileHandle) throws IOException {
    return loadWebPAsTexture(fileHandle.read());
  }
}
