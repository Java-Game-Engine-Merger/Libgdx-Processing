package pama1234.gdx.util.app;

import com.badlogic.gdx.math.MathUtils;

import pama1234.gdx.util.font.BetterBitmapFont;
import pama1234.gdx.util.font.TextStyleSupplier;
import pama1234.math.transform.Pose3D;
import pama1234.util.Annotations.SyntacticSugar;

/**
 * 此中间类主要放渲染文本相关的东东
 *
 * @see UtilScreen2D
 * @see UtilScreen3D
 */
public abstract class UtilScreenRenderText extends UtilScreenColor{
  public void text(String in,float x,float y) {
    renderer(font.fontBatch());
    font.text(in==null?"null":in,x,y);
  }
  public void text(String in,float x,float y,float z) {
    pushMatrix();
    translate(x,y,z);
    text(in);
    popMatrix();
  }
  public void text(String in,float x,float y,float z,float rx,float ry,float rz) {
    pushMatrix();
    translate(x,y,z);
    rotate(rx,ry,rz);
    text(in);
    popMatrix();
  }
  @SyntacticSugar
  public void text(String in) {
    text(in,0,0);
  }
  public void text(String in,Pose3D pose) {
    pushMatrix();
    pose(pose);
    text(in);
    popMatrix();
  }

  //---------------------------------------------------------------------------

  public float textWidth(String in) {
    return font.textWidth(in);
  }
  public float textWidthNoScale(String in) {
    return font.textWidthNoScale(in);
  }
  public void textScale(float in) {
    font.textScale(in);
  }
  public float textScale() {
    return font.styleFast.scale;
  }
  public void textSize(float in) {
    font.size(in);
  }
  public float textSize() {
    return font.styleFast.size;
  }
  @Deprecated
  public void textStyle(TextStyleSupplier in) {
    font.style=in;
    if(in==null) imageBatch.setColor(font.styleFast.foreground);
  }
  public float fontScale(float in) {
    if(in>=1) return MathUtils.floor(in);
    else return Math.max(MathUtils.floor(in*fontGridSize)/fontGridSize,1/fontGridSize);
  }
  public BetterBitmapFont textFont() {
    return font;
  }
  public void textFont(BetterBitmapFont fontIn) {
    font=fontIn;

    font.fontBatch=()->imageBatch;
    font.styleFast=fontStyle;
  }
  public void textMode(int in) {
    font.textMode=in;
  }

  //---------------------------------------------------------------------------
  @Deprecated
  public void fullText(String in,float x,float y) {
//    setProjectionMatrix(usedCamera.projection);
//    setTransformMatrix(matrix());
    renderer(imageBatch);
    font.draw(imageBatch,in==null?"null":in,x,y);
  }
  @Deprecated
  public void setTextScale(float in) {
    font.getData().setScale(in);
  }
}
