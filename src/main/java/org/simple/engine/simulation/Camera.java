package org.simple.engine.simulation;

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
}
