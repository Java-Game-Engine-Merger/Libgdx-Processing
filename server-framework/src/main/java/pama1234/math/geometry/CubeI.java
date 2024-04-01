package pama1234.math.geometry;

public interface CubeI extends RectI{

  public float x();
  public float y();
  public float z();
  //------------------
  public float w();
  public float h();
  public float l();
  //------------------
  public default float x1() {
    return x();
  }
  public default float y1() {
    return y();
  }
  public default float z1() {
    return z();
  }
  public default float x2() {
    return x()+w();
  }
  public default float y2() {
    return y()+h();
  }
  public default float z2() {
    return z()+l();
  }
  //------------------
  // public void w(GetFloat in);
  public default float cx() {
    return x()+w()/2f;
  }
  public default float cy() {
    return y()+h()/2f;
  }
  public default float cz() {
    return z()+l()/2f;
  }
  public default float xnw() {
    return x()+w();
  }
  public default float ynh() {
    return y()+h();
  }
  public default float znl() {
    return z()+l();
  }
  public default boolean contains(CubeI r) {
    return this.w()>0&&this.h()>0&&this.l()>0
      &&r.w()>0&&r.h()>0&&r.l()>0
      &&r.x()>=this.x()&&r.x()+r.w()<=this.x()+this.w()
      &&r.y()>=this.y()&&r.y()+r.h()<=this.y()+this.h()
      &&r.z()>=this.z()&&r.z()+r.l()<=this.z()+this.l();
  }
}
