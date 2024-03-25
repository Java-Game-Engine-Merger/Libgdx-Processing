package pama1234.gdx.util.p3d;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;

public class SpriteBatch3D extends SpriteBatch3DCore{

  public SpriteBatch3D(Camera camera) {
    this.usedCamera=camera;
  }
  public void switchTexture(Texture texture) {
    softFlush();
    lastTexture=texture;
    invTexWidth=1.0f/texture.getWidth();
    invTexHeight=1.0f/texture.getHeight();
  }

  @Override
  public void draw(Texture texture,
    float x,float y,
    float originX,float originY,
    float width,float height,
    float scaleX,float scaleY,
    float rotation,
    int srcX,int srcY,
    int srcWidth,int srcHeight,
    boolean flipX,boolean flipY) {

    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

    float[] vertices=this.vertices;

    if(texture!=lastTexture) switchTexture(texture);
    else if(idx==vertices.length) //
      softFlush();

    // bottom left and top right corner points relative to origin
    final float worldOriginX=x+originX;
    final float worldOriginY=y+originY;
    float fx=-originX;
    float fy=-originY;
    float fx2=width-originX;
    float fy2=height-originY;

    // scale
    if(scaleX!=1||scaleY!=1) {
      fx*=scaleX;
      fy*=scaleY;
      fx2*=scaleX;
      fy2*=scaleY;
    }

    // construct corner points, start from top left and go counter clockwise
    final float p1x=fx;
    final float p1y=fy;
    final float p2x=fx;
    final float p2y=fy2;
    final float p3x=fx2;
    final float p3y=fy2;
    final float p4x=fx2;
    final float p4y=fy;

    float x1;
    float y1;
    float x2;
    float y2;
    float x3;
    float y3;
    float x4;
    float y4;

    // rotate
    if(rotation!=0) {
      final float cos=MathUtils.cosDeg(rotation);
      final float sin=MathUtils.sinDeg(rotation);

      x1=cos*p1x-sin*p1y;
      y1=sin*p1x+cos*p1y;

      x2=cos*p2x-sin*p2y;
      y2=sin*p2x+cos*p2y;

      x3=cos*p3x-sin*p3y;
      y3=sin*p3x+cos*p3y;

      x4=x1+(x3-x2);
      y4=y3-(y2-y1);
    }else {
      x1=p1x;
      y1=p1y;

      x2=p2x;
      y2=p2y;

      x3=p3x;
      y3=p3y;

      x4=p4x;
      y4=p4y;
    }

    x1+=worldOriginX;
    y1+=worldOriginY;
    x2+=worldOriginX;
    y2+=worldOriginY;
    x3+=worldOriginX;
    y3+=worldOriginY;
    x4+=worldOriginX;
    y4+=worldOriginY;

    float u=srcX*invTexWidth;
    float v=(srcY+srcHeight)*invTexHeight;
    float u2=(srcX+srcWidth)*invTexWidth;
    float v2=srcY*invTexHeight;

    if(flipX) {
      float tmp=u;
      u=u2;
      u2=tmp;
    }

    if(flipY) {
      float tmp=v;
      v=v2;
      v2=tmp;
    }

    float color=this.colorPacked;
    int idx=this.idx;
    vertices[idx]=x1;
    vertices[idx+1]=y1;
    vertices[idx+2]=0;
    vertices[idx+3]=u;
    vertices[idx+4]=v;

    vertices[idx+5]=x2;
    vertices[idx+6]=y2;
    vertices[idx+7]=0;
    vertices[idx+8]=u;
    vertices[idx+9]=v2;

    vertices[idx+10]=x3;
    vertices[idx+11]=y3;
    vertices[idx+12]=0;
    vertices[idx+13]=u2;
    vertices[idx+14]=v2;

    vertices[idx+15]=x4;
    vertices[idx+16]=y4;
    vertices[idx+17]=0;
    vertices[idx+18]=u2;
    vertices[idx+19]=v;
    this.idx=idx+20;

    softFlush();
  }

