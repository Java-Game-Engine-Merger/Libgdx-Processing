package pama1234.processing.al.duel.util.control;

import pama1234.processing.util.app.UtilApp;
import pama1234.processing.util.element.Component;

public class Keyboard extends Component<KeyBar>{
  public KeyboardStyle s;
  public Keyboard(UtilApp p,int x,int y,int w,int h) {
    super(p,x,y);
    s=new KeyboardStyle();
    resize(keyNumToSize(w),keyNumToSize(h));
  }
  public int keyNumToSize(int in) {
    return (int)(Math.ceil(s.borderSize)-borderSize+(s.keySize+borderSize*2)*in+s.barWeight*2);
  }
  @Override
  public void initGraphics() {
    super.initGraphics();
    if(s!=null) g.textSize(s.keySize);
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
    for(KeyBar i:c.list) i.data.pressed(key,keyCode);
    refresh();
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    for(KeyBar i:c.list) i.data.released(key,keyCode);
    refresh();
  }
  @Override
  public void keyTyped(char key) {}
  @Override
  public void frameMoved(int x,int y) {}
}
