package pama1234.processing.al.corewars.util;

import java.nio.ByteBuffer;

import pama1234.processing.util.app.UtilApp;

public class NeuronArray extends MemoryArray{
  MetaInfoArray metaInfo;
  public NeuronArray(UtilApp p,int size,MetaInfoArray metaInfo,int x,int y,int w) {
    this(p,size,metaInfo,x,y,w,(int)Math.ceil(size/(float)w));
  }
  public NeuronArray(UtilApp p,int size,MetaInfoArray metaInfo,int x,int y) {
    this(p,size,metaInfo,x,y,1,size);
  }
  public NeuronArray(UtilApp p,int size,MetaInfoArray metaInfo,int x,int y,int w,int h) {
    super(p,size*NEURON_SIZE,x,y,w*NEURON_SIZE,h);
    this.metaInfo=metaInfo;
  }
  @Override
  public void draw() {}
  @Override
  public void init() {}
  @Override
  public void updateArray() {
    final ByteBuffer mmia=metaInfo.metaInfo.data,mia=metaInfo.data,ma=data;
    try {
      for(int i=0;i<mmia.capacity();i+=INT_SIZE*2) {
        int start=mmia.getInt(i),
          end=mmia.getInt(i+INT_SIZE);
        if(start==end) continue;
        if(start>end) {
          final int temp=start;
          start=end;
          end=temp;
        }
        start*=INT_SIZE;
        end*=INT_SIZE;
        for(int j=start;j<end;j+=INT_SIZE) {
          final int neuronPointer=mia.getInt(j)*NEURON_SIZE;
          final int neuronParentNumber=ma.getInt(neuronPointer+FLOAT_SIZE)*CELL_SIZE;
          //        final int neuronParentNumber=Math.min(ma.getInt(neuronPointer+FLOAT_SIZE),MAX_PARENT_LINKS)*CELL_SIZE;
          if(neuronParentNumber==0) continue;
          float sum=0;
          for(int k=0;k<neuronParentNumber;k+=CELL_SIZE) {
            final int parentPointer=ma.getInt(neuronPointer+CELL_SIZE+k+FLOAT_SIZE)*NEURON_SIZE;
            final float parentData=ma.getFloat(parentPointer);
            final float parentWeight=ma.getFloat(neuronPointer+CELL_SIZE+k);
            sum+=parentData*parentWeight;
          }
          float out=f(sum);
          if(Float.isFinite(out)) ma.putFloat(neuronPointer,out);
          else ma.putFloat(neuronPointer,0);
        }
      }
    }catch(RuntimeException e) {
      System.out.println(e);
    }
  }
  @Override
  public void preUpdateArray() {
    if(!data.hasRemaining()) {
      //      data.position(0);
      stopPre=true;
      return;
    }
    //    int pos=data.position()/INT_SIZE;
    //    if(pos%(NEURON_SIZE/INT_SIZE)<2) {
    //      if(pos%2==0) data.putFloat(p.random(-0x100,0x100));
    //      else data.putInt((int)p.random(MAX_PARENT_LINKS+1));
    //    }else {
    //      if(pos%2==0) data.putFloat(p.random(-0x10,0x10));
    //      else data.putInt((int)p.random(data.capacity()/NEURON_SIZE));
    //    }
    if(data.remaining()/INT_SIZE>=width) {
      for(int i=0;i<width/NEURON_SIZE;i++) {
        data.putFloat(p.random(-0x100,0x100));
        data.putInt((int)p.random(MAX_PARENT_LINKS+1));
        for(int j=0;j<MAX_PARENT_LINKS;j++) {
          data.putFloat(p.random(-0x10,0x10));
          data.putInt((int)p.random(data.capacity()/NEURON_SIZE));
        }
      }
    }else {
      data.putFloat(p.random(-0x100,0x100));
      data.putInt((int)p.random(MAX_PARENT_LINKS+1));
      for(int j=0;j<MAX_PARENT_LINKS;j++) {
        data.putFloat(p.random(-0x10,0x10));
        data.putInt((int)p.random(data.capacity()/NEURON_SIZE));
      }
    }
  }
  @Override
  public void postUpdateArray() {}
  public static final float SIGMOID_RANGE=1<<4;
  public static float f(float x) {
    return (float)((2*SIGMOID_RANGE)/(1+Math.pow(1+Math.E/SIGMOID_RANGE,-x))-SIGMOID_RANGE);
  }
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
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
}
