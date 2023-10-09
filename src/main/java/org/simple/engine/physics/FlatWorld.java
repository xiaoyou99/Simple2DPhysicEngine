package org.simple.engine.physics;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.simple.engine.physics.collision.CollisionInfo;
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
  private final TimeStep timeStep;

  // 设置
  private Settings settings;

  // 重力
  private FlatVector gravity;

  // 距离上次step的时间
  private double time;

  public FlatWorld() {
    this.bodies = new ArrayList<>();
    this.timeStep = new TimeStep(Settings.DEFAULT_PERIOD);
    this.settings = new Settings();
    this.time = 0.0d;
    this.gravity = new FlatVector(0, -9.81d);
  }

  /**
   * 更新物理世界
   *
   * @param elapsedTime 经过的时间
   * @return step是否增加
   */
  public boolean update(double elapsedTime) {
    return this.update(elapsedTime, 10);
  }

  /**
   * 更新物理世界
   *
   * @param elapsedTime  经过的时间
   * @param maximumSteps 最多迭代多少步
   * @return step是否增加
   */
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
    // body move
    for (FlatBody body : bodies) {
      body.step(settings.getPeriod());
    }

    // body collision resolve
    for (int i = 0; i < bodies.size() - 1; i++) {
      FlatBody bodyA = bodies.get(i);
      for (int j = i + 1; j < bodies.size(); j++) {
        FlatBody bodyB = bodies.get(j);
        CollisionInfo collisionInfo = detectCollision(bodyA, bodyB);
        if (!collisionInfo.isHasCollision()) {
          continue;
        }
        resolveCollision(bodyA, bodyB, collisionInfo.getNormal(), collisionInfo.getDepth());
      }
    }

    // todo: delete 这里是测试封闭世界的代码，后续需要删除
    double min = -50;
    double max = 50;
    for (int i = 0; i < bodies.size(); i++) {
      FlatBody bodyA = bodies.get(i);
      double offset = (max - min);
      if (bodyA.getPosition().x < min) {
        bodyA.setPosition(FlatVector.add(bodyA.getPosition(), new FlatVector(offset, 0)));
      }
      if (bodyA.getPosition().x > max) {
        bodyA.setPosition(FlatVector.sub(bodyA.getPosition(), new FlatVector(offset, 0)));
      }
      if (bodyA.getPosition().y < min) {
        bodyA.setPosition(FlatVector.add(bodyA.getPosition(), new FlatVector(0, offset)));
      }
      if (bodyA.getPosition().y > max) {
        bodyA.setPosition(FlatVector.sub(bodyA.getPosition(), new FlatVector(0, offset)));
      }
    }
  }

  private void resolveCollision(FlatBody bodyA, FlatBody bodyB, FlatVector normal, double depth) {
    // 刚体不可重叠
    bodyA.move(FlatVector.multiply(normal.negative(), depth / 2));
    bodyB.move(FlatVector.multiply(normal, depth / 2));

    // 计算冲量
    double e = Math.min(bodyA.restitution, bodyB.restitution);
    FlatVector vAB = FlatVector.sub(bodyB.getLinearVelocity(), bodyA.getLinearVelocity());
    double j = -(1 + e) * FlatMath.dot(vAB, normal);
    j /= ((1 / bodyA.mass) + (1 / bodyB.mass));

    FlatVector dvA = FlatVector.multiply(normal.negative(), j / bodyA.mass);
    FlatVector dvB = FlatVector.multiply(normal, j / bodyB.mass);
    bodyA.setLinearVelocity(FlatVector.add(bodyA.getLinearVelocity(), dvA));
    bodyB.setLinearVelocity(FlatVector.add(bodyB.getLinearVelocity(), dvB));

  }

  // 碰撞检测：返回的法线方向，整体是由A指向B的
  private CollisionInfo detectCollision(FlatBody bodyA, FlatBody bodyB) {
    if (bodyA.bodyType.equals(BodyTypeEnum.CIRCLE)) {
      // circle-polygon
      if (bodyB.bodyType.equals(BodyTypeEnum.BOX) || bodyB.bodyType.equals(BodyTypeEnum.POLYGON)) {
        return Collisions.intersectCirclePolygon(bodyA.getPosition(), bodyA.radius,
            bodyB.getTransformVertices());
      }
      // circle-circle
      if (bodyB.bodyType.equals(BodyTypeEnum.CIRCLE)) {
        return Collisions.intersectCircles(bodyA.getPosition(), bodyA.radius, bodyB.getPosition(),
            bodyB.radius);
      }
    }

    if (bodyA.bodyType.equals(BodyTypeEnum.BOX) || bodyA.bodyType.equals(BodyTypeEnum.POLYGON)) {
      // polygon-circle
      if (bodyB.bodyType.equals(BodyTypeEnum.CIRCLE)) {
        CollisionInfo collisionInfo = Collisions.intersectCirclePolygon(bodyB.getPosition(),
            bodyB.radius, bodyA.getTransformVertices());
        // 需要返回相反的方向
        if (!collisionInfo.isHasCollision()) {
          return collisionInfo;
        } else {
          return new CollisionInfo(true,
              collisionInfo.getNormal().negative(), collisionInfo.getDepth());
        }
      }
      // polygon-polygon
      if (bodyB.bodyType.equals(BodyTypeEnum.BOX) || bodyB.bodyType.equals(BodyTypeEnum.POLYGON)) {
        return Collisions.intersectPolygons(bodyA.getTransformVertices(),
            bodyB.getTransformVertices());
      }
    }

    return new CollisionInfo();
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
      switch (body.bodyType) {
        case CIRCLE:
          FlatVector subVec = position.sub(body.getPosition());
          if (subVec.length() <= body.radius) {
            findBodies.add(body);
          }
          break;
        case BOX:
          if (isPointInPolygon(position, body.getTransformVertices())) {
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

  // 射线法判断点是否在多边形中，chatGpt生成
  private static boolean isPointInPolygon(FlatVector point, FlatVector[] polygon) {
    int intersectCount = 0;
    int n = polygon.length;

    for (int i = 0; i < n; i++) {
      FlatVector p1 = polygon[i];
      FlatVector p2 = polygon[(i + 1) % n];
      // 从point出发，射出一条沿x轴正向的射线
      if (point.y > Math.min(p1.y, p2.y) && point.y < Math.max(p1.y, p2.y)) {
        if (point.x <= Math.max(p1.x, p2.x) && p1.y != p2.y) {
          double x = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;
          if (p1.x == p2.x || point.x <= x) {
            intersectCount++;
          }
        }
      }
    }

    return intersectCount % 2 == 1;
  }


}
