package org.simple.engine.simulation.input;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import org.simple.engine.physics.FlatVector;

/**
 * 鼠标左键上下左右移动相机焦点
 *
 * @since 2023/10/04
 */
public class MousePanningInputHandler extends AbstractMouseInputHandler implements InputHandler{

  private final Object lock;

  private Point start;

  // 记录当前的移动offset, 用于更新camera的offset
  private double offsetX;
  private double offsetY;

  public MousePanningInputHandler(Component component) {
    super(component);
    this.lock = new Object();
  }

  @Override
  protected void onMousePressed(MouseEvent e) {
    this.clearPanningState();
    this.start = e.getPoint();
  }

  @Override
  protected void onMouseDragged(MouseEvent e) {
    double x = e.getPoint().getX() - start.getX();
    double y = e.getPoint().getY() - start.getY();

    synchronized (lock) {
      this.offsetX += x;
      this.offsetY += y;
    }

    this.start = e.getPoint();
  }

  @Override
  protected void onMouseReleased(MouseEvent e) {
    this.clearPanningState();
  }

  private void clearPanningState() {
    this.start = null;
  }

  /**
   * 获取当前的offset并重置
   *
   * @return 当前offset
   */
  public FlatVector getOffsetAndReset() {
    synchronized (lock) {
      FlatVector flatVector = new FlatVector(offsetX, offsetY);
      this.offsetX = 0;
      this.offsetY = 0;
      return flatVector;
    }
  }

}
