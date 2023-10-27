package pama1234.processing.al.corewars;

import static pama1234.processing.al.corewars.util.MemoryArray.INT_SIZE;
import static pama1234.processing.al.corewars.util.MemoryArray.NEURON_SIZE;

import pama1234.processing.al.corewars.app.MainApp;
import pama1234.processing.al.corewars.util.MemoryArray;
import pama1234.processing.al.corewars.util.MetaInfoArray;
import pama1234.processing.al.corewars.util.MetaMetaInfoArray;
import pama1234.processing.al.corewars.util.NeuronArray;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.wrapper.PointCenter;

public class MemoryArrayNeuronSimulator extends PointCenter<MemoryArray>{
  public NeuronArray na;
  public MetaInfoArray mia;
  public MetaMetaInfoArray mmia;
  public int inputRange,outputRange;
  public MemoryArrayNeuronSimulator(UtilApp p,int neuronAmount,int layerAmount,int x,int y,int w,int inputRange,int outputRange) {
    super(p);
    this.inputRange=inputRange;
    this.outputRange=outputRange;
    //---
    mmia=new MetaMetaInfoArray(p,layerAmount,
      x+NEURON_SIZE*w+INT_SIZE*2*w+2,y);
    mia=new MetaInfoArray(p,neuronAmount*2+inputRange+outputRange,
      mmia,
      x+NEURON_SIZE*w+1,y,2*w);
    na=new NeuronArray(p,neuronAmount,
      mia,
      x,y,w);
    //---
    mmia.son=mia;
    mia.son=na;
    mmia.data.putInt(INT_SIZE,inputRange);
    mmia.data.putInt(mmia.data.capacity()-INT_SIZE*2,mia.data.capacity()/INT_SIZE-outputRange);
    mmia.data.putInt(mmia.data.capacity()-INT_SIZE,mia.data.capacity()/INT_SIZE);
    //---
    add.add(na);
    add.add(mia);
    add.add(mmia);
  }
  public void setInput(int pos,float value) {
    if(pos<0||pos>inputRange) throw new IndexOutOfBoundsException("setInput 0->"+pos+"<-"+inputRange);
    na.data.putFloat(mia.data.getInt(pos*INT_SIZE)*NEURON_SIZE,value);
  }
  public float getOutput(int pos) {
    if(pos<0||pos>outputRange) throw new IndexOutOfBoundsException("getOutput 0->"+pos+"<-"+outputRange);
    return na.data.getFloat(mia.data.getInt(mia.data.capacity()-(pos+1)*INT_SIZE)*NEURON_SIZE);
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new MainApp().run();
  }
}
