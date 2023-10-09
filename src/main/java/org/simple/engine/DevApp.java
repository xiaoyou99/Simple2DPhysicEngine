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
    randomBodyDrawing();
//    validateCircleAndBoxCorrect();
//    circleCollision();
//    boxRotation();
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

    double min = -30d;
    double max = 30d;
//    for (int i = 0; i < 1; i++) {
//      FlatVector position = new FlatVector(RandomUtil.randomDouble(min, max), RandomUtil.randomDouble(min, max));
//      double radius = RandomUtil.randomDouble(2d, 3d);
//      FlatBody circleBody = FlatBody.createCircleBody(position, 1.2d, 0.4d, false, radius);
//      flatWorld.addBody(circleBody);
//
//
//      FlatVector position2 = new FlatVector(RandomUtil.randomDouble(min, max), RandomUtil.randomDouble(min, max));
//      double radius2 = RandomUtil.randomDouble(2d, 3d);
//      FlatBody circleBody2 = FlatBody.createCircleBody(position2, 1.2d, 0.4d, false, radius);
//      flatWorld.addBody(circleBody2);
//
//      FlatVector position2 = new FlatVector(RandomUtil.randomDouble(min, max), RandomUtil.randomDouble(min, max));
//      double width = RandomUtil.randomDouble(2d, 3d);
//      double height = RandomUtil.randomDouble(3d, 4d);
//      FlatBody boxBody = FlatBody.createBoxBody(position2, 1.2d, 0.4d, false, width, height);
////      boxBody.rotate(Math.PI * RandomUtil.randomDouble(0, 2d));
////      boxBody.setRotationVelocity(RandomUtil.randomDouble(Math.PI / 3, Math.PI / 2));
//      flatWorld.addBody(boxBody);
//    }

    for(int i = 0; i < 4; i ++) {
      double minX = i * 15 - 30;
      double maxX = (i + 1) * 15 - 30;
      for(int j = 0; j < 4; j++) {
        double minY = j * 15 - 30;
        double maxY = (j + 1) * 15 - 30;
        addRandomBody(flatWorld, minX, maxX, minY, maxY, RandomUtil.randomDouble(2,5));
      }
    }

    // 初始化Frame
    SimulationFrame simulationFrame = new SimulationFrame(camera, flatWorld);
    for (FlatBody body : flatWorld.getBodies()) {
      simulationFrame.getSimulationPanel().addSimulationBody(new SimulationBody(RandomUtil.getRandomColor(), body));
    }
    simulationFrame.start();
  }

  private static void addRandomBody(FlatWorld world, double minX, double maxX, double minY, double maxY, double radiusOrWidth) {
    FlatVector position = new FlatVector(RandomUtil.randomDouble(minX + radiusOrWidth, maxX - radiusOrWidth),
        RandomUtil.randomDouble(minY + radiusOrWidth, maxY - radiusOrWidth));
    double restitution = 0.4d;
    FlatBody body;
    if (RandomUtil.randomInt(1, 2) == 1) {
      body = FlatBody.createCircleBody(position, 1.2d, restitution, false, RandomUtil.randomDouble(3 * radiusOrWidth / 4, radiusOrWidth));
    } else {
      body = FlatBody.createBoxBody(position, 1.2d, restitution, false,
          RandomUtil.randomDouble(3 * radiusOrWidth / 4, radiusOrWidth),
          RandomUtil.randomDouble(3 * radiusOrWidth / 4, radiusOrWidth));
    }
    body.setLinearVelocity(new FlatVector(RandomUtil.randomDouble(-5, 10), RandomUtil.randomDouble(-2, 10)));
    world.addBody(body);
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

  /**
   * 圆形碰撞
   */
  private static void circleCollision() {
    Camera camera = new Camera(24.0, 0, 0);
    FlatWorld flatWorld = new FlatWorld();


    double min = -10d;
    double max = 10d;
    for (int i = 0; i < 10; i++) {
      FlatVector position = new FlatVector(RandomUtil.randomDouble(min, max), RandomUtil.randomDouble(min, max));
      double radius = 1d;
      FlatBody circleBody = FlatBody.createCircleBody(position, 1.2d, 0.2d, false, radius);
      flatWorld.addBody(circleBody);
    }

    // 初始化Frame
    SimulationFrame simulationFrame = new SimulationFrame(camera, flatWorld);
    for (FlatBody body : flatWorld.getBodies()) {
      simulationFrame.getSimulationPanel().addSimulationBody(new SimulationBody(RandomUtil.getRandomColor(), body));
    }
    simulationFrame.start();
  }

  /**
   * 验证长方形旋转
   */
  private static void boxRotation() {
    Camera camera = new Camera(28.0, 0, 0);
    FlatWorld flatWorld = new FlatWorld();


    double min = -10d;
    double max = 10d;
    for (int i = 0; i < 12; i++) {
      FlatVector position2 = new FlatVector(RandomUtil.randomDouble(min, max), RandomUtil.randomDouble(min, max));
      double width = RandomUtil.randomDouble(0.02d, 2d);
      double height = RandomUtil.randomDouble(0.02d, 2d);
      FlatBody boxBody = FlatBody.createBoxBody(position2, 1.2d, 0.2d, false, width, height);
      boxBody.rotate(Math.PI * RandomUtil.randomDouble(0d, 1d));
      flatWorld.addBody(boxBody);
    }

//    FlatVector position2 = new FlatVector(1, 0);
//    double width = 2;
//    double height = 2;
//    FlatBody boxBody = FlatBody.createBoxBody(position2, 1.2d, 0.2d, false, width, height);
//    flatWorld.addBody(boxBody);
//
//    FlatVector position1 = new FlatVector(3, 0);
//    double width1 = 1.8;
//    double height1 = 1.8;
//    FlatBody boxBody1 = FlatBody.createBoxBody(position1, 1.2d, 0.2d, false, width1, height1);
//    flatWorld.addBody(boxBody1);

    // 初始化Frame
    SimulationFrame simulationFrame = new SimulationFrame(camera, flatWorld);
    for (FlatBody body : flatWorld.getBodies()) {
      simulationFrame.getSimulationPanel().addSimulationBody(new SimulationBody(RandomUtil.getRandomColor(), body));
    }
    simulationFrame.start();
  }
}
