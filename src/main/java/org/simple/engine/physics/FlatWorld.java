package org.simple.engine.physics;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.simple.engine.physics.collision.Collisions;

/**
 * RealWorld
 *
 * @since 2023/09/24
 */
@Getter
public class FlatWorld {
  // 属性: 刚体列表
  private List<FlatBody> bodies;

  // 时间序列对象
  protected final TimeStep timeStep;

  // 设置
  private Settings settings;

  // 距离上次step的时间
  private double time;

  public FlatWorld() {
    this.bodies = new ArrayList<>();
    this.timeStep = new TimeStep(Settings.DEFAULT_PERIOD);
    this.settings = new Settings();
    this.time = 0.0d;
  }

  public boolean update(double elapsedTime) {
    return this.update(elapsedTime, 1);
  }

  public boolean update(double elapsedTime, int maximumSteps) {
    if (elapsedTime < 0) {
      elapsedTime = 0.0d;
    }
    // 判断是否需要step
    time += elapsedTime;
    int step = 0;
    for (step = 0; step < maximumSteps; step++) {
      // 如果距离上次step，还不到一个周期时间，则直接break，不需要step
      if (time - settings.getPeriod() < 0) {
        break;
      }
      // 前进一步
      timeStep.update(settings.getPeriod());
      this.step();
      // 更新time
      time -= settings.getPeriod();
    }
    // 如果step > 0，返回true
    return step > 0;
  }


  private void step() {
    // 圆形碰撞处理
//    for (int i = 0; i < bodies.size() - 1; i++) {
//      FlatBody bodyA = bodies.get(i);
//      for (int j = i + 1; j < bodies.size(); j++) {
//        FlatBody bodyB = bodies.get(j);
//        CollisionInfo collisionInfo = Collisions.intersectCircles(bodyA.getPosition(), bodyA.radius,
//            bodyB.getPosition(), bodyB.radius);
//        if (!collisionInfo.isHasCollision()) {
//          continue;
//        }
//        FlatVector move = collisionInfo.getNormal().multiply(collisionInfo.getDepth() / 2);
//        bodyA.move(move.negative());
//        bodyB.move(move);
//      }
//    }

//    // 测试刚体旋转
//    for (FlatBody body : bodies) {
//      body.rotate((Math.PI / 2) * settings.getPeriod());
//    }

    // Box碰撞处理
    for (int i = 0; i < bodies.size() - 1; i++) {
      FlatBody bodyA = bodies.get(i);
      for (int j = i + 1; j < bodies.size(); j++) {
        FlatBody bodyB = bodies.get(j);
        boolean collision = Collisions.intersectPolygons(bodyA.getTransformVertices(), bodyB.getTransformVertices());
        if (collision) {
          System.out.println("111");
        }
      }
    }
  }

  /**
   * 刚体数量
   *
   * @return body数量
   */
  public int getBodyCount() {
    return this.bodies.size();
  }

  /**
   * 添加刚体
   *
   * @param flatBody 刚体
   */
  public void addBody(FlatBody flatBody) {
    bodies.add(flatBody);
  }

  /**
   * 选中一个点，给出包含这个点上的刚体
   *
   * @param position
   * @return 包含这个点上的刚体
   */
  // todo: 后期考虑性能优化
  public List<FlatBody> getBodyAt(FlatVector position) {
    List<FlatBody> findBodies = new ArrayList<>();
    for (FlatBody body : this.bodies) {
      FlatVector subVec = position.sub(body.getPosition());
      switch (body.bodyType) {
        case CIRCLE:
          if (subVec.length() <= body.radius) {
            findBodies.add(body);
          }
          break;
        case BOX:
          if (Math.abs(subVec.x) < body.width / 2 && Math.abs(subVec.y) < body.height / 2) {
            findBodies.add(body);
          }
          break;
        case POLYGON:
          // todo: 待补充
          break;
      }
    }
    return findBodies;
  }

}
