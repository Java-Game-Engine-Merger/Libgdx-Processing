package pama1234.processing.al.duel.util.control;

public abstract class KeyData{
  public char name;
  public boolean data,hist;
  public abstract void pressed(char key,int keyCode);
  public abstract void released(char key,int keyCode);
  public boolean refresh() {
    boolean out=data!=hist;
    if(out) hist=data;
    return out;
  }
}
