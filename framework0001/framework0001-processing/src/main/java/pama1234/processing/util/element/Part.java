package pama1234.processing.util.element;

import pama1234.processing.util.listener.EssentialListener;
import processing.core.PGraphics;

public abstract class Part implements EssentialListener{
  public PGraphics g;
  public Part(PGraphics g) {
    this.g=g;
  }
}
