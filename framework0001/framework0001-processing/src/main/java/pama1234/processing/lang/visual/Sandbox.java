package pama1234.processing.lang.visual;

import pama1234.processing.lang.visual.util.unit.Function;
import pama1234.processing.lang.visual.util.unit.Unit;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.wrapper.EntityCenter;

public class Sandbox extends EntityCenter<Unit>{
  public Function main;
  public Sandbox(UtilApp p) {
    super(p);
    main=new Function(p,0,0,"main");
    this.add.add(main);
  }
}
