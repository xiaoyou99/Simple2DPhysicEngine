package org.simple.engine.physics;

/**
 * 向量运算类
 *
 * @since 2023/09/26
 */
public class FlatMath {
  public static double length(FlatVector vector) {
    return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
  }

  public static FlatVector normalize(FlatVector vector) {
    double length = length(vector);
    return FlatVector.divide(vector, length);
  }

  public static double distance(FlatVector v1, FlatVector v2) {
    double dx = v1.x - v2.x;
    double dy = v1.y - v2.y;
    return Math.sqrt(dx * dx + dy * dy);
  }

  public static double dot(FlatVector v1, FlatVector v2) {
    return v1.x * v2.x + v1.y * v2.y;
  }

  /**
   * 返回叉乘结果。叉乘结果是个垂直于屏幕的向量，这里只返回这个向量的长度（正号代表垂直于屏幕向外，负号代表向里）
   *
   * @param v1 向量1
   * @param v2 向量2
   * @return 叉乘结果
   */
  public static double cross(FlatVector v1, FlatVector v2) {
    // x_1y_2 - x_2y_1
    return v1.x * v2.y - v2.x * v1.y;
  }
}
