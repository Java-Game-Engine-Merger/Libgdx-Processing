package pama1234.fnt;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main{
  public static void main(String[] args) throws IOException,InterruptedException {
    int size=4096;
    String name="MapleMonoRegular";
    int c=0;
    for(int i=0;i<Character.MAX_VALUE;i+=size) {
      exec(Script.class,List.of(new String[] {String.valueOf(i),String.valueOf(size),String.valueOf(c),name}));
      c++;
    }
  }

  public static int exec(Class klass,List<String> args) throws IOException,InterruptedException {

    String javaHome=System.getProperty("java.home");
    String javaBin=javaHome+
      File.separator+
      "bin"+
      File.separator+
      "java";
    String classpath=System.getProperty("java.class.path");
    String className=klass.getName();

    List<String> command=new LinkedList<String>();
    command.add(javaBin);
    command.add("-cp");
    command.add(classpath);
    command.add(className);
    if(args!=null) {
      command.addAll(args);
    }

    ProcessBuilder builder=new ProcessBuilder(command);

    Process process=builder.inheritIO().start();
    process.waitFor();
    return process.exitValue();
  }
  public static class Test{
    public static void main(String[] args) {
      Script.main(new String[] {String.valueOf(0),String.valueOf(4096),"MapleMonoRegular"});
    }
  }
}
