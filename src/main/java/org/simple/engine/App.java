package org.simple.engine;

import org.simple.engine.physics.FlatBody;
import org.simple.engine.physics.FlatVector;
import org.simple.engine.simulation.Camera;
import org.simple.engine.simulation.RandomColorUtil;
import org.simple.engine.simulation.SimulationFrame;
import org.simple.engine.simulation.SimulationPanel;
import org.simple.engine.simulation.body.SimulationBody;

/**
 * 程序入口
 */
public class App {
  private SimulationFrame simulationFrame;

  public static void main(String[] args) {
    App app = new App();
    Camera camera = new Camera(50.0, 0, 0);
    app.simulationFrame = new SimulationFrame(camera);

    FlatVector position = new FlatVector(0d, 0d);
    double radius = 1.2d;
    FlatBody circleBody = FlatBody.createCircleBody(position, 11d, 0.2d, false, radius);
    SimulationBody bigCircle = new SimulationBody(RandomColorUtil.getRandomColor(), circleBody);

    FlatVector position2 = new FlatVector(0.5d, 0.5d);
    double radius2 = 0.2d;
    FlatBody circleBody2 = FlatBody.createCircleBody(position2, 11d, 0.2d, false, radius2);
    SimulationBody smallCircle = new SimulationBody(RandomColorUtil.getRandomColor(), circleBody2);

    SimulationPanel simulationPanel = app.simulationFrame.getSimulationPanel();
    simulationPanel.addSimulationBody(bigCircle);
    simulationPanel.addSimulationBody(smallCircle);

    simulationPanel.repaint();
  }
}
