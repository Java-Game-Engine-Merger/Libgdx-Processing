package pama1234.processing.lang.visual.util.node;

public class StringDataPair extends KeyValuePair{
  public String data;
  @Override
  public String getString() {
    return data;
  }
  @Override
  public char getChar() {
    return data.charAt(0);
  }
  @Override
  public float getNumber() {
    return Float.parseFloat(data);
  }
  @Override
  public byte getBoolean() {
    return data.equals("true")?TRUE:FALSE;
  }
}
