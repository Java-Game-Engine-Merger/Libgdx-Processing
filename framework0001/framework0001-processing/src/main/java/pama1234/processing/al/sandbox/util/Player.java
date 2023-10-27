package pama1234.processing.al.sandbox.util;

import pama1234.processing.al.autometa.particle.util.Cell;
import pama1234.processing.al.corewars.MemoryArrayNeuronSimulator;
import pama1234.processing.al.corewars.util.MemoryArray;
import pama1234.processing.al.sandbox.util.organ.Motion;
import pama1234.processing.al.sandbox.util.organ.OrganList;
import pama1234.processing.al.sandbox.util.organ.Vision;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.wrapper.EntityWrapper;

public class Player extends EntityWrapper<MemoryArrayNeuronSimulator>{
  public static final int l=(int)Math.ceil(Cell.dist*3);
  //---
  public Cell cell;
  //---
  public Vision v;
  public Motion m;
  public OrganList organs;
  public Player(UtilApp p,int x,int y) {
    super(p,new MemoryArrayNeuronSimulator(p,128+Vision.INPUT_SIZE,6,x,y,1,3+Vision.INPUT_SIZE,3));
    v=new Vision(this,x*MemoryArray.scale,y*MemoryArray.scale-20-(int)Vision.MAG_SIZE*2);
    m=new Motion(this,x*MemoryArray.scale,y*MemoryArray.scale-16);
    organs=new OrganList();
    organs.add(v);
    organs.add(m);
  }
  public float getScore() {
    return cell.score.pos;
  }
  @Override
  public void update() {
    organs.perceive();
    organs.setPerception();
    super.update();
    organs.getAction();
    organs.act();
    organs.update();
    //    cell.parent.layer.set((int)Math.round(cell.point.pos.x-CellCenter.x1+layer_cell_size),(int)Math.round(cell.point.pos.y-CellCenter.y1+layer_cell_size),0xffffffff);
  }
  @Override
  public void display() {
    organs.display();
    super.display();
  }
}
