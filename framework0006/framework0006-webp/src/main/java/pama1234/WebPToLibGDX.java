package pama1234;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.twelvemonkeys.imageio.plugins.webp.WebPImageReaderSpi;

public class WebPToLibGDX{
  static {
    // 注册 WebP 插件
    IIORegistry.getDefaultInstance().registerServiceProvider(new WebPImageReaderSpi());
  }
  public static Texture loadWebPAsTexture(FileHandle fileHandle) throws IOException {
    // 使用ImageIO读取WebP文件
    BufferedImage bufferedImage=readWebPImage(fileHandle);

    // 将BufferedImage转换为Pixmap
    Pixmap pixmap=convertBufferedImageToPixmap(bufferedImage);

    // 创建LibGDX的Texture
    return new Texture(pixmap);
  }

  private static BufferedImage readWebPImage(FileHandle fileHandle) throws IOException {
//    ImageInputStream input=ImageIO.createImageInputStream(fileHandle.read());
//    Iterator<ImageReader> readers=ImageIO.getImageReaders(input);
//
//    if(!readers.hasNext()) {
//      throw new IOException("No ImageReader found for given format.");
//    }
//
//    ImageReader reader=readers.next();
//    reader.setInput(input);
//    return reader.read(0);
    return ImageIO.read(fileHandle.read());
  }

  private static Pixmap convertBufferedImageToPixmap(BufferedImage bufferedImage) {
    int width=bufferedImage.getWidth();
    int height=bufferedImage.getHeight();
    Pixmap pixmap=new Pixmap(width,height,Format.RGBA8888);

    for(int y=0;y<height;y++) {
      for(int x=0;x<width;x++) {
        int argb=bufferedImage.getRGB(x,y);
        int rgba=((argb&0xFF000000)>>>24)|((argb&0x00FF0000)<<16)|((argb&0x0000FF00)<<16)|((argb&0x000000FF)<<16);
        pixmap.drawPixel(x,y,rgba);
      }
    }

    return pixmap;
  }

  public static void main(String[] args) {
    try {
      Texture texture=loadWebPAsTexture(new FileHandle(""));
      // 使用texture
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
}