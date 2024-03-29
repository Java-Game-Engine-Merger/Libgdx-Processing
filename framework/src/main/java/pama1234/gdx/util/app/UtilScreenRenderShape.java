package pama1234.gdx.util.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
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
public abstract class UtilScreenRenderShape extends UtilScreenRenderImage{

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

  public void circle(float x,float y,float size) {
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
  // TODO pass segment size to shapeDrawer
  public void circle(float x,float y,float size,int seg) {
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
  public void circle(float x,float y,float z,float s,int seg) {
    pushMatrix();
    translate(0,0,z);
    circle(x,y,s,seg);
    popMatrix();
  }

  //---------------------------------------------------------------------------

  public void rect(float x,float y,float w,float h) {
    renderer(shapeDrawer.getBatch());
    if(fill) {
      shapeDrawer.setColor(fillColor);
      shapeDrawer.filledRectangle(x,y,w,h);
    }
    if(stroke) {
      shapeDrawer.setColor(strokeColor);
      shapeDrawer.rectangle(x,y,w,h);
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
    renderer(shapeDrawer.getBatch());
    if(fill) {
      shapeDrawer.setColor(fillColor);
      shapeDrawer.filledTriangle(x1,y1,x2,y2,x3,y3);
    }
    if(stroke) {
      shapeDrawer.setColor(strokeColor);
      shapeDrawer.triangle(x1,y1,x2,y2,x3,y3);
    }
  }
  public void polygon(PolygonRegion polygon,float x,float y) {
    renderer(shapeDrawer.getBatch());
    if(fill) {
      shapeDrawer.setColor(fillColor);
      shapeDrawer.filledPolygon(polygon.getVertices());
    }
    if(stroke) {
      shapeDrawer.setColor(strokeColor);
      shapeDrawer.polygon(polygon.getVertices());
    }
  }
  @Deprecated
  public void polygon(float[] array,int size) {
    //    if(fill) {
    //      renderer(pFill);
    //          pFill.polygon(array,0,size*2);
    //    }
    //    if(stroke) {
    //      renderer(rStroke);
    //      rStroke.polygon(array,0,size*2);
    //    }
    renderer(shapeDrawer.getBatch());
    if(fill) {
      shapeDrawer.setColor(fillColor);
      shapeDrawer.filledPolygon(array,0,size*2);
    }
    if(stroke) {
      shapeDrawer.setColor(strokeColor);
      shapeDrawer.polygon(
        array,0,size*2,
        shapeDrawer.defaultLineWidth,shapeDrawer.isJoinNecessary(shapeDrawer.defaultLineWidth)?JoinType.POINTY:JoinType.NONE);
    }
  }
  public void quad(float x1,float y1,float x2,float y2,float x3,float y3,float x4,float y4) {
    //    if(fill) {
    //      renderer(rFill);
    //      rFill.polygonVarargs(x1,y1,x2,y2,x3,y3,x4,y4);
    //    }
    //    if(stroke) {
    //      renderer(rStroke);
    //      rStroke.polygonVarargs(x1,y1,x2,y2,x3,y3,x4,y4);
    //    }
    if(!(fill||stroke)) return;
    renderer(shapeDrawer.getBatch());
    float[] vertices= {x1,y1,x2,y2,x3,y3,x4,y4};
    if(fill) {
      shapeDrawer.setColor(fillColor);
      shapeDrawer.filledPolygon(vertices);
    }
    if(stroke) {
      shapeDrawer.setColor(strokeColor);
      shapeDrawer.polygon(vertices);
    }
  }

  //---------------------------------------------------------------------------

  @Deprecated
  public void arc(float x1,float y1,float x2,float y2,float a,float b) {
    //TODO
  }
  public void arc(float x,float y,float radius,float start,float degrees) {
    if(stroke) {
      renderer(shapeDrawer.getBatch());
      shapeDrawer.setColor(strokeColor);
      shapeDrawer.arc(x,y,radius,start,UtilMath.rad(degrees));
      //      renderer(rStroke);
      //      rStroke.arcNoBorder(x,y,radius,start,degrees,UtilMath.max(1,(int)(6*(float)Math.cbrt(radius)*(degrees/360))));
    }
  }
  public void dot(float x,float y,int color) {

    renderer(shapeDrawer.getBatch());
    shapeDrawer.setColor(fillColor);
    shapeDrawer.filledRectangle(x-0.5f,y-0.5f,1,1);

  }
  public void line(float x1,float y1,float x2,float y2) {
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
    //    renderer(rFill);
    //    rFill.rect(x,y,w,h);

    renderer(shapeDrawer.getBatch());
    shapeDrawer.setColor(fillColor);
    shapeDrawer.filledRectangle(x,y,w,h);

  }
}