package pama1234.processing.al.duel.util.control.hist;

import pama1234.math.physics.Point;
import pama1234.processing.al.duel.util.Bullet;
import pama1234.processing.al.duel.util.Player;
import pama1234.processing.util.UITools;
import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.element.BorderedComponent;
import processing.core.PApplet;
import processing.core.PConstants;

public class Control<T extends Point>extends BorderedComponent{
  public static final int up=0,down=1,left=2,right=3,fire=4,mega=5;
  public final boolean[] keys=new boolean[6];
  public float speed=4;
  public float tx=0,ty=0;
  public T target;
  public Player player;
  public Player comp;
  public int cooling,minCooling=10;
  public static final float borderSize=0.5f,keySize=16,barWeight=1;
  public Control(UtilApp p,int x,int y,float speed,T target) {
    super(p,x,y,keyNumToSize(6),keyNumToSize(2));
    this.speed=speed;
    this.target=target;
    refresh();
  }
  public static int keyNumToSize(int in) {
    return (int)(Math.ceil(borderSize)-(int)borderSize+(keySize+borderSize*2)*in+barWeight*2);
  }
  @Override
  public void initGraphics() {
    super.initGraphics();
    //    System.out.println("Control.initGraphics()"+g);
    g.textSize(keySize);
  }
  @Override
  public void init() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
  @Override
  public void update() {
    super.update();
    if(keys[left]!=keys[right]) {
      if(keys[left]) tx=-1;
      else tx=1;
    }else tx=0;
    if(keys[up]!=keys[down]) {
      if(keys[up]) ty=-1;
      else ty=1;
    }else ty=0;
    float l=PApplet.mag(tx,ty);
    if(l>0) {
      tx*=speed/l;
      ty*=speed/l;
      target.add(tx,ty);
    }
    if(cooling<=0) {
      if(keys[fire]) {
        float tx=comp.point.pos.x-player.point.pos.x,
          ty=comp.point.pos.y-player.point.pos.y;
        float mag=PApplet.mag(tx,ty);
        tx*=7/mag;
        ty*=7/mag;
        Bullet e=new Bullet(player.parent,target.pos.x+tx,target.pos.y+ty);
        e.point.vel.set(tx/2,ty/2);
        player.parent.center.add.add(e);
        cooling=minCooling;
      }
    }else {
      cooling--;
    }
  }
  @Override
  public void draw() {
    keybar(keys[fire],'Z',0,0);
    keybar(keys[mega],'X',1,0);
    keybar(keys[up],'▲',4,0);
    keybar(keys[down],'▼',4,1);
    keybar(keys[left],'◀',3,1);
    keybar(keys[right],'▶',5,1);
  }
  public void keybar(boolean b,char in,float x,float y) {
    drawKey(b,in,
      numToPos(x),
      numToPos(y),
      keySize,keySize,barWeight);
  }
  private int numToPos(float x) {
    return (int)Math.ceil(barWeight+x*(keySize+borderSize*2)+borderSize);
  }
  public void drawKey(boolean b,char in,float x,float y,float w,float h,float s) {
    if(!b) y-=s;
    g.fill(b?0xffDDA0DD:0xff8B008B);
    g.rect(x,y,w,h);
    if(!b) {
      g.fill(0xffDC143C);
      g.rect(x,y+h,w,s);
    }
    g.fill(b?255:191);
    g.text(in,x+1,y-keySize/4);
    UITools.border(g,x,y,w,h,0x80DDAE4E,0x803289A8);
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
  public void keyPressed(char key,int keyCode) {
    key=Character.toLowerCase(key);
    switch(key) {
      case 'z':
        keys[fire]=true;
        break;
      case 'x':
        keys[mega]=true;
        break;
    }
    switch(keyCode) {
      case PConstants.UP:
        keys[up]=true;
        break;
      case PConstants.DOWN:
        keys[down]=true;
        break;
      case PConstants.LEFT:
        keys[left]=true;
        break;
      case PConstants.RIGHT:
        keys[right]=true;
        break;
    }
    refresh();
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    key=Character.toLowerCase(key);
    switch(key) {
      case 'z':
        keys[fire]=false;
        break;
      case 'x':
        keys[mega]=false;
        break;
    }
    switch(keyCode) {
      case PConstants.UP:
        keys[up]=false;
        break;
      case PConstants.DOWN:
        keys[down]=false;
        break;
      case PConstants.LEFT:
        keys[left]=false;
        break;
      case PConstants.RIGHT:
        keys[right]=false;
        break;
    }
    refresh();
  }
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameMoved(int x,int y) {}
  //  public static void main(String[] args) {
  //    new UtilApp() {
  //      Control<PathPoint> c;
  //      PathPoint point;
  //      @Override
  //      public void settings() {
  //        super.settings();
  //        background=color(0);
  //        noSmooth();
  //      }
  //      @Override
  //      public void init() {
  //        stroke(0xc0ffffff);
  //        c=new Control<>(this,0,0,4,point=new PathPoint(0,0));
  //        c.refresh();
  //        center.add.add(c);
  //      }
  //      @Override
  //      public void display() {
  //        UITools.cross(g,point.pos.x,point.pos.y,4,4);
  //      }
  //      @Override
  //      public void update() {
  //        point.update();
  //        //        System.out.println(point.pos);
  //      }
  //    }.run();
  //  }
}
