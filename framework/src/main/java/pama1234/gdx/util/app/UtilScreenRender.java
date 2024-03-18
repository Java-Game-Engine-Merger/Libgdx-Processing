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
  //---------------------------------------------------------------------------
  public void image(Texture in,float x,float y) {
    imageBatch.begin();
    imageBatch.draw(in,x,y);
    imageBatch.end();
  }
  public void tvg(TinyVG in) {//TODO
    imageBatch.begin();
    in.draw(tvgDrawer);
    imageBatch.end();
  }
  @Deprecated
  public void image(Texture in,float x,float y,float z) {
    imageBatch.begin();
    imageBatch.draw(in,x,y);
    imageBatch.end();
  }
  public void image(TextureRegion in,float x,float y) {
    imageBatch.begin();
    imageBatch.draw(in,x,y);
    imageBatch.end();
  }
  public void image(Texture in,float x,float y,float w,float h) {
    imageBatch.begin();
    imageBatch.draw(in,x,y,w,h);
    imageBatch.end();
  }
  public void image(TextureRegion in,float x,float y,ShaderProgram shader) {
    imageBatch.begin();
    imageBatch.setShader(shader);
    imageBatch.draw(in,x,y);
    imageBatch.setShader(null);
    imageBatch.end();
  }
  public void image(Texture in,float x,float y,float w,float h,ShaderProgram shader) {
    imageBatch.begin();
    imageBatch.setShader(shader);
    imageBatch.draw(in,x,y,w,h);
    imageBatch.setShader(null);
    imageBatch.end();
  }
  public void imageCenterPos(Texture in,float x,float y,float w,float h) {
    imageBatch.begin();
    imageBatch.draw(in,x-w/2,y-h/2,w,h);
    imageBatch.end();
  }
  @Deprecated
  public void imageCenterPos(Texture in,float x,float y,float z,float w,float h) {
    pushMatrix();
    translate(0,0,z);
    imageBatch.begin();
    imageBatch.draw(in,x-w/2,y-h/2,w,h);
    imageBatch.end();
    popMatrix();
  }
  public void image(TextureRegion in,float x,float y,float w,float h) {
    imageBatch.begin();
    innerImage(in,x,y,w,h);
    imageBatch.end();
  }
  public void innerImage(TextureRegion in,float x,float y,float w,float h) {
    imageBatch.draw(in,x,y,w,h);
  }
  public void sprite(Sprite in) {
    imageBatch.begin();
    in.draw(imageBatch);
    imageBatch.end();
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
    shapeDrawer.getBatch().begin();
    if(fill) {
      shapeDrawer.setColor(fillColor);
      shapeDrawer.filledCircle(x,y,size);
    }
    if(stroke) {
      shapeDrawer.setColor(strokeColor);
      shapeDrawer.circle(x,y,size,JoinType.SMOOTH);
    }
    shapeDrawer.getBatch().end();
  }
  public static int circleSeg(float s) {
    return UtilMath.max((int)(MathUtils.PI*s),6);
  }
  public void circle(float x,float y,float s,int seg) {
    if(fill) {
      rFill.circle(x,y,s,seg);
      rFill.flush();
    }
    if(stroke) {
      rStroke.circle(x,y,s,seg);
      rStroke.flush();
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
      rFill.rect(x,y,w,h);
      rFill.flush();
    }
    if(stroke) {
      rStroke.rect(x,y,w,h);
      rStroke.flush();
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
      rFill.triangle(x1,y1,x2,y2,x3,y3);
      rFill.flush();
    }
    if(stroke) {
      rStroke.triangle(x1,y1,x2,y2,x3,y3);
      rStroke.flush();
    }
  }
  public void polygon(PolygonRegion polygon,float x,float y) {
    if(fill) {
      pFill.begin();
      pFill.draw(polygon,x,y);
      pFill.flush();
      pFill.end();
    }
    if(stroke) {
      rStroke.polygon(polygon,x,y);
      rStroke.flush();
    }
  }
  @Deprecated
  public void polygon(float[] array,int l) {
    if(fill) {
      pFill.begin();
      pFill.polygon(array,0,l*2);
      pFill.flush();
      pFill.end();
    }
    if(stroke) {
      rStroke.polygon(array,0,l*2);
      rStroke.flush();
    }
  }
  public void quad(float x1,float y1,float x2,float y2,float x3,float y3,float x4,float y4) {
    if(fill) {
      rFill.polygonVarargs(x1,y1,x2,y2,x3,y3,x4,y4);
      rFill.flush();
    }
    if(stroke) {
      rStroke.polygonVarargs(x1,y1,x2,y2,x3,y3,x4,y4);
      rStroke.flush();
    }
  }
  //---------------------------------------------------------------------------
  @Deprecated
  public void arc(float x1,float y1,float x2,float y2,float a,float b) {
    //TODO
  }
  public void arc(float x,float y,float radius,float start,float degrees) {
    if(stroke) {
      rStroke.arcNoBorder(x,y,radius,start,degrees,UtilMath.max(1,(int)(6*(float)Math.cbrt(radius)*(degrees/360))));
      rStroke.flush();
    }
  }
  public void dot(float x,float y,int color) {
    rFill.getColor().set(color);
    rFill.rect(x-0.5f,y-0.5f,1,1);
    rFill.flush();
  }
  public void line(float x1,float y1,float x2,float y2) {
    if(stroke) {
      // rStroke.renderer.setShader(PGraphicsOpenGL.);
      rStroke.line(x1,y1,x2,y2);
      rStroke.flush();
      // rFill.line(x1,y1,x2,y2);
      // rFill.flush();
    }
  }
  // TODO in alpha
  public void sline(float x1,float y1,float x2,float y2) {
    if(stroke) {
      shapeDrawer.getBatch().begin();
      shapeDrawer.setColor(strokeColor);
      //      shapeDrawer.setDefaultLineWidth(strokeWeight/4f);
      shapeDrawer.line(x1,y1,x2,y2);
      shapeDrawer.getBatch().end();
      //      shapeDrawer.getBatch().flush();
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
    rFill.rect(x,y,w,h);
    rFill.flush();
  }
}