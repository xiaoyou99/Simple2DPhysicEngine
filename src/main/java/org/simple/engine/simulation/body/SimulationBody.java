package org.simple.engine.simulation.body;

import java.awt.Color;
import lombok.Getter;
import org.simple.engine.physics.FlatBody;

/**
 * Simulation body
 *
 * @since 2023/10/03
 */
@Getter
public class SimulationBody {
  private Color color;

  private FlatBody flatBody;

  public SimulationBody(Color color, FlatBody flatBody) {
    this.color = color;
    this.flatBody = flatBody;
  }
}
