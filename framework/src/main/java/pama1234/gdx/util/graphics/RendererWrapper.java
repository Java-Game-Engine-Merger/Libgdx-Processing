package pama1234.gdx.util.graphics;

import org.jetbrains.annotations.Nullable;

import pama1234.util.function.ExecuteFunction;

public class RendererWrapper{
  @Nullable
  public Object renderer;
  public ExecuteFunction from,to;

  public RendererWrapper(Object renderer) {
    this.renderer=renderer;
  }

  public RendererWrapper(Object renderer,ExecuteFunction from,ExecuteFunction to) {
    this(renderer);
    this.from=from;
    this.to=to;
  }

  public void from() {
    if(from!=null) from.execute();
  }

  public void to() {
    if(to!=null) to.execute();
  }
}
