package pama1234.gdx.util.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import dev.lyze.gdxtinyvg.TinyVG;

/**
 * 图像渲染工具类，继承自文本渲染工具类
 */
public abstract class UtilScreenRenderImage extends UtilScreenRenderText{

  /**
   * 渲染3D模型
   *
   * @param in 要渲染的模型实例
   */
  public void model(ModelInstance in) {
    renderer(modelBatch);
    modelBatch.render(in);
  }

  /**
   * 渲染并刷新3D模型
   *
   * @param in 要渲染的模型实例
   * @deprecated 已弃用
   */
  @Deprecated
  public void modelFlush(ModelInstance in) {
    model(in);
    flushModel();
  }

  /**
   * 刷新3D模型
   *
   * @deprecated 已弃用
   */
  @Deprecated
  public void flushModel() {
    modelBatch.flush();
  }

  /**
   * 设置深度测试
   *
   * @param flag 是否启用深度测试
   */
  public void depth(boolean flag) {
    if(depth==flag) return;

    depth=flag;

    if(depth) {
      shapeDrawer=shapeDrawer3d;
      imageBatch=batch3d;
    }else {
      shapeDrawer=shapeDrawerDefault;
      imageBatch=imageBatchDefault;
    }
    font.fontBatch=()->imageBatch;

    endRenderer();
  }

  /**
   * 渲染TinyVG图像
   *
   * @param in TinyVG对象
   */
  public void tvg(TinyVG in) {
    renderer(imageBatch);
    in.draw(tvgDrawer);
  }

  /**
   * 渲染纹理图像
   *
   * @param in 纹理对象
   * @param x  x坐标
   * @param y  y坐标
   */
  public void image(Texture in,float x,float y) {
    renderer(imageBatch);
    imageBatch.draw(in,x,y);
  }

  /**
   * 渲染纹理图像（已弃用）
   *
   * @param in 纹理对象
   * @param x  x坐标
   * @param y  y坐标
   * @param z  z坐标
   * @deprecated 已弃用
   */
  @Deprecated
  public void image(Texture in,float x,float y,float z) {
    renderer(imageBatch);
    imageBatch.draw(in,x,y);
  }

  /**
   * 渲染纹理区域图像
   *
   * @param in 纹理区域对象
   * @param x  x坐标
   * @param y  y坐标
   */
  public void image(TextureRegion in,float x,float y) {
    renderer(imageBatch);
    imageBatch.draw(in,x,y);
  }

  /**
   * 渲染缩放后的纹理图像
   *
   * @param in 纹理对象
   * @param x  x坐标
   * @param y  y坐标
   * @param w  宽度
   * @param h  高度
   */
  public void image(Texture in,float x,float y,float w,float h) {
    renderer(imageBatch);
    imageBatch.draw(in,x,y,w,h);
  }

  /**
   * 使用着色器渲染纹理区域图像
   *
   * @param in     纹理区域对象
   * @param x      x坐标
   * @param y      y坐标
   * @param shader 着色器程序
   */
  public void image(TextureRegion in,float x,float y,ShaderProgram shader) {
    renderer(imageBatch);
    imageBatch.setShader(shader);
    imageBatch.draw(in,x,y);
    imageBatch.setShader(null);
  }

  /**
   * 使用着色器渲染缩放后的纹理图像
   *
   * @param in     纹理对象
   * @param x      x坐标
   * @param y      y坐标
   * @param w      宽度
   * @param h      高度
   * @param shader 着色器程序
   */
  public void image(Texture in,float x,float y,float w,float h,ShaderProgram shader) {
    renderer(imageBatch);
    imageBatch.setShader(shader);
    imageBatch.draw(in,x,y,w,h);
    imageBatch.setShader(null);
  }

  /**
   * 渲染中心对齐的纹理图像
   *
   * @param in 纹理对象
   * @param x  中心x坐标
   * @param y  中心y坐标
   * @param w  宽度
   * @param h  高度
   */
  public void imageCenterPos(Texture in,float x,float y,float w,float h) {
    renderer(imageBatch);
    imageBatch.draw(in,x-w/2,y-h/2,w,h);
  }

  /**
   * 渲染中心对齐的纹理图像（已弃用）
   *
   * @param in 纹理对象
   * @param x  中心x坐标
   * @param y  中心y坐标
   * @param z  z坐标
   * @param w  宽度
   * @param h  高度
   * @deprecated 已弃用
   */
  @Deprecated
  public void imageCenterPos(Texture in,float x,float y,float z,float w,float h) {
    pushMatrix();
    translate(0,0,z);
    renderer(imageBatch);
    imageBatch.draw(in,x-w/2,y-h/2,w,h);
    popMatrix();
  }

  /**
   * 渲染缩放后的纹理区域图像
   *
   * @param in 纹理区域对象
   * @param x  x坐标
   * @param y  y坐标
   * @param w  宽度
   * @param h  高度
   */
  public void image(TextureRegion in,float x,float y,float w,float h) {
    renderer(imageBatch);
    innerImage(in,x,y,w,h);
  }

  /**
   * 内部方法：渲染缩放后的纹理区域图像
   *
   * @param in 纹理区域对象
   * @param x  x坐标
   * @param y  y坐标
   * @param w  宽度
   * @param h  高度
   */
  private void innerImage(TextureRegion in,float x,float y,float w,float h) {
    imageBatch.draw(in,x,y,w,h);
  }

  /**
   * 渲染精灵图像
   *
   * @param in 精灵对象
   */
  public void sprite(Sprite in) {
    renderer(imageBatch);
    in.draw(imageBatch);
  }
}
