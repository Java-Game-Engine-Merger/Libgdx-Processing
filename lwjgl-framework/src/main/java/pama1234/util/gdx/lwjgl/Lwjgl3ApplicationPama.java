package pama1234.util.gdx.lwjgl;

import pama1234.gdx.MobileUtil.EmptyMobileUtil;
import pama1234.gdx.Pama;

public class Lwjgl3ApplicationPama{

  public static void init() {
    Pama.mobile=new EmptyMobileUtil();
    Pama.img=new ImageLoadUtilLwjgl();
  }
}