  @Override
  public void draw(Texture texture,
    float x,float y,
    float width,float height,
    int srcX,int srcY,
    int srcWidth,int srcHeight,
    boolean flipX,boolean flipY) {

    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

    float[] vertices=this.vertices;

    if(texture!=lastTexture) switchTexture(texture);
    else if(idx==vertices.length) //
      softFlush();

    float u=srcX*invTexWidth;
    float v=(srcY+srcHeight)*invTexHeight;
    float u2=(srcX+srcWidth)*invTexWidth;
    float v2=srcY*invTexHeight;
    final float fx2=x+width;
    final float fy2=y+height;

    if(flipX) {
      float tmp=u;
      u=u2;
      u2=tmp;
    }

    if(flipY) {
      float tmp=v;
      v=v2;
      v2=tmp;
    }

    float color=this.colorPacked;
    int idx=this.idx;
    vertices[idx]=x;
    vertices[idx+1]=y;
    vertices[idx+2]=0;
    vertices[idx+3]=u;
    vertices[idx+4]=v;

    vertices[idx+5]=x;
    vertices[idx+6]=fy2;
    vertices[idx+7]=0;
    vertices[idx+8]=u;
    vertices[idx+9]=v2;

    vertices[idx+10]=fx2;
    vertices[idx+11]=fy2;
    vertices[idx+12]=0;
    vertices[idx+13]=u2;
    vertices[idx+14]=v2;

    vertices[idx+15]=fx2;
    vertices[idx+16]=y;
    vertices[idx+17]=0;
    vertices[idx+18]=u2;
    vertices[idx+19]=v;
    this.idx=idx+20;

    softFlush();
  }

  @Override
  public void draw(Texture texture,
    float x,float y,
    int srcX,int srcY,
    int srcWidth,int srcHeight) {

    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

    float[] vertices=this.vertices;

    if(texture!=lastTexture) switchTexture(texture);
    else if(idx==vertices.length) //
      softFlush();

    final float u=srcX*invTexWidth;
    final float v=(srcY+srcHeight)*invTexHeight;
    final float u2=(srcX+srcWidth)*invTexWidth;
    final float v2=srcY*invTexHeight;
    final float fx2=x+srcWidth;
    final float fy2=y+srcHeight;

    //    float color=this.colorPacked;
    int idx=this.idx;
    vertices[idx]=x;
    vertices[idx+1]=y;
    vertices[idx+2]=0;
    vertices[idx+3]=u;
    vertices[idx+4]=v;

    vertices[idx+5]=x;
    vertices[idx+6]=fy2;
    vertices[idx+7]=0;
    vertices[idx+8]=u;
    vertices[idx+9]=v2;

    vertices[idx+10]=fx2;
    vertices[idx+11]=fy2;
    vertices[idx+12]=0;
    vertices[idx+13]=u2;
    vertices[idx+14]=v2;

    vertices[idx+15]=fx2;
    vertices[idx+16]=y;
    vertices[idx+17]=0;
    vertices[idx+18]=u2;
    vertices[idx+19]=v;
    this.idx=idx+20;

    softFlush();
  }

  @Override
  public void draw(Texture texture,
    float x,float y,
    float width,float height,
    float u,float v,
    float u2,float v2) {

    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

    float[] vertices=this.vertices;

    if(texture!=lastTexture) switchTexture(texture);
    else if(idx==vertices.length) //
      softFlush();

    final float fx2=x+width;
    final float fy2=y+height;

    float color=this.colorPacked;
    int idx=this.idx;
    vertices[idx]=x;
    vertices[idx+1]=y;
    vertices[idx+2]=0;
    vertices[idx+3]=u;
    vertices[idx+4]=v;

    vertices[idx+5]=x;
    vertices[idx+6]=fy2;
    vertices[idx+7]=0;
    vertices[idx+8]=u;
    vertices[idx+9]=v2;

    vertices[idx+10]=fx2;
    vertices[idx+11]=fy2;
    vertices[idx+12]=0;
    vertices[idx+13]=u2;
    vertices[idx+14]=v2;

    vertices[idx+15]=fx2;
    vertices[idx+16]=y;
    vertices[idx+17]=0;
    vertices[idx+18]=u2;
    vertices[idx+19]=v;
    this.idx=idx+20;

    softFlush();
  }

