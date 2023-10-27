package pama1234.processing.al.duel.util.control;

public class KeyBarStyle{
  public int pressedBar,releasedBar;
  public int pressedText,releasedText;
  public int borderLeft,borderRight;
  public int body;
  public KeyBarStyle() {}
  public KeyBarStyle(int pressedBar,int releasedBar,int pressedText,int releasedText,int borderLeft,int borderRight,int body) {
    this.pressedBar=pressedBar;
    this.releasedBar=releasedBar;
    this.pressedText=pressedText;
    this.releasedText=releasedText;
    this.borderLeft=borderLeft;
    this.borderRight=borderRight;
    this.body=body;
  }
}
