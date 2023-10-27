package pama1234.processing.al.corewars.util;

import pama1234.processing.util.app.UtilApp;

public class MetaMetaInfoArray extends MemoryArray{
  public MetaInfoArray son;
  public MetaMetaInfoArray(UtilApp p,int size,int x,int y,int w) {
    this(p,size,x,y,w,(int)Math.ceil(size/(float)w));
  }
  public MetaMetaInfoArray(UtilApp p,int size,int x,int y) {
    this(p,size,x,y,1,size);
  }
  public MetaMetaInfoArray(UtilApp p,int size,int x,int y,int w,int h) {
    super(p,(size+2)*INT_SIZE*2,x,y,w*INT_SIZE*2,h+2);
    data.position(INT_SIZE*2);
  }
  @Override
  public void draw() {}
  @Override
  public void init() {}
  @Override
  public void updateArray() {}
  @Override
  public void preUpdateArray() {
    if(data.position()>=data.capacity()-INT_SIZE*2) {
      //      data.position(INT_SIZE*2);
      stopPre=true;
      return;
    }
    data.putInt((int)p.random(son.data.capacity()/INT_SIZE));
  }
  @Override
  public void postUpdateArray() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
  @Override
  public void mousePressed(int button) {}
  @Override
  public void mouseReleased(int button) {}
  @Override
  public void mouseClicked(int button) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(int c) {}
  @Override
  public void keyPressed(char key,int keyCode) {
    super.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
}
