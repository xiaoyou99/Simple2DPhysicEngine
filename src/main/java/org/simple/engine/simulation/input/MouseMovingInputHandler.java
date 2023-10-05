package org.simple.engine.simulation.input;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.List;
import org.simple.engine.physics.FlatBody;
import org.simple.engine.physics.FlatVector;
import org.simple.engine.physics.FlatWorld;
import org.simple.engine.simulation.Camera;

/**
 * 鼠标移动刚体位置
 *
 * @since 2023/10/05
 */
public class MouseMovingInputHandler extends AbstractMouseInputHandler implements InputHandler {
  private FlatVector start;

  private Camera camera;

  private FlatWorld flatWorld;

  private FlatBody selectBody;

  private boolean moving;

  public MouseMovingInputHandler(Component component, Camera camera, FlatWorld flatWorld) {
    super(component);
    this.camera = camera;
    this.flatWorld = flatWorld;
  }

  @Override
  protected void onMousePressed(MouseEvent e) {
    this.clearMovingState();
    this.start = camera.pixel2Vec(e.getPoint(), component.getWidth(), component.getHeight());
    List<FlatBody> selectBodies = this.flatWorld.getBodyAt(start);
    this.selectBody = (selectBodies == null || selectBodies.size() == 0) ? null : selectBodies.get(0);
    this.moving = this.selectBody != null;
  }

  @Override
  protected void onMouseDragged(MouseEvent e) {
    if (selectBody == null || !moving || start == null) {
      return;
    }

    FlatVector currentPos = camera.pixel2Vec(e.getPoint(), component.getWidth(), component.getHeight());
    FlatVector move = currentPos.sub(start);
    // 更新物理世界中的刚体位置
    this.selectBody.move(move);

    this.start = currentPos;
  }

  @Override
  protected void onMouseReleased(MouseEvent e) {
    this.clearMovingState();
  }

  private void clearMovingState() {
    this.start = null;
    this.selectBody = null;
    this.moving = false;
  }

  @Override
  public boolean isActive() {
    return moving;
  }
}
