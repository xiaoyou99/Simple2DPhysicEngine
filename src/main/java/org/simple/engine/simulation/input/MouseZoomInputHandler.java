package org.simple.engine.simulation.input;

import java.awt.Component;
import java.awt.event.MouseWheelEvent;

/**
 * 缩放Handler: 根据鼠标滚轮调整缩放级别
 *
 * @since 2023/10/03
 */
public class MouseZoomInputHandler extends AbstractMouseInputHandler implements InputHandler {

  private final Object lock;

  private double scale;

  public MouseZoomInputHandler(Component component) {
    super(component);
    this.lock = new Object();
    this.scale = 1.0d;
  }

  @Override
  protected void onMouseWheelMoved(MouseWheelEvent e) {
    int notches = e.getWheelRotation();
    synchronized (lock) {
      if (notches < 0) {
        // 鼠标向上滚动，scale应该增大
        scale *= 1.2d;
      } else {
        // 鼠标向下滚动，scale应该减小
        scale *= 0.8d;
      }
    }
  }

  public double getScaleAndReset() {
    synchronized (lock) {
      double currScale = this.scale;
      // 重置scale, 否则很快就会指数级增长或缩小
      this.scale = 1.0;
      return currScale;
    }
  }

  @Override
  public boolean isActive() {
    return false;
  }
}
