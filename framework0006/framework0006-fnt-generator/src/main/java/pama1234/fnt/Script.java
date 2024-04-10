package pama1234.fnt;

import com.badlogic.gdx.tools.hiero.Hiero;

public class Script{
  public static String userDir=System.getProperty("user.dir");
  public static void main(String[] args) {
    //    System.setProperty("sun.java2d.uiScale","1");

    int i=Integer.parseInt(args[0]);
    int size=Integer.parseInt(args[1]);
    int c=Integer.parseInt(args[2]);
    String name=args[3];

    try {
      Hiero.main(new String[] {
        "--input",userDir+"/framework/src/test/resources/data/pama1234/MapleMono.hiero",
        "--output",userDir+"/fontOut/scriptGen/"+size+"CharInPage/"+c+"/"+name+"-"+c+".fnt",
        "--batch",
        "-c",createString(i,size)
      });
    }catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String createString(int i,int size) {
    StringBuilder sb=new StringBuilder();
    for(int j=i;j<i+size;j++) {
      sb.append((char)j);
    }
    return sb.toString();
  }
}
