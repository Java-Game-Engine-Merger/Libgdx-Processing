package pama1234.processing.lang.visual.util.unit;

import pama1234.math.Tools;
import pama1234.processing.lang.visual.util.Component;
import pama1234.processing.lang.visual.util.node.Name;
import pama1234.processing.util.app.UtilApp;

public abstract class Unit extends Component{
  public Name name;
  public String prefix;
  public boolean inBox;
  public Unit(UtilApp p,int x,int y,String prefix,String name) {
    super(p,x,y);
    this.name=new Name(name);
    this.prefix=prefix;
    refresh();
    resize((int)Math.ceil(p.textWidth(prefix+" "+name)),(int)Math.ceil(textSize*2.5f));
    //    System.out.println(Integer.toHexString(p.color(251,97,4)));
  }
  public void drawName() {
    g.text(prefix+" "+name.s,0,0);
  }
  @Override
  public void update() {
    super.update();
    boolean tb=Tools.inBox(p.cam.mouseX,p.cam.mouseY,point.pos.x,point.pos.y,g.width,g.height);
    if(tb!=inBox) {
      inBox=tb;
      refresh();
    }
    //    System.out.println(inBox);
  }
}
