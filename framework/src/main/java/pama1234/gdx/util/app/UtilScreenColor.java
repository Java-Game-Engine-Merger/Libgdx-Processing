package pama1234.gdx.util.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import space.earlygrey.shapedrawer.CapType;
import space.earlygrey.shapedrawer.JoinType;

/**
 * 此中间类主要放颜色赋值方面的方法
 * 
 * @see UtilScreen2D
 * @see UtilScreen3D
 */
public abstract class UtilScreenColor extends UtilScreenPose{

  /**
   * 设置文本颜色
   * 
   * @param in 颜色对象
   */
  public void textColor(Color in) {
    textColor.set(in);
    font.color(textColor);
  }

  /**
   * 设置文本颜色和透明度
   * 
   * @param in    颜色对象
   * @param alpha 透明度 (0-255)
   */
  public void textColor(Color in,int alpha) {
    textColor.set(in);
    textColor.a=alpha/255f;
    font.color(textColor);
  }

  /**
   * 设置文本颜色
   * 
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   * @param a 透明度 (0-255)
   */
  public void textColor(int r,int g,int b,int a) {
    textColor.set(r/255f,g/255f,b/255f,a/255f);
    font.color(textColor);
  }

  /**
   * 设置文本颜色
   * 
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   */
  public void textColor(int r,int g,int b) {
    textColor.set(r/255f,g/255f,b/255f,1);
    font.color(textColor);
  }

  /**
   * 设置文本颜色
   * 
   * @param in 灰度值 (0-255)
   */
  public void textColor(int in) {
    textColor(in,in,in);
  }

  /**
   * 设置文本颜色
   * 
   * @param gray  灰度值 (0-255)
   * @param alpha 透明度 (0-255)
   */
  public void textColor(int gray,int alpha) {
    textColor.set(gray/255f,gray/255f,gray/255f,alpha/255f);
    font.color(textColor);
  }

  //---------------------------------------------------------------------------

  /**
   * 设置填充颜色
   * 
   * @param in 颜色对象
   */
  public void fill(Color in) {
    fillColor.set(in);
    pFill.setColor(fillColor);
  }

  /**
   * 设置填充颜色和透明度
   * 
   * @param in 颜色对象
   * @param a  透明度 (0-255)
   */
  public void fill(Color in,int a) {
    fillColor.set(in);
    fillColor.a=a/255f;
    pFill.setColor(fillColor);
  }

  /**
   * 设置填充颜色
   * 
   * @param gray 灰度值 (0-255)
   */
  public void fill(int gray) {
    fill(gray,255);
  }

  /**
   * 设置填充颜色
   * 
   * @param gray 灰度值 (0-255)
   * @param a    透明度 (0-255)
   */
  public void fill(int gray,int a) {
    fillColor.set(gray/255f,gray/255f,gray/255f,a/255f);
    pFill.setColor(fillColor);
  }

  /**
   * 设置填充颜色
   * 
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   */
  public void fill(int r,int g,int b) {
    fillColor.set(r/255f,g/255f,b/255f,1);
    pFill.setColor(fillColor);
  }

  /**
   * 设置填充颜色
   * 
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   * @param a 透明度 (0-255)
   */
  public void fill(int r,int g,int b,int a) {
    fillColor.set(r/255f,g/255f,b/255f,a/255f);
    pFill.setColor(fillColor);
  }

  /**
   * 设置填充颜色
   * 
   * @param argb ARGB格式的颜色值
   */
  public void fillHex(int argb) {
    fill((argb>>16)&0xff,(argb>>8)&0xff,argb&0xff,(argb>>24)&0xff);
  }

  /**
   * 取消填充
   */
  public void noFill() {
    fill=false;
  }

  /**
   * 启用填充
   */
  public void doFill() {
    fill=true;
  }

  //---------------------------------------------------------------------------

  /**
   * 设置图像批处理颜色
   * 
   * @param in 颜色对象
   */
  public void tint(Color in) {
    Color tc=imageBatch.getColor();
    tc.set(in);
    imageBatch.setColor(tc);
  }

  /**
   * 设置图像批处理颜色
   * 
   * @param gray 灰度值 (0-255)
   */
  public void tint(int gray) {
    tint(gray,255);
  }

