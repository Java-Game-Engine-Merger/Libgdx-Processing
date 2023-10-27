package pama1234.processing.al.autometa.particle.util.copy;

import static pama1234.processing.al.autometa.particle.util.CellCenter.borderSize;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import pama1234.math.physics.MassPoint;
import pama1234.math.physics.MassVar;
import pama1234.nio.ByteData;
import pama1234.nio.ByteDataFactory;
import pama1234.processing.util.Entity;
import pama1234.processing.util.app.UtilApp;

public class Cell extends Entity implements ByteData{
  public static final float scoreDamping=1f/8;
  public static final int buffer_size=MassPoint.buffer_size+ByteData.INT_SIZE;
  /**
   * this two value should always be integer
   */
  public static final float size=2,dist=size;
  public static final float minScore=1f/(1<<16);
  public CellCenter parent;
  public int id;
  public final MassPoint point;
  public float totalScore;
  public final LinkedList<Cell> back=new LinkedList<Cell>();
  public final MassVar score=new MassVar(0);
  {
    score.f=0f;
  }
  public Cell(UtilApp p,CellCenter parent,int meta,float x,float y) {
    super(p);
    this.parent=parent;
    this.id=meta;
    this.point=new MassPoint(x,y);
  }
  @Override
  public void init() {}
  @Override
  public void update() {
    point.update();
    totalScore+=score.pos;
    totalScore-=minScore*2;
    score.pos*=scoreDamping;
    score.pos+=minScore;
    score.update();
  }
  @Override
  public void display() {
    parent.layer.fill(parent.meta.color[id]);
    parent.layer.ellipse(
      point.pos.x-CellCenter.x1+borderSize,
      point.pos.y-CellCenter.y1+borderSize,
      size,size);
  }
  @Override
  public void pause() {}
  @Override
  public void resume() {}
  @Override
  public void dispose() {}
  @Override
  public void mousePressed(final int button) {}
  @Override
  public void mouseReleased(final int button) {}
  @Override
  public void mouseClicked(final int button) {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(final int c) {}
  @Override
  public void keyPressed(final char key,final int keyCode) {}
  @Override
  public void keyReleased(final char key,final int keyCode) {}
  @Override
  public void keyTyped(final char key) {}
  @Override
  public void frameResized(final int w,final int h) {}
  @Override
  public void frameMoved(final int x,final int y) {}
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    id=in.getInt(offset);
    point.fromData(in,offset+=ByteData.INT_SIZE,offset+=point.bufferSize());
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putInt(offset,id);
    point.toData(in,offset+=ByteData.INT_SIZE);
    return in;
  }
  @Override
  public int bufferSize() {
    return buffer_size;
  }
  public static class CellFactory implements ByteDataFactory<Cell>{
    @Override
    public ByteBuffer save(Cell in) {
      return null;
    }
    @Override
    public Cell load(ByteBuffer in) {
      return null;
    }
    @Override
    public ByteBuffer saveTo(Cell in,ByteBuffer data) {
      return null;
    }
    @Override
    public Cell loadTo(ByteBuffer in,Cell element) {
      return null;
    }
  }
}
