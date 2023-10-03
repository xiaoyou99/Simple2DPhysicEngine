package org.simple.engine;

import org.simple.engine.physics.FlatBody;
import org.simple.engine.physics.FlatVector;
import org.simple.engine.physics.FlatWorld;
import org.simple.engine.simulation.Camera;
import org.simple.engine.simulation.RandomColorUtil;
import org.simple.engine.simulation.SimulationFrame;
import org.simple.engine.simulation.body.SimulationBody;

/**
 * 程序入口
 */
public class DevApp {

  public static void main(String[] args) {
    Camera camera = new Camera(30.0, 0, 0);
    FlatWorld flatWorld = new FlatWorld();

    FlatVector position = new FlatVector(0d, 0d);
    double radius = 1.2d;
    FlatBody circleBody = FlatBody.createCircleBody(position, 11d, 0.2d, false, radius);

    FlatVector position2 = new FlatVector(0.5d, 0.5d);
    double radius2 = 0.2d;
    FlatBody circleBody2 = FlatBody.createCircleBody(position2, 11d, 0.2d, false, radius2);

    flatWorld.addBody(circleBody);
    flatWorld.addBody(circleBody2);

    // 初始化Frame
    SimulationFrame simulationFrame = new SimulationFrame(camera, flatWorld);
    simulationFrame.getSimulationPanel().addSimulationBody(new SimulationBody(RandomColorUtil.getRandomColor(), circleBody));
    simulationFrame.getSimulationPanel().addSimulationBody(new SimulationBody(RandomColorUtil.getRandomColor(), circleBody2));
    simulationFrame.start();

  }
}
