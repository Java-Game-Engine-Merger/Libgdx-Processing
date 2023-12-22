package pama1234.gdx.util.ui.menu;

import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.util.app.UtilScreen;

import com.badlogic.gdx.utils.Array;

public abstract class SettingSector<T extends UtilScreen,E>{
  public TextButtonCam<?> pb;

  public Array<Button<?>> buttonsScreen=new Array<>();
  public Array<TextButtonCam<?>> buttonsCam=new Array<>();
  public Array<TextField> camTextFields=new Array<>();

  public abstract void createButton(T p,E ps);
  public void clear() {
    buttonsScreen.clear();
    buttonsCam.clear();
    camTextFields.clear();
  }
}
