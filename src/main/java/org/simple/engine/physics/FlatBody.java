package org.simple.engine.physics;

/**
 * 平面刚体类
 *
 * @since 2023/09/26
 */
public class FlatBody {

  private FlatVector position;
  private FlatVector linearVelocity;
  public double rotation;
  public double rotationVelocity;

  public final double mass;
  public final double area;
  public final double density;
  // 弹性
  public final double restitution;
  public final boolean isStatic;
  public final double radius;
  public final double width;
  public final double height;
  public final BodyTypeEnum bodyType;

  private FlatBody(FlatVector position, double mass, double area, double density,
      double restitution, boolean isStatic, double radius, double width, double height, BodyTypeEnum bodyType) {
    this.position = position;
    this.linearVelocity = FlatVector.Zero;
    this.rotation = 0f;
    this.rotationVelocity = 0f;

    this.mass = mass;
    this.area = area;
    this.density = density;
    this.restitution = restitution;
    this.isStatic = isStatic;
    this.radius = radius;
    this.width = width;
    this.height = height;
    this.bodyType = bodyType;
  }

  public static final FlatBody createCircleBody(FlatVector position, double density,
      double restitution, boolean isStatic, double radius) {
    validateDensity(density);
    double area = Math.PI * radius * radius;
    validateArea(area);
    double mass = density * area;
    return new FlatBody(position, mass, area, density, restitution, isStatic, radius, 0f,0f, BodyTypeEnum.CIRCLE);
  }

  private static void validateDensity(double density) {

  }

  private static void validateArea(double area) {

  }

  // getter and setter
  public FlatVector getPosition() {
    return position;
  }

  public void setPosition(FlatVector position) {
    this.position = position;
  }

  public FlatVector getLinearVelocity() {
    return linearVelocity;
  }

  public void setLinearVelocity(FlatVector linearVelocity) {
    this.linearVelocity = linearVelocity;
  }
}
