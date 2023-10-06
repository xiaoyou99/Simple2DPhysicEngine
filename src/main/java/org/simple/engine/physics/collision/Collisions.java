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

  public static boolean intersectPolygons(FlatVector[] verticesA, FlatVector[] verticesB) {
    for (int i = 0; i < verticesA.length; i++) {
      FlatVector edge = FlatVector.sub(verticesA[(i + 1) % verticesA.length], verticesA[i]);
      // 顺时针旋转90度，并normalize
      FlatVector normal = new FlatVector(-edge.y, edge.x);
      // 向法线方向投影
      double[] projectionA = projection(verticesA, normal);
      double[] projectionB = projection(verticesB, normal);
      // 判断是否有gap
      if (projectionA[1] < projectionB[0] || projectionB[1] < projectionA[0]) {
        return false;
      }
    }


    for (int i = 0; i < verticesB.length; i++) {
      FlatVector edge = FlatVector.sub(verticesB[i], verticesB[(i + 1) % verticesB.length]);
      // 顺时针旋转90度，并normalize
      FlatVector normal = new FlatVector(-edge.y, edge.x);
      // 向法线方向投影
      double[] projectionA = projection(verticesA, normal);
      double[] projectionB = projection(verticesB, normal);
      // 判断是否有gap
      if (projectionA[1] <= projectionB[0] || projectionB[1] <= projectionA[0]) {
        return false;
      }
    }

    return true;
  }

  private static double[] projection(FlatVector[] vertices, FlatVector projDirection) {
    double max = Double.NEGATIVE_INFINITY;
    double min = Double.POSITIVE_INFINITY;

    for (FlatVector vertex : vertices) {
      double dotResult = FlatMath.dot(vertex, projDirection);
      if (dotResult < min) {
        min = dotResult;
      }
      if (dotResult > max) {
        max = dotResult;
      }
    }
    return new double[] {min, max};
  }
}
