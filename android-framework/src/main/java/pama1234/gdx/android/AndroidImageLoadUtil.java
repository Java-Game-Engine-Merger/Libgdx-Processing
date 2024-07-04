package pama1234.gdx.android;

import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import pama1234.gdx.ImageLoadUtil;

public class AndroidImageLoadUtil implements ImageLoadUtil{
  public static Texture loadWebPAsTexture(InputStream inputStream) throws IOException {
    // 使用BitmapFactory读取WebP文件
    Bitmap bitmap=BitmapFactory.decodeStream(inputStream);

    Texture tex=new Texture(bitmap.getWidth(),bitmap.getHeight(),Format.RGBA8888);
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,tex.getTextureObjectHandle());
    GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
    bitmap.recycle();

    // 创建LibGDX的Texture
    return tex;
  }

  @Override
  public Texture loadWebPAsTexture(FileHandle fileHandle) throws IOException {
    return loadWebPAsTexture(fileHandle.read());
  }
}
