package org.simple.engine.simulation.input;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

/**
 * 抽象鼠标事件Handler
 *
 * @since 2023/10/03
 */
public class AbstractMouseInputHandler extends AbstractInputHandler implements InputHandler {

  private final MouseAdapter mouseAdapter;

  public AbstractMouseInputHandler(Component component) {
    super(component);
    this.mouseAdapter = new CustomMouseAdapter();
  }

  @Override
  public void install() {
    this.component.addMouseWheelListener(this.mouseAdapter);
  }

  @Override
  public void uninstall() {
    this.component.removeMouseWheelListener(this.mouseAdapter);
  }

  private class CustomMouseAdapter extends MouseAdapter {

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      onMouseWheelMoved(e);
    }
  }

  protected void onMouseWheelMoved(MouseWheelEvent e) {
  }
}
