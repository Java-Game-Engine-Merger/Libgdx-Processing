package pama1234.gdx;

import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public interface ImageLoadUtil{
  public Texture loadWebPAsTexture(FileHandle fileHandle) throws IOException;
}
