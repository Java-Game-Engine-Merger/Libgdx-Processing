package pama1234.util.net;

import java.io.*;

public class SocketData{
  public SocketInterface s;
  public InputStream i;
  public OutputStream o;
  public ObjectInputStream objectInputStream;
  public ObjectOutputStream objectOutputStream;

  public SocketData(SocketInterface s) {
    this.s=s;
    try {
      this.i=s.getInputStream();
      this.o=s.getOutputStream();
      this.objectOutputStream=new ObjectOutputStream(o);
      this.objectInputStream=new ObjectInputStream(i);
    }catch(IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public void dispose() {
    try {
      i.close();
      o.flush();
      o.close();
    }catch(IOException e) {
      e.printStackTrace();
    }finally {
      s.dispose();
    }
  }

  public void writeObject(Object inputData) throws IOException {
    objectOutputStream.writeObject(inputData);
    objectOutputStream.flush();
  }

  public Object readObject() throws IOException,ClassNotFoundException {
    return objectInputStream.readObject();
  }
}