  /**
   * 设置图像批处理颜色
   * 
   * @param gray 灰度值 (0-255)
   * @param a    透明度 (0-255)
   */
  public void tint(int gray,int a) {
    Color tc=imageBatch.getColor();
    tc.set(gray/255f,gray/255f,gray/255f,a/255f);
    imageBatch.setColor(tc);
  }

  /**
   * 设置图像批处理颜色
   * 
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   */
  public void tint(int r,int g,int b) {
    tint(r,g,b,255);
  }

  /**
   * 设置图像批处理颜色
   * 
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   * @param a 透明度 (0-255)
   */
  public void tint(int r,int g,int b,int a) {
    Color tc=imageBatch.getColor();
    tc.set(r/255f,g/255f,b/255f,a/255f);
    imageBatch.setColor(tc);
  }

  /**
   * 取消图像批处理颜色
   */
  public void noTint() {
    imageBatch.setColor(imageBatch.getColor().set(1,1,1,1));
  }

  //---------------------------------------------------------------------------

  /**
   * 设置描边颜色
   * 
   * @param in 颜色对象
   */
  public void stroke(Color in) {
    strokeColor.set(in);
  }

  /**
   * 设置描边颜色和透明度
   * 
   * @param in    颜色对象
   * @param alpha 透明度 (0-255)
   */
  public void stroke(Color in,int alpha) {
    strokeColor.set(in);
    strokeColor.a=alpha/255f;
  }

  /**
   * 设置描边颜色
   * 
   * @param gray 灰度值 (0-255)
   */
  public void stroke(int gray) {
    stroke(gray,255);
  }

  /**
   * 设置描边颜色
   * 
   * @param gray 灰度值 (0-255)
   * @param a    透明度 (0-255)
   */
  public void stroke(int gray,int a) {
    strokeColor.set(gray/255f,gray/255f,gray/255f,a/255f);
  }

  /**
   * 设置描边颜色
   * 
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   */
  public void stroke(int r,int g,int b) {
    strokeColor.set(r/255f,g/255f,b/255f,1);
  }

  /**
   * 设置描边颜色
   * 
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   * @param a 透明度 (0-255)
   */
  public void stroke(int r,int g,int b,int a) {
    strokeColor.set(r/255f,g/255f,b/255f,a/255f);
  }

  /**
   * 取消描边
   */
  public void noStroke() {
    stroke=false;
  }

  /**
   * 启用描边
   */
  public void doStroke() {
    stroke=true;
  }

  //---------------------------------------------------------------------------

  /**
   * 设置描边端点类型
   * 
   * @param capType 端点类型
   */
  public void strokeCap(CapType capType) {
    this.capType=capType;
  }

  /**
   * 设置描边连接类型
   * 
   * @param joinType 连接类型
   */
  public void strokeJoin(JoinType joinType) {
    this.joinType=joinType;
  }

  /**
   * 设置描边宽度
   * 
   * @param in 宽度值
   */
  public void strokeWeight(float in) {
    if(in<=0) return;
    strokeWeight=in;
    Gdx.gl.glLineWidth(strokeWeight);
  }

  /**
   * 设置形状绘制器的默认线宽
   * 
   * @param in 宽度值
   */
  public void sstrokeWeight(float in) {
    shapeDrawer.setDefaultLineWidth(in);
  }

  //---------------------------------------------------------------------------

  /**
   * 设置颜色对象的灰度值
   * 
   * @param c    颜色对象
   * @param gray 灰度值 (0-255)
   */
  public void color(Color c,float gray) {
    c.set(gray/255f,gray/255f,gray/255f,1);
  }

  /**
   * 设置颜色对象的灰度值和透明度
   * 
   * @param c    颜色对象
   * @param gray 灰度值 (0-255)
   * @param a    透明度 (0-255)
   */
  public void color(Color c,float gray,float a) {
    c.set(gray/255f,gray/255f,gray/255f,a/255f);
  }

  /**
   * 设置颜色对象的RGB值
   * 
   * @param c 颜色对象
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   */
  public void color(Color c,float r,float g,float b) {
    c.set(r/255f,g/255f,b/255f,1);
  }

