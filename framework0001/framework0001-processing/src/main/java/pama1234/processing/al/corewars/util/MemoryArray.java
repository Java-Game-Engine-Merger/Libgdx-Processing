package pama1234.processing.al.corewars.util;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;

import pama1234.math.Tools;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.element.BlankComponent;

public abstract class MemoryArray extends BlankComponent{
  public static final int FLOAT_SIZE=4,INT_SIZE=4;
  public static final int CELL_SIZE=FLOAT_SIZE+INT_SIZE;
  public static final int MAX_PARENT_LINKS=4;
  public static final int NEURON_SIZE=MAX_PARENT_LINKS*CELL_SIZE+CELL_SIZE;
  public static int scale=8,range=4;
  public static float pixelSize=scale/(float)range;
  public static final DecimalFormat floatFormatter=new DecimalFormat(
    "####,"+
      "0000."+
      "0000"),
    intFormatter=new DecimalFormat(
      "####,"+
        "0000");
  public ByteBuffer data;
  public int minSize;
  public int mousePos,mouseX,mouseY,bytePos;
  public int width,height;
  public boolean pause,focus;
  public boolean stopPre,stopPost;
  private StringBuilder sb=new StringBuilder();
  static {
    floatFormatter.setPositivePrefix(" ");
    intFormatter.setPositivePrefix(" ");
  }
  public MemoryArray(UtilApp p,int size,int x,int y) {
    this(p,size,x,y,1,size+2);
  }
  public MemoryArray(UtilApp p,int size,int x,int y,int w,int h) {
    super(p,x*scale,y*scale,w*range,h+2);
    data=ByteBuffer.allocate(size);
    this.width=w;
    this.height=h;
    g.beginDraw();
    g.fill(0xff006799);
    g.stroke(0xff0099B6);
    g.endDraw();
    updateSize();
  }
  @Override
  public void update() {
    super.update();
    if(!pause) {
      if(!stopPre) preUpdateArray();
      updateArray();
      if(!stopPost) postUpdateArray();
    }
    if((focus=Tools.inBox(p.cam.mouseX,p.cam.mouseY,point.pos.x,point.pos.y,width*scale,height*pixelSize))) {
      mousePos=(mouseY=(int)(Math.floor((p.cam.mouseY-point.pos.y)*range)/scale))*width+(mouseX=(int)(Math.floor(p.cam.mouseX-point.pos.x)/scale));
      bytePos=(int)(Math.floor((p.cam.mouseX-point.pos.x)*range)/scale)%4;
      if(mousePos>=data.capacity()) mousePos=-1;
    }
    mousePos/=4;
    g.beginDraw();
    g.clear();
    g.rect(0,0,g.width-1,g.height-1);
    g.loadPixels();
    drawBuffer();
    g.updatePixels();
    g.endDraw();
  }
  public abstract void updateArray();
  public abstract void preUpdateArray();
  public abstract void postUpdateArray();
  public void drawBuffer() {
    for(int i=0;i<minSize;i++) {
      g.pixels[i*range]=Tools.color(data.get(i)&0xc0);
      g.pixels[i*range+1]=Tools.color((data.get(i)<<2)&0xc0);
      g.pixels[i*range+2]=Tools.color((data.get(i)<<4)&0xc0);
      g.pixels[i*range+3]=Tools.color((data.get(i)<<6)&0xc0);
    }
  }
  public void updateSize() {
    minSize=Math.min(g.pixels.length/range,data.capacity());
  }
  @Override
  public void display() {
    float x=(int)(Math.ceil(point.pos.x/scale)*scale),
      y=(int)(Math.floor(point.pos.y/scale)*scale),
      w=g.width*pixelSize,
      h=g.height*pixelSize;
    p.image(g,x,y,w,h);
    p.stroke(0xffDDF4C4);
    for(int i=1;i<w/(INT_SIZE*scale);i++) p.line(x+scale*INT_SIZE*i,y,x+scale*INT_SIZE*i,y+h);
    p.stroke(0xff389CA3);
    p.rect(x,y,w,h);
    if(focus&&mousePos>=0) drawBar();
  }
  public void drawBar() {
    p.stroke(0xffDDF4C4);
    float tx=point.pos.x+(mouseX/4*4)*scale;
    float ty=point.pos.y+mouseY*pixelSize;
    p.rect(tx,ty,
      scale*4,pixelSize);
    p.stroke(0xff006799);
    p.rect(
      point.pos.x+mouseX*scale,
      ty,
      scale,pixelSize);
    p.stroke(0xffFB6104);
    p.rect(
      point.pos.x+mouseX*scale+bytePos*pixelSize,
      ty,
      pixelSize,pixelSize);
    sb.append("mousePos  <[");
    sb.append(mouseX);
    sb.append(" ");
    sb.append(bytePos);
    sb.append("]->");
    sb.append(mouseX/4);
    sb.append(",");
    sb.append(mouseY);
    sb.append(">\n");
    sb.append("value at  <");
    sb.append(mousePos+(mouseX%4)/4f);
    sb.append(">:");
    sb.append("\nDecfloat  ");
    sb.append(floatFormatter.format(data.getFloat(mousePos*4)));
    sb.append("\nDecInt    ");
    sb.append(intFormatter.format(data.getInt(mousePos*4)));
    sb.append("\nHexInt    ");
    sb.append(Integer.toHexString(data.getInt(mousePos*4)).toUpperCase());
    sb.append("\nOctInt    ");
    sb.append(Integer.toOctalString(data.getInt(mousePos*4)).toUpperCase());
    sb.append("\nDecByte   ");
    sb.append((data.get(mousePos*4+mouseX%4)&0xff));
    sb.append("\nHexByte   ");
    sb.append(Integer.toHexString(data.get(mousePos*4+mouseX%4)&0xff).toUpperCase());
    sb.append("\nOctByte   ");
    sb.append(Integer.toOctalString(data.get(mousePos*4+mouseX%4)&0xff).toUpperCase());
    sb.append("\nBinByte   ");
    sb.append(Integer.toBinaryString(data.get(mousePos*4+mouseX%4)&0xff).toUpperCase());
    String string=sb.toString();
    float tw=p.textWidth(string);
    float th=p.g.textSize*10.5f;
    p.stroke(0x80006799);
    p.fill(0x80000000);
    p.rect(tx,ty-th,tw,th);
    p.fill(0xffffffff);
    p.noFill();
    p.text(string,tx,ty);
    sb.setLength(0);
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(!focus) return;
    switch(key) {
      case ' ': {
        pause=!pause;
      }
        break;
      case 'w': {
        int i=mousePos*4+mouseX%4;
        data.put(i,(byte)(data.get(i)+(1<<((3-bytePos)*2))));
      }
        break;
      case 's': {
        int i=mousePos*4+mouseX%4;
        data.put(i,(byte)(data.get(i)-(1<<((3-bytePos)*2))));
      }
        break;
    }
    if(pause) {
      g.beginDraw();
      g.fill(0xc0);
      g.stroke(0x80);
      g.endDraw();
    }else {
      g.beginDraw();
      g.fill(0xff006799);
      g.stroke(0xff0099B6);
      g.endDraw();
    }
  }
}