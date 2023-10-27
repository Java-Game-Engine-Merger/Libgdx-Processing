package pama1234.processing.al.autometa.particle;

import pama1234.processing.al.autometa.particle.util.Cell;
import pama1234.processing.al.autometa.particle.util.CellCenter;
import pama1234.processing.al.autometa.particle.util.MetaInfo;
import pama1234.processing.al.autometa.particle.util.MetaInfoCenter;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.wrapper.PointCenter;
import processing.core.PConstants;

public class ParticleAutomata extends PointCenter<CellCenter>{
  public MetaInfoCenter metaCenter;
  public CellCenter cellCenter;
  public static final float[][] rules=new float[][] {
    {0,1,-1,-1,0,0,0,0,0,0,0,1},
    {1,0,1,-1,-1,0,0,0,0,0,0,0},
    {0,1,0,1,-1,-1,0,0,0,0,0,0},
    {0,0,1,0,1,-1,-1,0,0,0,0,0},
    {0,0,0,1,0,1,-1,-1,0,0,0,0},
    {0,0,0,0,1,0,1,-1,-1,0,0,0},
    {0,0,0,0,0,1,0,1,-1,-1,0,0},
    {0,0,0,0,0,0,1,0,1,-1,-1,0},
    {0,0,0,0,0,0,0,1,0,1,-1,-1},
    {-1,0,0,0,0,0,0,0,1,0,1,-1},
    {-1,-1,0,0,0,0,0,0,0,1,0,1},
    {1,-1,-1,0,0,0,0,0,0,0,1,0}};
  public ParticleAutomata(UtilApp p) {
    super(p);
    MetaInfo[][] core=initCore();
    //---
    initColors(p,core);
    //--
    initCellCenter(p,core,1<<4);
  }
  public MetaInfo[][] initCore() {
    for(float[] fs:rules) for(int i=0;i<fs.length;i++) fs[i]*=Cell.size/6;
    MetaInfo[][] core=new MetaInfo[rules.length][];
    for(int i=0;i<core.length;i++) {
      core[i]=new MetaInfo[rules[i].length];
      for(int j=0;j<core[i].length;j++) core[i][j]=new MetaInfo(rules[i][j]);
      int ti=i-1;
      if(ti<0) ti+=core[i].length;
      core[i][ti].scoreG=1;
    }
    return core;
  }
  public void initColors(UtilApp p,MetaInfo[][] core) {
    int[] color=new int[core.length];
    p.colorMode(PConstants.HSB);
    for(int i=0;i<core.length;i++) color[i]=p.color(255f/core.length*i,255,255);
    p.colorMode(PConstants.RGB);
    metaCenter=new MetaInfoCenter(core,color);
  }
  public void initCellCenter(UtilApp p,MetaInfo[][] core,final int size) {
    cellCenter=new CellCenter(p,metaCenter);
    for(int i=0;i<core.length;i++) for(int j=0;j<size;j++) cellCenter.c.add.add(
      new Cell(cellCenter,i,randomCellPos(),randomCellPos()));
    add.add(cellCenter);
  }
  public float randomCellPos() {
    return p.random(0,CellCenter.boxR*2);
  }
}
