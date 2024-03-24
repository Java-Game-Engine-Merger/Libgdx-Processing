package pama1234.gdx.util.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.lyze.gdxtinyvg.TinyVG;
import pama1234.math.UtilMath;
import pama1234.math.geometry.RectI;
import pama1234.math.transform.Pose3D;
import space.earlygrey.shapedrawer.JoinType;

/**
 * 此中间类主要放渲染相关的东东
 * 
 * @see UtilScreen2D
 * @see UtilScreen3D
 */
public abstract class UtilScreenRender extends UtilScreenTextRender{
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
  //---------------------------------------------------------------------------
  public void clear() {
    ScreenUtils.clear(0,0,0,0,true);
  }
  public void background(int r,int g,int b) {
    ScreenUtils.clear(r/255f,g/255f,b/255f,1,true);
  }
  public void background(int gray,int a) {
    ScreenUtils.clear(gray/255f,gray/255f,gray/255f,a/255f,true);
  }
  public void background(Color color,int a) {
    ScreenUtils.clear(color.r,color.g,color.b,a/255f,true);
  }
  public void background(int in) {
    background(in,in,in);
  }
  public void background(Color in) {
    ScreenUtils.clear(in,true);
  }
  //---------------------------------------------------------------------------
  public void circle(float x,float y,float size) {//TODO
    int seg=circleSeg(size);
    circle(x,y,size,seg);
  }
  // TODO in alpha
  public void scircle(float x,float y,float size) {
    renderer(shapeDrawer.getBatch());
    if(fill) {
      shapeDrawer.setColor(fillColor);
      shapeDrawer.filledCircle(x,y,size);
    }
    if(stroke) {
      shapeDrawer.setColor(strokeColor);
      shapeDrawer.circle(x,y,size,JoinType.SMOOTH);
    }
  }
  public static int circleSeg(float s) {
    return UtilMath.max((int)(MathUtils.PI*s),6);
  }
  public void circle(float x,float y,float s,int seg) {
    if(fill) {
      renderer(rFill);
      rFill.circle(x,y,s,seg);
    }
    if(stroke) {
      renderer(rStroke);
      rStroke.circle(x,y,s,seg);
    }
  }
  public void circle(float x,float y,float z,float s,int seg) {
    pushMatrix();
    translate(0,0,z);
    circle(x,y,s,seg);
    popMatrix();
  }
  //---------------------------------------------------------------------------
  public void rect(float x,float y,float w,float h) {
    if(fill) {
      renderer(rFill);
      rFill.rect(x,y,w,h);
    }
    if(stroke) {
      renderer(rStroke);
      rStroke.rect(x,y,w,h);
    }
  }
  public void rect(float x,float y,float z,float w,float h) {
    pushMatrix();
    translate(0,0,z);
    rect(x,y,w,h);
    popMatrix();
  }
  public void rect(float x,float y,float w,float h,Pose3D pose) {
    pushMatrix();
    pose(pose);
    rect(x,y,w,h);
    popMatrix();
  }
  public void rect(RectI rect,Pose3D pose) {
    rect(rect.x(),rect.y(),rect.w(),rect.h(),pose);
  }
  public void rect(RectI rect) {
    rect(rect.x(),rect.y(),rect.w(),rect.h());
  }
  public void triangle(float x1,float y1,float x2,float y2,float x3,float y3) {
    if(fill) {
      renderer(rFill);
      rFill.triangle(x1,y1,x2,y2,x3,y3);
    }
    if(stroke) {
      renderer(rStroke);
      rStroke.triangle(x1,y1,x2,y2,x3,y3);
    }
  }
  public void polygon(PolygonRegion polygon,float x,float y) {
    if(fill) {
      renderer(pFill);
      pFill.draw(polygon,x,y);
    }
    if(stroke) {
      renderer(rStroke);
      rStroke.polygon(polygon,x,y);
    }
  }
  @Deprecated
  public void polygon(float[] array,int l) {
    if(fill) {
      renderer(pFill);
      pFill.polygon(array,0,l*2);
    }
    if(stroke) {
      renderer(rStroke);
      rStroke.polygon(array,0,l*2);
    }
  }
  public void quad(float x1,float y1,float x2,float y2,float x3,float y3,float x4,float y4) {
    if(fill) {
      renderer(rFill);
      rFill.polygonVarargs(x1,y1,x2,y2,x3,y3,x4,y4);
    }
    if(stroke) {
      renderer(rStroke);
      rStroke.polygonVarargs(x1,y1,x2,y2,x3,y3,x4,y4);
    }
  }
  //---------------------------------------------------------------------------
  @Deprecated
  public void arc(float x1,float y1,float x2,float y2,float a,float b) {
    //TODO
  }
  public void arc(float x,float y,float radius,float start,float degrees) {
    if(stroke) {
      renderer(rStroke);
      rStroke.arcNoBorder(x,y,radius,start,degrees,UtilMath.max(1,(int)(6*(float)Math.cbrt(radius)*(degrees/360))));
    }
  }
  public void dot(float x,float y,int color) {
    renderer(rFill);
    rFill.getColor().set(color);
    rFill.rect(x-0.5f,y-0.5f,1,1);
  }
  public void line(float x1,float y1,float x2,float y2) {
    if(stroke) {
      renderer(rStroke);
      rStroke.line(x1,y1,x2,y2);
    }
  }
  // TODO in alpha
  public void sline(float x1,float y1,float x2,float y2) {
    if(stroke) {
      renderer(shapeDrawer.getBatch());
      shapeDrawer.setColor(strokeColor);
      shapeDrawer.line(x1,y1,x2,y2);
    }
  }
  public void cross(float x,float y,float w,float h) {
    line(x-w,y,x+w,y);
    line(x,y-h,x,y+h);
  }
  @Deprecated
  public void border(float x,float y,float w,float h,float weight) {
    fill(128,128,128,204);
    fillRect(x,y,w,weight);
    fillRect(x,y,weight,h);
    fill(255,255,255,204);
    fillRect(x,y+h-weight,w,weight);
    fillRect(x+w-weight,y,weight,h);
  }
  public void fillRect(float x,float y,float w,float h) {
    renderer(rFill);
    rFill.rect(x,y,w,h);
  }
}