package pama1234.processing.al.duel.util.control;

public class IntKey extends KeyData{
  int keyCode;
  public IntKey(int keyCode) {
    this.keyCode=keyCode;
  }
  @Override
  public void pressed(char key,int keyCode) {
    if(this.keyCode==keyCode) data=true;
  }
  @Override
  public void released(char key,int keyCode) {
    if(this.keyCode==keyCode) data=false;
  }
}
