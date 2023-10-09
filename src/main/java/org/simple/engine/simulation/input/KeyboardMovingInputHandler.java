package org.simple.engine.simulation.input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import org.simple.engine.physics.FlatBody;
import org.simple.engine.physics.FlatVector;
import org.simple.engine.physics.FlatWorld;

/**
 * 使用方向键，控制某一个刚体的上下左右移动
 *
 * @since 2023/10/07
 */
public class KeyboardMovingInputHandler extends AbstractKeyboardInputHandler {
  private static final double FORCE_MAGNITUDE = 64;

  private FlatWorld flatWorld;
  private double dx;
  private double dy;

  public KeyboardMovingInputHandler(Component component, FlatWorld flatWorld) {
    super(component);
    this.flatWorld = flatWorld;
    this.dx = 0;
    this.dy = 0;
  }

  protected void onKeyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (!isDirectionKey(keyCode)) {
      return;
    }
    // 防止找不到可以移动的body
    if (flatWorld == null || flatWorld.getBodyCount() == 0) {
      return;
    }
    FlatBody body = flatWorld.getBodies().get(0);
    switch (keyCode) {
      case KeyEvent.VK_LEFT:
        dx--;
        break;
      case KeyEvent.VK_RIGHT:
        dx++;
        break;
      case KeyEvent.VK_UP:
        dy++;
        break;
      case KeyEvent.VK_DOWN:
        dy--;
        break;
      default:
        break;
    }
    if (Math.abs(dx) > 0.0001 || Math.abs(dy) > 0.0001) {
      applyForceToBody(body);
    }
    dx = 0;
    dy = 0;
  }

  private void applyForceToBody(FlatBody body) {
    FlatVector forceDirection = new FlatVector(dx, dy);
    FlatVector force = FlatVector.multiply(forceDirection, FORCE_MAGNITUDE);
    body.applyForce(force);
  }

  private boolean isDirectionKey(int keyCode) {
    return keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_UP
        || keyCode == KeyEvent.VK_DOWN;
  }
}