  @Override
  public void draw(Texture texture,float x,float y) {
    draw(texture,x,y,texture.getWidth(),texture.getHeight());
  }

  @Override
  public void draw(Texture texture,float x,float y,float width,float height) {
    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

    float[] vertices=this.vertices;

    if(texture!=lastTexture) switchTexture(texture);
    else if(idx==vertices.length) //
      softFlush();

    final float fx2=x+width;
    final float fy2=y+height;
    final float u=0;
    final float v=1;
    final float u2=1;
    final float v2=0;

    float color=this.colorPacked;
    int idx=this.idx;
    vertices[idx]=x;
    vertices[idx+1]=y;
    vertices[idx+2]=0;
    vertices[idx+3]=u;
    vertices[idx+4]=v;

    vertices[idx+5]=x;
    vertices[idx+6]=fy2;
    vertices[idx+7]=0;
    vertices[idx+8]=u;
    vertices[idx+9]=v2;

    vertices[idx+10]=fx2;
    vertices[idx+11]=fy2;
    vertices[idx+12]=0;
    vertices[idx+13]=u2;
    vertices[idx+14]=v2;

    vertices[idx+15]=fx2;
    vertices[idx+16]=y;
    vertices[idx+17]=0;
    vertices[idx+18]=u2;
    vertices[idx+19]=v;
    this.idx=idx+20;

    softFlush();
  }

  @Override
  public void draw(Texture texture,float[] spriteVertices,int offset,int count) {
    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

    int verticesLength=vertices.length;
    int remainingVertices=verticesLength;
    if(texture!=lastTexture) switchTexture(texture);
    else {
      remainingVertices-=idx;
      if(remainingVertices==0) {
        softFlush();
        remainingVertices=verticesLength;
      }
    }
    int copyCount=Math.min(remainingVertices,count);

    //    System.out.println(Arrays.toString(spriteVertices));

    // TODO spriteVertices is modified in the method
    // change spriteVertices[i] from color float bits to 0(z pos)
    //    setPackedColor(spriteVertices[2]);
    for(int i=2;i<spriteVertices.length;i+=5) {
      spriteVertices[i]=0;
    }

    System.arraycopy(spriteVertices,offset,vertices,idx,copyCount);

    idx+=copyCount;
    count-=copyCount;
    while(count>0) {
      offset+=copyCount;
      softFlush();
      copyCount=Math.min(verticesLength,count);
      System.arraycopy(spriteVertices,offset,vertices,0,copyCount);
      idx+=copyCount;
      count-=copyCount;
    }

    softFlush();
  }

  @Override
  public void draw(TextureRegion region,float x,float y) {
    draw(region,x,y,region.getRegionWidth(),region.getRegionHeight());
  }

  @Override
  public void draw(TextureRegion region,float x,float y,float width,float height) {
    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

    float[] vertices=this.vertices;

    Texture texture=region.getTexture();
    if(texture!=lastTexture) {
      switchTexture(texture);
    }else if(idx==vertices.length) //
      softFlush();

    final float fx2=x+width;
    final float fy2=y+height;
    final float u=region.getU();
    final float v=region.getV2();
    final float u2=region.getU2();
    final float v2=region.getV();

    float color=this.colorPacked;
    int idx=this.idx;
    vertices[idx]=x;
    vertices[idx+1]=y;
    vertices[idx+2]=0;
    vertices[idx+3]=u;
    vertices[idx+4]=v;

    vertices[idx+5]=x;
    vertices[idx+6]=fy2;
    vertices[idx+7]=0;
    vertices[idx+8]=u;
    vertices[idx+9]=v2;

    vertices[idx+10]=fx2;
    vertices[idx+11]=fy2;
    vertices[idx+12]=0;
    vertices[idx+13]=u2;
    vertices[idx+14]=v2;

    vertices[idx+15]=fx2;
    vertices[idx+16]=y;
    vertices[idx+17]=0;
    vertices[idx+18]=u2;
    vertices[idx+19]=v;
    this.idx=idx+20;

    softFlush();
  }

