package pama1234.gdx.util.font;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import pama1234.WebPToLibGDX;

public class WebpBitmapFontUtil{

  public static BitmapFont create(FileHandle fontFile,boolean flipped) {
    BitmapFontData data=new BitmapFontData(fontFile,flipped);
    int n=data.imagePaths.length;
    Array<TextureRegion> tr=new Array<>();
    for(int i=0;i<n;i++) {
      FileHandle file;
      if(data.fontFile==null) file=Gdx.files.internal(data.imagePaths[i]);
      else file=Gdx.files.getFileHandle(data.imagePaths[i],data.fontFile.type());
      Texture texture=null;
      try {
        texture=WebPToLibGDX.loadWebPAsTexture(file);
      }catch(IOException e) {
        throw new RuntimeException(e);
      }
      tr.add(new TextureRegion(texture));
    }
    BitmapFont font=new BitmapFont(data,tr,true);
    return font;
  }
}
