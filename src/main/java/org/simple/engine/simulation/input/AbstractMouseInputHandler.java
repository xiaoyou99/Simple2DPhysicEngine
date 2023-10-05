package org.simple.engine.simulation.input;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;

/**
 * 抽象鼠标事件Handler
 *
 * @since 2023/10/03
 */
public abstract class AbstractMouseInputHandler extends AbstractInputHandler implements InputHandler {

  private final MouseAdapter mouseAdapter;

  public AbstractMouseInputHandler(Component component) {
    super(component);
    this.mouseAdapter = new CustomMouseAdapter();
  }

  @Override
  public void install() {
    this.component.addMouseWheelListener(this.mouseAdapter);
    this.component.addMouseListener(this.mouseAdapter);
    this.component.addMouseMotionListener(this.mouseAdapter);
  }

  @Override
  public void uninstall() {
    this.component.removeMouseWheelListener(this.mouseAdapter);
    this.component.removeMouseListener(this.mouseAdapter);
    this.component.removeMouseListener(this.mouseAdapter);
  }

  private class CustomMouseAdapter extends MouseAdapter {

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      onMouseWheelMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
      if (hasActiveMutexHandler()) {
        return;
      }
      onMousePressed(e);
    }

    private boolean hasActiveMutexHandler() {
      List<InputHandler> mutexHandlers = getMutexHandler();
      for (InputHandler handler : mutexHandlers) {
        if (handler.isActive()) {
          return true;
        }
      }
      return false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      if (hasActiveMutexHandler()) {
        return;
      }
      onMouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if (hasActiveMutexHandler()) {
        return;
      }
      onMouseReleased(e);
    }

  }

  protected void onMouseWheelMoved(MouseWheelEvent e) {
  }

  protected void onMousePressed(MouseEvent e) {
  }

  protected void onMouseDragged(MouseEvent e) {
  }

  protected void onMouseReleased(MouseEvent e) {
  }
}
