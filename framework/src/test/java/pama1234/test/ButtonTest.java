package pama1234.test;

import static pama1234.util.gdx.lwjgl.UtilLauncher.getDefaultConfiguration;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;

import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.launcher.MainAppBase;

public class ButtonTest extends ScreenCore2D{
  public static void main(String[] args) {
    test0001();
  }

  @Test
  public static void test0001() {
    MainAppBase mab=new MainAppBase() {
      {
        var classArray=new Class[] {ButtonTest.class};
        screenClassList=new ArrayList<>(classArray.length);
        for(int i=0;i<classArray.length;i++) {
          screenClassList.add(i,classArray[i]);
        }
      }

    };
    new Lwjgl3Application(mab,getDefaultConfiguration(mab,"ShapeTest"));
  }

  @Override
  public void setup() {
    centerCamAddAll(new Entity<>(this) {
      @Override
      public void display() {

        line(0,0,50,50);

        line(50,0,100,50);
      }
    });

    centerScreenAddAll(genButtons_0004(this));
  }

  @Override
  public void update() {

  }

  @Override
  public void display() {}

  @Override
  public void displayWithCam() {

    line(0,0,50,50);

    line(50,0,100,50);
  }

  @Override
  public void frameResized() {

  }
  public static TextButton<?>[] genButtons_0004(ButtonTest p) {
    // TODO 重写按钮逻辑
    var out=new TextButton[] {
      new TextButton<>(p,true,()->true,self-> {},self-> {},self-> {},self->self.text="开始游戏",()->p.bu,()->(int)((p.width-p.textWidth("开始游戏"))/2f-p.pu/2),()->(int)(p.height*0.45f)),
      new TextButton<>(p,self->self.text="  设置  ",()->true,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {})
        .rectAutoWidth(()->(int)((p.width-p.textWidth("  设置  "))/2f-p.pu/2),()->(int)(p.height*0.45f+p.bu*1.2f),()->p.bu),
    };
    return out;
  }
}
