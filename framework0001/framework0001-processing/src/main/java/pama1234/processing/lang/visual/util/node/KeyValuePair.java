package pama1234.processing.lang.visual.util.node;

public abstract class KeyValuePair extends Node{
  public Name key;
  public int type;
  public abstract String getString();
  public abstract char getChar();
  public abstract float getNumber();
  public abstract byte getBoolean();
}