  @Override
  public void draw(TextureRegion region,
    float x,float y,
    float originX,float originY,
    float width,float height,
    float scaleX,float scaleY,
    float rotation) {

    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

    float[] vertices=this.vertices;

    Texture texture=region.getTexture();
    if(texture!=lastTexture) {
      switchTexture(texture);
    }else if(idx==vertices.length) //
      softFlush();

    // bottom left and top right corner points relative to origin
    final float worldOriginX=x+originX;
    final float worldOriginY=y+originY;
    float fx=-originX;
    float fy=-originY;
    float fx2=width-originX;
    float fy2=height-originY;

    // scale
    if(scaleX!=1||scaleY!=1) {
      fx*=scaleX;
      fy*=scaleY;
      fx2*=scaleX;
      fy2*=scaleY;
    }

    // construct corner points, start from top left and go counter clockwise
    final float p1x=fx;
    final float p1y=fy;
    final float p2x=fx;
    final float p2y=fy2;
    final float p3x=fx2;
    final float p3y=fy2;
    final float p4x=fx2;
    final float p4y=fy;

    float x1;
    float y1;
    float x2;
    float y2;
    float x3;
    float y3;
    float x4;
    float y4;

    // rotate
    if(rotation!=0) {
      final float cos=MathUtils.cosDeg(rotation);
      final float sin=MathUtils.sinDeg(rotation);

      x1=cos*p1x-sin*p1y;
      y1=sin*p1x+cos*p1y;

      x2=cos*p2x-sin*p2y;
      y2=sin*p2x+cos*p2y;

      x3=cos*p3x-sin*p3y;
      y3=sin*p3x+cos*p3y;

      x4=x1+(x3-x2);
      y4=y3-(y2-y1);
    }else {
      x1=p1x;
      y1=p1y;

      x2=p2x;
      y2=p2y;

      x3=p3x;
      y3=p3y;

      x4=p4x;
      y4=p4y;
    }

    x1+=worldOriginX;
    y1+=worldOriginY;
    x2+=worldOriginX;
    y2+=worldOriginY;
    x3+=worldOriginX;
    y3+=worldOriginY;
    x4+=worldOriginX;
    y4+=worldOriginY;

    final float u=region.getU();
    final float v=region.getV2();
    final float u2=region.getU2();
    final float v2=region.getV();

    float color=this.colorPacked;
    int idx=this.idx;
    vertices[idx]=x1;
    vertices[idx+1]=y1;
    vertices[idx+2]=0;
    vertices[idx+3]=u;
    vertices[idx+4]=v;

    vertices[idx+5]=x2;
    vertices[idx+6]=y2;
    vertices[idx+7]=0;
    vertices[idx+8]=u;
    vertices[idx+9]=v2;

    vertices[idx+10]=x3;
    vertices[idx+11]=y3;
    vertices[idx+12]=0;
    vertices[idx+13]=u2;
    vertices[idx+14]=v2;

    vertices[idx+15]=x4;
    vertices[idx+16]=y4;
    vertices[idx+17]=0;
    vertices[idx+18]=u2;
    vertices[idx+19]=v;
    this.idx=idx+20;

    softFlush();
  }

