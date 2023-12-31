package pama1234.util;

import pama1234.Tools;
import pama1234.util.listener.ServerEntityListener;
import pama1234.util.wrapper.ServerEntityCenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class UtilServer implements Runnable{
  public ServerEntityCenter<UtilServer,ServerEntityListener> center=new ServerEntityCenter<UtilServer,ServerEntityListener>(this);
  public boolean doDispose=true;
  public boolean stop;

  public float frameRate;
  public float frameRateTarget;
  public long frameRatePeriod;
  public long frameRateLastNanos;
  public int frameCount;

  public long overSleepTime=0L;

  public Random rng=new Random();
  public final float random(float high) {
    if(high==0||high!=high) return 0;
    float value=0;
    do value=rng.nextFloat()*high; while(value==high);
    return value;
  }
  public final float random(float low,float high) {
    if(low>=high) return low;
    float diff=high-low;
    float value=0;
    do value=random(diff)+low; while(value==high);
    return value;
  }

  {
    frameRate(60);
    Runtime.getRuntime().addShutdownHook(new Thread(()-> {
      outerDispose();
    },"ShutdownHook"));
  }
  public abstract void init();
  public abstract void update();
  public void outerDispose() {
    if(doDispose) {
      dispose();
      doDispose=false;
    }
  }
  public abstract void dispose();
  @Override
  public void run() {
    long beforeTime=System.nanoTime();
    long overSleepTime=0L;
    int noDelays=0;
    final int NO_DELAYS_PER_YIELD=15;

    init();
    while(!stop) {
      long now=System.nanoTime();
      double rate=1000000.0/((now-frameRateLastNanos)/1000000.0);
      float instantaneousRate=(float)(rate/1000.0);
      frameRate=(frameRate*0.9f)+(instantaneousRate*0.1f);

      center.update();
      update();

      frameRateLastNanos=now;
      //-
      long afterTime=System.nanoTime();
      long timeDiff=afterTime-beforeTime;
      long sleepTime=(frameRatePeriod-timeDiff)-overSleepTime;
      if(sleepTime>0) {
        try {
          Thread.sleep(sleepTime/1000000L,(int)(sleepTime%1000000L));
        }catch(InterruptedException ex) {}
        overSleepTime=(System.nanoTime()-afterTime)-sleepTime;
      }else {
        overSleepTime=0L;
        noDelays++;
        if(noDelays>NO_DELAYS_PER_YIELD) {
          Thread.yield();
          noDelays=0;
        }
      }
      frameCount++;
      beforeTime=System.nanoTime();
    }
    outerDispose();
  }
  public void frameRate(float fps) {
    frameRateTarget=fps;
    frameRatePeriod=(long)(1000000000.0/frameRateTarget);
  }
  public String loadString(String file) {
    return loadString(Paths.get(file).toFile());
  }
  public static String loadString(File path) {
    try {
      InputStream inputStream=new FileInputStream(path);
      String text=new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8))
        .lines()
        .collect(Collectors.joining("\n"));
      inputStream.close();
      return text;
    }catch(IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  public static void saveString(String path,String in) {
    saveString(Paths.get(path).toFile(),in);
  }
  public static void saveString(File path,String in) {
    try {
      OutputStream outputStream=new FileOutputStream(path);
      outputStream.write(in.getBytes(StandardCharsets.UTF_8));
      outputStream.close();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
  public static void println(Object... in) {
    for(var i:in) System.out.print(i+" ");
    System.out.println();
  }
  public static void exit() {
    System.exit(0);
  }
  public static void main(String[] args) {
    new UtilServer() {
      @Override
      public void init() {
        frameRate(120);
      }
      @Override
      public void update() {
        float tf=frameRate-frameRateTarget;
        if(tf>15||tf<-15) System.out.println(Tools.cutToLastDigit(tf));
      }
      @Override
      public void dispose() {}
    }.run();
  }
}