  /**
   * 设置颜色对象的RGBA值
   * 
   * @param c 颜色对象
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   * @param a 透明度 (0-255)
   */
  public void color(Color c,float r,float g,float b,float a) {
    c.set(r/255f,g/255f,b/255f,a/255f);
  }

  /**
   * 生成灰度颜色对象
   * 
   * @param gray 灰度值 (0-255)
   * @return 颜色对象
   */
  public static Color color(float gray) {
    return new Color(gray/255f,gray/255f,gray/255f,1);
  }

  /**
   * 生成灰度颜色对象
   * 
   * @param gray 灰度值 (0-255)
   * @param a    透明度 (0-255)
   * @return 颜色对象
   */
  public static Color color(float gray,float a) {
    return new Color(gray/255f,gray/255f,gray/255f,a/255f);
  }

  /**
   * 生成RGB颜色对象
   * 
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   * @return 颜色对象
   */
  public static Color color(float r,float g,float b) {
    return new Color(r/255f,g/255f,b/255f,1);
  }

  /**
   * 生成RGBA颜色对象
   * 
   * @param r 红色分量 (0-255)
   * @param g 绿色分量 (0-255)
   * @param b 蓝色分量 (0-255)
   * @param a 透明度 (0-255)
   * @return 颜色对象
   */
  public static Color color(float r,float g,float b,float a) {
    return new Color(r/255f,g/255f,b/255f,a/255f);
  }

  /**
   * 生成颜色对象从十六进制字符串 输入AARRGGBB格式的十六进制字符串。
   * 
   * @param hex 十六进制字符串
   * @return 颜色对象
   */
  public static Color color(String hex) {
    if(hex.startsWith("#")) {
      hex=hex.substring(1);
    }
    int colorValue=(int)Long.parseLong(hex,16);
    float alpha=hex.length()>6?((colorValue>>24)&0xFF)/255f:1;
    float red=((colorValue>>16)&0xFF)/255f;
    float green=((colorValue>>8)&0xFF)/255f;
    float blue=(colorValue&0xFF)/255f;
    return new Color(red,green,blue,alpha);
  }

  /**
   * 生成颜色对象从ARGB整数值 (已弃用)
   * 
   * @param argb ARGB格式的颜色值
   * @return 颜色对象
   */
  @Deprecated
  public static Color colorFromInt(int argb) {
    return newColorFromInt(argb);
  }

  /**
   * 生成颜色对象从ARGB整数值
   * 
   * @param argb ARGB格式的颜色值
   * @return 颜色对象
   */
  public static Color newColorFromInt(int argb) {
    return colorFromInt(new Color(),argb);
  }

  /**
   * 设置颜色对象从ARGB整数值
   * 
   * @param c    颜色对象
   * @param argb ARGB格式的颜色值
   * @return 颜色对象
   */
  public static Color colorFromInt(Color c,int argb) {
    Color.argb8888ToColor(c,argb);
    return c;
  }

  /**
   * 线性插值颜色 (已弃用)
   * 
   * @param a   起始颜色
   * @param b   结束颜色
   * @param pos 插值位置 (0-1)
   * @return 插值后的颜色
   */
  @Deprecated
  public static Color lerpColor(Color a,Color b,float pos) {
    Color out=new Color();
    lerpColor(a,b,out,pos);
    return out;
  }
  public static void lerpColor(Color a,Color b,Color out,float pos) {
    if(pos==0) out.set(a);
    else if(pos==1) out.set(b);
    else {
      float tr=b.r-a.r,
        tg=b.g-a.g,
        tb=b.b-a.b,
        ta=b.a-a.a;
      out.set(a.r+tr*pos,a.g+tg*pos,a.b+tb*pos,a.a+ta*pos);
    }
  }

  //---------------------------------------------------------------------------

  public void backgroundColor(int r,int g,int b) {
    color(backgroundColor,r,g,b);
  }
  public void backgroundColor(int gray,int a) {
    color(backgroundColor,gray,a);
  }
  public void backgroundColor(int in) {
    color(backgroundColor,in);
  }
  public void backgroundColor(Color in) {
    backgroundColor.set(in);
  }
}