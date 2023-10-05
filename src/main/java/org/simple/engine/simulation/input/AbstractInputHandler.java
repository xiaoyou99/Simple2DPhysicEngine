package org.simple.engine.simulation.input;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象inputHandler
 *
 * @since 2023/10/03
 */
public abstract class AbstractInputHandler implements InputHandler{
  protected Component component;

  private List<InputHandler> mutexInputHandlers;

  public AbstractInputHandler(Component component) {
    this.component = component;
    mutexInputHandlers = new ArrayList<>();
  }

  @Override
  public List<InputHandler> getMutexHandler() {
    return mutexInputHandlers;
  }

  @Override
  public void addMutexHandler(InputHandler inputHandler) {
    this.mutexInputHandlers.add(inputHandler);
  }
}
