package org.simple.engine.simulation.input;

import java.awt.Component;

/**
 * 抽象inputHandler
 *
 * @since 2023/10/03
 */
public abstract class AbstractInputHandler implements InputHandler{
  protected Component component;

  public AbstractInputHandler(Component component) {
    this.component = component;
  }
}
