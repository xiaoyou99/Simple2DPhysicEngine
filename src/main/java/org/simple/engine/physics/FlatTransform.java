package org.simple.engine.physics;

/**
 * FlatTransform
 *
 * @since 2023/10/06
 */
public class FlatTransform {
  public final double positionX;
  public final double positionY;
  public final double sin;
  public final double cos;

  public static final FlatTransform ZERO = new FlatTransform(0d, 0d, 0d);

  public FlatTransform(FlatVector position, double angle) {
    this.positionX = position.x;
    this.positionY = position.y;
    this.sin = Math.sin(angle);
    this.cos = Math.cos(angle);
  }

  public FlatTransform(double angle) {
    this.positionX = 0d;
    this.positionY = 0d;
    this.sin = Math.sin(angle);
    this.cos = Math.cos(angle);
  }

  public FlatTransform(double x, double y, double angle) {
    this.positionX = x;
    this.positionY = y;
    this.sin = Math.sin(angle);
    this.cos = Math.cos(angle);
  }
}
