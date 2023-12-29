package pama1234.util.entity;

import pama1234.util.UtilServer;
import pama1234.util.listener.ServerEntityListener;

public abstract class ServerEntity<T extends UtilServer> implements ServerEntityListener{
  public T p;

  public ServerEntity(T p) {
    this.p=p;
  }
}