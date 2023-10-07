package org.simple.engine.physics;

import org.simple.engine.util.MathUtil;

/**
 * 平面刚体类
 *
 * @since 2023/09/26
 */
public class FlatBody {
  // 属性限制
  public static final double MIN_AREA = 0.01d * 0.01d;
  public static final double MAX_AREA = 64.0d * 64.0d;
  public static final double MIN_DENSITY = 0.5d; // g/cm^3
  public static final double MAX_DENSITY = 24.5d; // g/cm^3
  public static final double MIN_RESTITUTION = 0.0d;
  public static final double MAX_RESTITUTION = 1.0d;

  // 可变属性
  private FlatVector position;
  private FlatVector linearVelocity;
  private double rotation;
  private double rotationVelocity;
  private FlatVector[] transformVertices;
  private boolean transformUpdatedRequired;

  // 不可变属性
  public final double mass;
  public final double area;
  public final double density;
  public final double restitution; // 弹性
  public final boolean isStatic;
  public final double radius;
  public final double width;
  public final double height;
  public final FlatVector[] vertices;
  public final int[] triangles;
  public final BodyTypeEnum bodyType;

  private FlatBody(FlatVector position, double mass, double area, double density,
      double restitution, boolean isStatic, double radius, double width, double height, BodyTypeEnum bodyType) {
    this.position = position;
    this.linearVelocity = FlatVector.Zero;
    this.rotation = 0d;
    this.rotationVelocity = 0d;

    this.mass = mass;
    this.area = area;
    this.density = density;
    this.restitution = restitution;
    this.isStatic = isStatic;
    this.radius = radius;
    this.width = width;
    this.height = height;
    this.bodyType = bodyType;
    
    if (BodyTypeEnum.BOX.equals(bodyType)) {
      this.vertices = createBoxVertices(width, height);
      this.transformVertices = createBoxVertices(width, height);
      this.triangles = createBoxTriangles();
    } else {
      this.vertices = null;
      this.transformVertices = null;
      this.triangles = null;
    }
    this.transformUpdatedRequired = true;
  }

  private FlatVector[] createBoxVertices(double width, double height) {
    double left = - width / 2d;
    double right = width / 2d;
    double top = height / 2d;
    double bottom = - height / 2d;

    FlatVector[] vertices = new FlatVector[4];
    vertices[0] = new FlatVector(left, top);
    vertices[1] = new FlatVector(right, top);
    vertices[2] = new FlatVector(right, bottom);
    vertices[3] = new FlatVector(left, bottom);

    return vertices;
  }

  private int[] createBoxTriangles() {
    // 0 left-top  1  right-top  2 right-bottom  3 left-bottom
    return new int[] {0, 1, 2, 0, 2, 3};
  }

  /**
   * 创建圆形刚体
   *
   * @param position 中心位置
   * @param density 密度
   * @param restitution 弹性
   * @param isStatic 是否静态
   * @param radius 半径
   * @return 刚体
   */
  public static FlatBody createCircleBody(FlatVector position, double density,
      double restitution, boolean isStatic, double radius) {
    validateDensity(density);
    double area = Math.PI * radius * radius;
    validateArea(area);
    double mass = density * area; // 默认所有body在垂直于屏幕方向的高度都是1
    restitution = MathUtil.clamp(restitution, MIN_RESTITUTION, MAX_RESTITUTION);
    return new FlatBody(position, mass, area, density, restitution, isStatic, radius, 0d,0d, BodyTypeEnum.CIRCLE);
  }

  /**
   * 创建长方形刚体
   *
   * @param position 中心位置
   * @param density 密度
   * @param restitution 弹性
   * @param isStatic 是否静态
   * @param width x方向长度
   * @param height y方向长度
   * @return 刚体
   */
  public static FlatBody createBoxBody(FlatVector position, double density,
      double restitution, boolean isStatic, double width, double height) {
    validateDensity(density);
    double area = width * height;
    validateArea(area);
    double mass = density * area; // 默认所有body在垂直于屏幕方向的高度都是1
    restitution = MathUtil.clamp(restitution, MIN_RESTITUTION, MAX_RESTITUTION);
    return new FlatBody(position, mass, area, density, restitution, isStatic, 0d, width,height, BodyTypeEnum.BOX);
  }

  private static void validateDensity(double density) {
    if (density < MIN_DENSITY || density > MAX_DENSITY) {
      throw new IllegalArgumentException("density value illegal, value is [" + density + "]");
    }
  }

  private static void validateArea(double area) {
    if (area < MIN_AREA || area > MAX_AREA) {
      throw new IllegalArgumentException("area value illegal, value is [" + area + "]");
    }
  }

  // non-static methods
  public FlatVector[] getTransformVertices() {
    if (this.vertices == null) {
      return this.transformVertices;
    }
    if (this.transformUpdatedRequired) {
      FlatTransform transform = new FlatTransform(this.position, this.rotation);
      for(int i = 0; i < this.vertices.length; i++) {
        FlatVector v = this.vertices[i];
        this.transformVertices[i] = FlatVector.transform(v, transform);
      }
    }

    this.transformUpdatedRequired = false;
    return this.transformVertices;
  }

  public void step(double elapsedTime) {
    this.position = FlatVector.add(this.position, FlatVector.multiply(this.linearVelocity, elapsedTime));
    this.rotation += this.rotationVelocity * elapsedTime;
  }

  public void move(FlatVector move)  {
    this.position = new FlatVector(this.position.x + move.x, this.position.y + move.y);
    this.transformUpdatedRequired = true;
  }

  public void moveTo(FlatVector destination)  {
    this.position = destination;
    this.transformUpdatedRequired = true;
  }

  public void rotate(double angle) {
    this.rotation += angle;
    this.transformUpdatedRequired = true;
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
