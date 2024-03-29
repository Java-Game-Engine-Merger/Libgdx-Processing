package pama1234.util.function;

@FunctionalInterface
public interface ExecuteWith<T>{
  public void execute(T in);
}