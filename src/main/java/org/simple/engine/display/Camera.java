package org.simple.engine.display;

import org.simple.engine.display.body.LinePic;
import org.simple.engine.world.FlatVector;

/**
 * 相机类：用于将真实刚体对象映射到WorldPanel上
 *
 * @since 2023/09/24
 */
public class Camera {
  // 画布在y轴方向上的像素点个数
  private final int YSize;

  public Camera(int YSize) {
    this.YSize = YSize;
  }

  /**
   * 向量转为线图形
   *
   * @param start  向量起点
   * @param vector 向量
   * @return LinePic
   */
  public LinePic FlatVector2LinePic(FlatVector start, FlatVector vector) {
    return new LinePic(start.x, transferY(start.y), start.x + vector.x,
        transferY(start.y) - vector.y);
  }

  /**
   * y轴向上的坐标系中某点的y坐标，变换为y轴向下的坐标系中的y坐标
   *
   * @param y y轴向上的坐标系中某点的y坐标
   * @return 变换后的y坐标
   */
  private float transferY(float y) {
    return -y + YSize;
  }
}
