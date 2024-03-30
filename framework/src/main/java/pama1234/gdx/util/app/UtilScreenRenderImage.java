package pama1234.gdx.util.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import dev.lyze.gdxtinyvg.TinyVG;

public abstract class UtilScreenRenderImage extends UtilScreenRenderText{

  //---------------------------------------------------------------------------

  public void model(ModelInstance in) {
    renderer(modelBatch);
    modelBatch.render(in);
  }
  @Deprecated
  public void modelFlush(ModelInstance in) {
    model(in);
    flushModel();
  }
  @Deprecated
  public void flushModel() {
    //        endShape();
    modelBatch.flush();
    //        beginShape();
  }

  public void depth(boolean flag) {
    depth=flag;
  }

  //---------------------------------------------------------------------------

  public void tvg(TinyVG in) {//TODO
    renderer(imageBatch);
    in.draw(tvgDrawer);
  }
  public void image(Texture in,float x,float y) {
    renderer(imageBatch);
    imageBatch.draw(in,x,y);
  }
  @Deprecated
  public void image(Texture in,float x,float y,float z) {
    renderer(imageBatch);
    imageBatch.draw(in,x,y);
  }
  public void image(TextureRegion in,float x,float y) {
    renderer(imageBatch);
    imageBatch.draw(in,x,y);
  }
  public void image(Texture in,float x,float y,float w,float h) {
    renderer(imageBatch);
    imageBatch.draw(in,x,y,w,h);
  }
  public void image(TextureRegion in,float x,float y,ShaderProgram shader) {
    renderer(imageBatch);
    imageBatch.setShader(shader);
    imageBatch.draw(in,x,y);
    imageBatch.setShader(null);
  }
  public void image(Texture in,float x,float y,float w,float h,ShaderProgram shader) {
    renderer(imageBatch);
    imageBatch.setShader(shader);
    imageBatch.draw(in,x,y,w,h);
    imageBatch.setShader(null);
  }
  public void imageCenterPos(Texture in,float x,float y,float w,float h) {
    renderer(imageBatch);
    imageBatch.draw(in,x-w/2,y-h/2,w,h);
  }
  @Deprecated
  public void imageCenterPos(Texture in,float x,float y,float z,float w,float h) {
    pushMatrix();
    translate(0,0,z);
    renderer(imageBatch);
    imageBatch.draw(in,x-w/2,y-h/2,w,h);
    popMatrix();
  }
  public void image(TextureRegion in,float x,float y,float w,float h) {
    renderer(imageBatch);
    innerImage(in,x,y,w,h);
  }
  public void innerImage(TextureRegion in,float x,float y,float w,float h) {
    imageBatch.draw(in,x,y,w,h);
  }
  public void sprite(Sprite in) {
    renderer(imageBatch);
    in.draw(imageBatch);
  }

}
