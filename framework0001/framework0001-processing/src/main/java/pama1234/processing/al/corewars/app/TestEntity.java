package pama1234.processing.al.corewars.app;

import pama1234.processing.al.corewars.MemoryArrayNeuronSimulator;
import pama1234.processing.al.corewars.util.MemoryArray;
import pama1234.processing.util.app.UtilApp;

public class TestEntity extends MemoryArrayNeuronSimulator{
  public MemoryArray in,out;//TODO finish this
  public TestEntity(UtilApp p) {
    super(p,64,8,0,0,1,4,4);
  }
}
