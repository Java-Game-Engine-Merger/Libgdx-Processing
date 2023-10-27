package pama1234.processing.al.sandbox;

import java.util.LinkedList;

import pama1234.processing.al.autometa.particle.ParticleAutomata;
import pama1234.processing.al.autometa.particle.util.Cell;
import pama1234.processing.al.autometa.particle.util.CellCenter;
import pama1234.processing.al.sandbox.util.Player;
import pama1234.processing.util.Entity;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.wrapper.EntityCenter;

public class Sandbox extends EntityCenter<Entity>{
  public ParticleAutomata pa;
  public Player a,b;
  public Sandbox(UtilApp p) {
    super(p);
    pa=new ParticleAutomata(p);
    int ti=(int)Math.ceil(CellCenter.boxR/8f+1);
    a=new Player(p,ti+4,-ti);
    b=new Player(p,ti+4+64,-ti);
    LinkedList<Cell> tl=pa.cellCenter.c.add;
    a.cell=tl.get(0);
    b.cell=tl.get(tl.size()/2);
    add.add(pa);
    add.add(a);
    add.add(b);
  }
}
