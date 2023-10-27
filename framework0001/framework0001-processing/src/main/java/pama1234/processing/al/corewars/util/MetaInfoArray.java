package pama1234.processing.al.corewars.util;

import pama1234.processing.util.app.UtilApp;

public class MetaInfoArray extends MemoryArray{
  public MetaMetaInfoArray metaInfo;
  public NeuronArray son;
  public MetaInfoArray(UtilApp p,int size,MetaMetaInfoArray metaInfo,int x,int y,int w) {
    this(p,size,metaInfo,x,y,w,(int)Math.ceil(size/(float)w));
  }
  public MetaInfoArray(UtilApp p,int size,MetaMetaInfoArray metaInfo,int x,int y) {
    this(p,size,metaInfo,x,y,1,size);
  }
  public MetaInfoArray(UtilApp p,int size,MetaMetaInfoArray metaInfo,int x,int y,int w,int h) {
    super(p,size*INT_SIZE,x,y,w*INT_SIZE,h);
    this.metaInfo=metaInfo;
  }
  @Override
  public void draw() {}
  @Override
  public void init() {}
  @Override
  public void updateArray() {}
  @Override
  public void preUpdateArray() {
    if(!data.hasRemaining()) {
      stopPre=true;
      //      data.position(0);
      return;
    }
    if(data.remaining()>=width) for(int i=0;i<width/INT_SIZE;i++) data.putInt((int)p.random(son.data.capacity()/NEURON_SIZE));
    else data.putInt((int)p.random(son.data.capacity()/NEURON_SIZE));
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
