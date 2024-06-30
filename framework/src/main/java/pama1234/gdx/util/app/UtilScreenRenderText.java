package pama1234.gdx.util.app;

import com.badlogic.gdx.math.MathUtils;

import pama1234.gdx.util.font.BetterBitmapFont;
import pama1234.gdx.util.font.TextStyleSupplier;
import pama1234.math.transform.Pose3D;
import pama1234.util.Annotations.SyntacticSugar;

/**
 * 此中间类主要用于渲染文本相关的操作
 *
 * @see UtilScreen2D
 * @see UtilScreen3D
 */
public abstract class UtilScreenRenderText extends UtilScreenColor{

  /**
   * 在指定位置渲染文本
   *
   * @param in 要渲染的文本
   * @param x  x坐标
   * @param y  y坐标
   */
  public void text(String in,float x,float y) {
    renderer(font.fontBatch());
    font.text(in==null?"null":in,x,y);
  }

  /**
   * 在指定三维位置渲染文本
   *
   * @param in 要渲染的文本
   * @param x  x坐标
   * @param y  y坐标
   * @param z  z坐标
   */
  public void text(String in,float x,float y,float z) {
    pushMatrix();
    translate(x,y,z);
    text(in);
    popMatrix();
  }

  /**
   * 在指定三维位置和旋转角度渲染文本
   *
   * @param in 要渲染的文本
   * @param x  x坐标
   * @param y  y坐标
   * @param z  z坐标
   * @param rx x轴旋转角度
   * @param ry y轴旋转角度
   * @param rz z轴旋转角度
   */
  public void text(String in,float x,float y,float z,float rx,float ry,float rz) {
    pushMatrix();
    translate(x,y,z);
    rotate(rx,ry,rz);
    text(in);
    popMatrix();
  }

  /**
   * 语法糖，在原点渲染文本
   *
   * @param in 要渲染的文本
   */
  @SyntacticSugar
  public void text(String in) {
    text(in,0,0);
  }

  /**
   * 根据给定的三维姿态渲染文本
   *
   * @param in   要渲染的文本
   * @param pose 三维姿态
   */
  public void text(String in,Pose3D pose) {
    pushMatrix();
    pose(pose);
    text(in);
    popMatrix();
  }

  /**
   * 获取文本宽度（包含缩放）
   *
   * @param in 要测量的文本
   * @return 文本宽度
   */
  public float textWidth(String in) {
    return font.textWidth(in,true);
  }

  /**
   * 获取文本宽度（不包含缩放）
   *
   * @param in 要测量的文本
   * @return 文本宽度
   */
  public float textWidthNoScale(String in) {
    return font.textWidthNoScale(in);
  }

  /**
   * 设置文本缩放比例
   *
   * @param in 缩放比例
   */
  public void textScale(float in) {
    font.textScale(in);
  }

  /**
   * 获取当前文本缩放比例
   *
   * @return 当前缩放比例
   */
  public float textScale() {
    return font.styleFast.scale;
  }

  /**
   * 设置文本大小
   *
   * @param in 文本大小
   */
  public void textSize(float in) {
    font.size(in);
  }

  /**
   * 获取当前文本大小
   *
   * @return 当前文本大小
   */
  public float textSize() {
    return font.styleFast.size;
  }

  /**
   * 设置文本样式
   *
   * @param in 文本样式
   * @deprecated 已弃用
   */
  @Deprecated
  public void textStyle(TextStyleSupplier in) {
    font.style=in;
    if(in==null) imageBatch.setColor(font.styleFast.foreground);
  }

  /**
   * 计算合适的字体缩放比例
   *
   * @param in 缩放比例
   * @return 合适的字体缩放比例
   */
  public float fontScale(float in) {
    if(in>=1) return MathUtils.floor(in);
    else return Math.max(MathUtils.floor(in*fontGridSize)/fontGridSize,1/fontGridSize);
  }

  /**
   * 获取当前字体对象
   *
   * @return 当前字体对象
   */
  public BetterBitmapFont textFont() {
    return font;
  }

  /**
   * 设置字体对象
   *
   * @param fontIn 字体对象
   */
  public void textFont(BetterBitmapFont fontIn) {
    font=fontIn;
    font.fontBatch=()->imageBatch;
    font.styleFast=fontStyle;
  }

  /**
   * 设置文本模式
   *
   * @param in 文本模式
   */
  public void textMode(int in) {
    font.textMode=in;
  }

  @Deprecated
  public void fullText(String in,float x,float y) {
    renderer(imageBatch);
    font.draw(imageBatch,in==null?"null":in,x,y);
  }
}
