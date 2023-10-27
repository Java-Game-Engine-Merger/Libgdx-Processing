package pama1234.processing.util.wrapper;

import pama1234.processing.util.element.Part;

public class PartCenter<T extends Part>extends Center<T>{
  public void display() {
    for(Part i:list) i.display();
  }
  public void update() {
    refresh();
    for(Part i:list) i.update();
  }
}
