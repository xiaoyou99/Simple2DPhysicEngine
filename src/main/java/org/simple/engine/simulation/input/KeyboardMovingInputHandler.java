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
  private FlatWorld flatWorld;

  public KeyboardMovingInputHandler(Component component, FlatWorld flatWorld) {
    super(component);
    this.flatWorld = flatWorld;
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
    double amount = 0.2;
    switch (keyCode) {
      case KeyEvent.VK_LEFT:
        body.move(FlatVector.multiply(new FlatVector(-1, 0), amount));
        break;
      case KeyEvent.VK_RIGHT:
        body.move(FlatVector.multiply(new FlatVector(1, 0), amount));
        break;
      case KeyEvent.VK_UP:
        body.move(FlatVector.multiply(new FlatVector(0, 1), amount));
        break;
      case KeyEvent.VK_DOWN:
        body.move(FlatVector.multiply(new FlatVector(0, -1), amount));
        break;
      default:
        break;
    }
  }

  private boolean isDirectionKey(int keyCode) {
    return keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_UP
        || keyCode == KeyEvent.VK_DOWN;
  }
}
