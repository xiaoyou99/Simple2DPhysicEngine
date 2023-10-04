package org.simple.engine.simulation;

import java.awt.Point;
import org.simple.engine.physics.FlatVector;

/**
 * 相机类：用于缩放和坐标系转换
 *
 * @since 2023/09/24
 */
public class Camera {
  // 每米映射成多少像素
  public double scale;

  // x方向的偏移量(像素为单位)
  public double offsetX;

  // y方向的偏移量(像素为单位)
  public double offsetY;

  public Camera(double scale, double offsetX, double offsetY) {
    this.scale = scale;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }

  /**
   * 屏幕上的一个像素，映射到物理世界中的一个点
   *
   * 现实世界中的一个点(x,y)，通过如下方式映射成swing坐标系的一个点(x_0,y_0):
   * 先乘以scale，再做一个矩阵变换。写成方程如下：
   * [1 0]  [scale * x] + [w/2 + offsetX] = [x_0]
   * [0 -1] [scale * y] + [h/2 + offsetY] = [y_0]
   *
   * @param point swing中的Point
   * @param w panel的width
   * @param h panel的height
   * @return 一个FlatVector对象，代表物理世界中的一个向量
   */
  public FlatVector pixel2Vec(Point point, int w, int h) {
    double x = (point.getX() - (w / 2.0) - offsetX) / scale;
    double y = (-point.getY() + (h / 2.0) + offsetY) / scale;
    return new FlatVector(x, y);
  }
}
