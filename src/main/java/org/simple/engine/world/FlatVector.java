package org.simple.engine.world;

/**
 * class usage
 *
 * @since 2023/09/24
 */
public class FlatVector {
  public final float x;

  public final float y;

  public FlatVector(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public static FlatVector Zero = new FlatVector(0f, 0f);

  public FlatVector add(FlatVector other) {
    return new FlatVector(this.x + other.x, this.y + other.y);
  }

  public FlatVector sub(FlatVector other) {
    return new FlatVector(this.x - other.x, this.y - other.y);
  }

  public FlatVector multiply(float scale) {
    return new FlatVector(this.x * scale, this.y * scale);
  }
}
