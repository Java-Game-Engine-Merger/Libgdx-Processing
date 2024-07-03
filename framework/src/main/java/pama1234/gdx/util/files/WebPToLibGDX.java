package pama1234.gdx.util.files;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.twelvemonkeys.imageio.plugins.webp.WebPImageReaderSpi;

import pama1234.Tools;

public class WebPToLibGDX{
  static {
    // 注册 WebP 插件
    IIORegistry.getDefaultInstance().registerServiceProvider(new WebPImageReaderSpi());
  }

  public static Texture loadWebPAsTexture(FileHandle fileHandle) throws IOException {
    Tools.time();
    // 使用ImageIO读取WebP文件
    BufferedImage bufferedImage=readWebPImage(fileHandle);

    // 将BufferedImage转换为Pixmap
    Pixmap pixmap=convertBufferedImageToPixmap(bufferedImage);

    Tools.printPeriod();

    // 创建LibGDX的Texture
    return new Texture(pixmap);
  }

  private static BufferedImage readWebPImage(FileHandle fileHandle) throws IOException {
    return ImageIO.read(fileHandle.read());
  }

  private static Pixmap convertBufferedImageToPixmap(BufferedImage bufferedImage) {
    // 将BufferedImage转换为自定义的Pixmap
    return new BufferedImagePixmap(bufferedImage);
  }

  // 自定义的Pixmap类，包装BufferedImage
  private static class BufferedImagePixmap extends Pixmap{
    private final BufferedImage bufferedImage;

    public BufferedImagePixmap(BufferedImage bufferedImage) {
      super(bufferedImage.getWidth(),bufferedImage.getHeight(),Format.RGBA8888);
      this.bufferedImage=bufferedImage;
      loadPixels();
    }

    private void loadPixels() {
      int width=bufferedImage.getWidth();
      int height=bufferedImage.getHeight();
      ByteBuffer buffer=getPixels();

      // 使用并行流来处理像素转换
      IntStream.range(0,height).parallel().forEach(y-> {
        for(int x=0;x<width;x++) {
          int argb=bufferedImage.getRGB(x,y);
          int a=(argb>>24)&0xFF;
          int r=(argb>>16)&0xFF;
          int g=(argb>>8)&0xFF;
          int b=argb&0xFF;
          int index=(x+y*width)*4;
          buffer.put(index,(byte)r);
          buffer.put(index+1,(byte)g);
          buffer.put(index+2,(byte)b);
          buffer.put(index+3,(byte)a);
        }
      });
    }
  }

  public static void main(String[] args) {
    try {
      Texture texture=loadWebPAsTexture(new FileHandle("path/to/your/webp/file"));
      // 使用texture
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
}