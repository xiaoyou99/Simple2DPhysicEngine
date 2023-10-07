package org.simple.engine.simulation.input;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 抽象键盘处理类
 *
 * @since 2023/10/07
 */
public abstract class AbstractKeyboardInputHandler extends AbstractInputHandler implements InputHandler {
  private CustomKeyboardAdapter customKeyboardAdapter;

  public AbstractKeyboardInputHandler(Component component) {
    super(component);
    this.customKeyboardAdapter = new CustomKeyboardAdapter();
  }

  @Override
  public void install() {
    component.setFocusable(true);
    component.addKeyListener(this.customKeyboardAdapter);
  }

  @Override
  public void uninstall() {
    component.removeKeyListener(this.customKeyboardAdapter);
  }

  @Override
  public boolean isActive() {
    return false;
  }

  private class CustomKeyboardAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      onKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
      onKeyReleased(e);
    }
  }

  protected void onKeyPressed(KeyEvent e) {
  }

  protected void onKeyReleased(KeyEvent e) {
  }
}
