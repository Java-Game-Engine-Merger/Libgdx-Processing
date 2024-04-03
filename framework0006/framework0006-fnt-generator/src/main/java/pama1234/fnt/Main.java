package pama1234.fnt;

import com.badlogic.gdx.tools.hiero.Hiero;

public class Main{
  public static String userDir=System.getProperty("user.dir");
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");

    int size=4096;
    String name="MapleMonoRegular";
    for(int i=0;i<Character.MAX_VALUE;i+=size) {
      try {
        Hiero.main(new String[] {
          "--input \""+userDir+"/framework/src/test/resources/data/pama1234/MapleMono.hiero\"",
          "--output \""+userDir+"/"+size+"CharInPage/"+i+"/"+name+"-"+i+".fnt\"",
          "--batch"});
      }catch(Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
