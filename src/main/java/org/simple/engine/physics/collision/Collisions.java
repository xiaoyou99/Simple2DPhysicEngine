package org.simple.engine.physics.collision;

import org.simple.engine.physics.FlatMath;
import org.simple.engine.physics.FlatVector;

/**
 * 碰撞处理类
 *
 * @since 2023/10/06
 */
public class Collisions {


  public static CollisionInfo intersectCircles(FlatVector centerA, double radiusA,
      FlatVector centerB, double radiusB) {
    // vector from A to B
    FlatVector centerConnection = FlatVector.sub(centerB, centerA);
    double distance = FlatVector.length(centerConnection);
    if (distance > (radiusA + radiusB)) {
      return new CollisionInfo();
    }
    return new CollisionInfo(true, FlatMath.normalize(centerConnection),
         radiusA + radiusB - distance);
  }
}
