package pama1234.gdx.util.ui;

import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextButton.ButtonRect;
import pama1234.math.geometry.RectF;
import pama1234.math.geometry.RectI;

/**
 * 用来调整UI尤其是按钮UI的位置的类
 */
public class CompositionUtil{
  public static <T extends TextButton<?>> T[] linearComposition(T buttons[],boolean vertical,float interval) {
    return linearComposition(buttons,0,buttons.length,vertical,interval);
  }
  public static <T extends TextButton<?>> T[] linearComposition(T buttons[],int start,int end,boolean vertical,float interval) {
    if(start+1>=buttons.length) return buttons;
    int e=end>buttons.length-1?buttons.length:end+1;
    for(int i=start+1;i<e;i++) {
      final int l=i-1;
      var r=buttons[l].rect;
      linearComposition(r,buttons[i],vertical,interval);
    }
    return buttons;
  }
  public static <T extends TextButton<?>> T linearComposition(RectI base,T button,boolean vertical,float interval) {
    RectI rect=button.rect;
    if(rect instanceof RectF r) {
      // RectF r=(RectF)rect;
      if(vertical) {
        r.y=()->base.ynh()+interval;
      }else {
        r.x=()->base.xnw()+interval;
      }
    }else if(rect instanceof ButtonRect r) {
      if(vertical) {
        r.y=base.ynh()+interval;
      }else {
        r.x=base.xnw()+interval;
      }
    }
    return button;
  }
}
