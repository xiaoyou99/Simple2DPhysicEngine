package org.simple.engine.physics.collision;

import lombok.Getter;
import lombok.Setter;
import org.simple.engine.physics.FlatVector;

/**
 * 碰撞的信息
 *
 * @since 2023/10/06
 */
@Getter
@Setter
public class CollisionInfo {
  private boolean hasCollision;

  // 法线方向
  private FlatVector normal;

  // 深度
  private double depth;

  public CollisionInfo() {
    this.hasCollision = false;
    this.normal = null;
    this.depth = 0d;
  }

  public CollisionInfo(boolean hasCollision, FlatVector normal, double depth) {
    this.hasCollision = hasCollision;
    this.normal = normal;
    this.depth = depth;
  }
}
