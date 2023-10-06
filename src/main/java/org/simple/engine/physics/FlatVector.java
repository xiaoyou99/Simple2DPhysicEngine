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

  public static FlatVector Zero = new FlatVector(0d, 0d);

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

  public static double length(FlatVector v) {
    return Math.sqrt(v.x * v.x + v.y * v.y);
  }

  public static FlatVector transform(FlatVector v, FlatTransform transform) {
    // x_new = x cos - y sin + offsetX
    // y_new = x sin + y cos + offsetY
    return new FlatVector(v.x * transform.cos - v.y * transform.sin + transform.positionX,
        v.x * transform.sin + v.y * transform.cos + transform.positionY);
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

  public double length() {
    return Math.sqrt(this.x * this.x + this.y * this.y);
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
