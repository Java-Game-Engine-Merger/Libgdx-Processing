package pama1234.processing.al.duel.util.control;

public class CharKey extends KeyData{
  char key;
  @Override
  public void pressed(char key,int keyCode) {
    if(this.key==key) data=true;
  }
  @Override
  public void released(char key,int keyCode) {
    if(this.key==key) data=false;
  }
}