  @Override
  public void draw(TextureRegion region,
    float x,float y,
    float originX,float originY,
    float width,float height,
    float scaleX,float scaleY,
    float rotation,
    boolean clockwise) {

    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

    float[] vertices=this.vertices;

    Texture texture=region.getTexture();
    if(texture!=lastTexture) {
      switchTexture(texture);
    }else if(idx==vertices.length) //
      softFlush();

    // bottom left and top right corner points relative to origin
    final float worldOriginX=x+originX;
    final float worldOriginY=y+originY;
    float fx=-originX;
    float fy=-originY;
    float fx2=width-originX;
    float fy2=height-originY;

    // scale
    if(scaleX!=1||scaleY!=1) {
      fx*=scaleX;
      fy*=scaleY;
      fx2*=scaleX;
      fy2*=scaleY;
    }

    // construct corner points, start from top left and go counter clockwise
    final float p1x=fx;
    final float p1y=fy;
    final float p2x=fx;
    final float p2y=fy2;
    final float p3x=fx2;
    final float p3y=fy2;
    final float p4x=fx2;
    final float p4y=fy;

    float x1;
    float y1;
    float x2;
    float y2;
    float x3;
    float y3;
    float x4;
    float y4;

    // rotate
    if(rotation!=0) {
      final float cos=MathUtils.cosDeg(rotation);
      final float sin=MathUtils.sinDeg(rotation);

      x1=cos*p1x-sin*p1y;
      y1=sin*p1x+cos*p1y;

      x2=cos*p2x-sin*p2y;
      y2=sin*p2x+cos*p2y;

      x3=cos*p3x-sin*p3y;
      y3=sin*p3x+cos*p3y;

      x4=x1+(x3-x2);
      y4=y3-(y2-y1);
    }else {
      x1=p1x;
      y1=p1y;

      x2=p2x;
      y2=p2y;

      x3=p3x;
      y3=p3y;

      x4=p4x;
      y4=p4y;
    }

    x1+=worldOriginX;
    y1+=worldOriginY;
    x2+=worldOriginX;
    y2+=worldOriginY;
    x3+=worldOriginX;
    y3+=worldOriginY;
    x4+=worldOriginX;
    y4+=worldOriginY;

    float u1,v1,u2,v2,u3,v3,u4,v4;
    if(clockwise) {
      u1=region.getU2();
      v1=region.getV2();
      u2=region.getU();
      v2=region.getV2();
      u3=region.getU();
      v3=region.getV();
      u4=region.getU2();
      v4=region.getV();
    }else {
      u1=region.getU();
      v1=region.getV();
      u2=region.getU2();
      v2=region.getV();
      u3=region.getU2();
      v3=region.getV2();
      u4=region.getU();
      v4=region.getV2();
    }

    float color=this.colorPacked;
    int idx=this.idx;
    vertices[idx]=x1;
    vertices[idx+1]=y1;
    vertices[idx+2]=0;
    vertices[idx+3]=u1;
    vertices[idx+4]=v1;

    vertices[idx+5]=x2;
    vertices[idx+6]=y2;
    vertices[idx+7]=0;
    vertices[idx+8]=u2;
    vertices[idx+9]=v2;

    vertices[idx+10]=x3;
    vertices[idx+11]=y3;
    vertices[idx+12]=0;
    vertices[idx+13]=u3;
    vertices[idx+14]=v3;

    vertices[idx+15]=x4;
    vertices[idx+16]=y4;
    vertices[idx+17]=0;
    vertices[idx+18]=u4;
    vertices[idx+19]=v4;
    this.idx=idx+20;

    softFlush();
  }

  @Override
  public void draw(TextureRegion region,float width,float height,Affine2 transform) {
    if(!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

    float[] vertices=this.vertices;

    Texture texture=region.getTexture();
    if(texture!=lastTexture) {
      switchTexture(texture);
    }else if(idx==vertices.length) {
      softFlush();
    }

    // construct corner points
    float x1=transform.m02;
    float y1=transform.m12;
    float x2=transform.m01*height+transform.m02;
    float y2=transform.m11*height+transform.m12;
    float x3=transform.m00*width+transform.m01*height+transform.m02;
    float y3=transform.m10*width+transform.m11*height+transform.m12;
    float x4=transform.m00*width+transform.m02;
    float y4=transform.m10*width+transform.m12;

    float u=region.getU();
    float v=region.getV2();
    float u2=region.getU2();
    float v2=region.getV();

    float color=this.colorPacked;
    int idx=this.idx;
    vertices[idx]=x1;
    vertices[idx+1]=y1;
    vertices[idx+2]=0;
    vertices[idx+3]=u;
    vertices[idx+4]=v;

    vertices[idx+5]=x2;
    vertices[idx+6]=y2;
    vertices[idx+7]=0;
    vertices[idx+8]=u;
    vertices[idx+9]=v2;

    vertices[idx+10]=x3;
    vertices[idx+11]=y3;
    vertices[idx+12]=0;
    vertices[idx+13]=u2;
    vertices[idx+14]=v2;

    vertices[idx+15]=x4;
    vertices[idx+16]=y4;
    vertices[idx+17]=0;
    vertices[idx+18]=u2;
    vertices[idx+19]=v;
    this.idx=idx+20;

    softFlush();
  }

}
