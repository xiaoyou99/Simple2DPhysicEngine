package org.simple.engine.physics;

/**
 * 平面向量类
 *
 * @since 2023/09/24
 */
public class FlatVector {

  public final double x;

  public final double y;

  public FlatVector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static FlatVector Zero = new FlatVector(0f, 0f);

  // static compute methods

  public static FlatVector add(FlatVector v1, FlatVector v2) {
    return new FlatVector(v1.x + v2.x, v1.y + v2.y);
  }

  public static FlatVector sub(FlatVector v1, FlatVector v2) {
    return new FlatVector(v1.x - v2.x, v1.y - v2.y);
  }

  public static FlatVector multiply(FlatVector v, double scale) {
    return new FlatVector(v.x * scale, v.y * scale);
  }

  public static FlatVector divide(FlatVector v, double scale) {
    return new FlatVector(v.x / scale, v.y / scale);
  }

  public static FlatVector negative(FlatVector v) {
    return new FlatVector(-v.x, -v.y);
  }

  // non-static compute methods

  public FlatVector add(FlatVector other) {
    return new FlatVector(this.x + other.x, this.y + other.y);
  }

  public FlatVector sub(FlatVector other) {
    return new FlatVector(this.x - other.x, this.y - other.y);
  }

  public FlatVector multiply(double scale) {
    return new FlatVector(this.x * scale, this.y * scale);
  }

  public FlatVector divide(double scale) {
    return new FlatVector(this.x / scale, this.y / scale);
  }

  public FlatVector negative() {
    return new FlatVector(-this.x, -this.y);
  }

  // equals, hashCode
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!(o instanceof FlatVector)) {
      return false;
    }
    FlatVector other = (FlatVector) o;
    return this.x == other.x && this.y == other.y;
  }

  @Override
  public int hashCode() {
    return Double.hashCode(this.x) * 32 + Double.hashCode(this.y);
  }
}
