package pama1234.math;

import com.esotericsoftware.kryo.util.Pool;

import pama1234.math.vec.Vec3f;

/**
 * 记得释放obtain的对象
 */
public class MathPool{
  public static Vec3fPool vec3fPool=new Vec3fPool();

  public static class Vec3fPool extends Pool<Vec3f> implements ExecuteFree<Vec3f>{
    public Vec3fPool() {
      this(true,true);
    }
    public Vec3fPool(boolean threadSafe,boolean softReferences) {
      this(threadSafe,softReferences,Integer.MAX_VALUE);
    }

    public Vec3fPool(boolean threadSafe,boolean softReferences,int maximumCapacity) {
      super(threadSafe,softReferences,maximumCapacity);
    }

    @Override
    protected Vec3f create() {
      return new Vec3f();
    }
    @Override
    public Vec3f obtain() {
      Vec3f obtain=obtainInner();
      obtain.set(0);
      return obtain;
    }

    public Vec3f obtainInner() {
      Vec3f obtain=super.obtain();
      obtain.pool=this;
      return obtain;
    }

    public Vec3f obtain(float x,float y,float z) {
      Vec3f obtain=obtainInner();
      obtain.set(x,y,z);
      return obtain;
    }
  }

  public interface ExecuteFree<T>{
    void free(T o);
  }
}
