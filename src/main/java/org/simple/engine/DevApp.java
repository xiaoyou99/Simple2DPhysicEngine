package org.simple.engine;

import org.simple.engine.physics.FlatBody;
import org.simple.engine.physics.FlatVector;
import org.simple.engine.physics.FlatWorld;
import org.simple.engine.simulation.Camera;
import org.simple.engine.simulation.SimulationFrame;
import org.simple.engine.simulation.body.SimulationBody;
import org.simple.engine.util.RandomUtil;

/**
 * 程序入口
 */
public class DevApp {

  public static void main(String[] args) {
//    twoCircleDrawing();
//    randomBodyDrawing();
    validateCircleAndBoxCorrect();
  }

  /**
   * 画两个圆，验证基本正确性
   */
  private static void twoCircleDrawing() {
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
    simulationFrame.getSimulationPanel().addSimulationBody(new SimulationBody(RandomUtil.getRandomColor(), circleBody));
    simulationFrame.getSimulationPanel().addSimulationBody(new SimulationBody(RandomUtil.getRandomColor(), circleBody2));
    simulationFrame.start();
  }

  /**
   * 随机画5个box和5个圆形
   */
  private static void randomBodyDrawing() {
    Camera camera = new Camera(15.0, 0, 0);
    FlatWorld flatWorld = new FlatWorld();

    double min = -10d;
    double max = 10d;
    for (int i = 0; i < 5; i++) {
      FlatVector position = new FlatVector(RandomUtil.randomDouble(min, max), RandomUtil.randomDouble(min, max));
      double radius = RandomUtil.randomDouble(0.02d, 3d);
      FlatBody circleBody = FlatBody.createCircleBody(position, 1.2d, 0.2d, false, radius);
      flatWorld.addBody(circleBody);

      FlatVector position2 = new FlatVector(RandomUtil.randomDouble(min, max), RandomUtil.randomDouble(min, max));
      double width = RandomUtil.randomDouble(0.02d, 2d);
      double height = RandomUtil.randomDouble(0.02d, 2d);
      FlatBody boxBody = FlatBody.createBoxBody(position2, 1.2d, 0.2d, false, width, height);
      flatWorld.addBody(boxBody);
    }

    // 初始化Frame
    SimulationFrame simulationFrame = new SimulationFrame(camera, flatWorld);
    for (FlatBody body : flatWorld.getBodies()) {
      simulationFrame.getSimulationPanel().addSimulationBody(new SimulationBody(RandomUtil.getRandomColor(), body));
    }
    simulationFrame.start();
  }

  /**
   * 画一个圆和一个长方形，验证基本的几何关系
   */
  private static void validateCircleAndBoxCorrect() {
    Camera camera = new Camera(15.0, 500, -200);
    FlatWorld flatWorld = new FlatWorld();



    FlatVector position2 = new FlatVector(0, 0);
    double width = 10;
    double height = 15;
    FlatBody boxBody = FlatBody.createBoxBody(position2, 1.2d, 0.2d, false, width, height);
    flatWorld.addBody(boxBody);

    FlatVector position = new FlatVector(0, 0);
    double radius = 5;
    FlatBody circleBody = FlatBody.createCircleBody(position, 1.2d, 0.2d, false, radius);
    flatWorld.addBody(circleBody);


    // 初始化Frame
    SimulationFrame simulationFrame = new SimulationFrame(camera, flatWorld);
    for (FlatBody body : flatWorld.getBodies()) {
      simulationFrame.getSimulationPanel().addSimulationBody(new SimulationBody(RandomUtil.getRandomColor(), body));
    }
    simulationFrame.start();
  }
}
