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

  public static CollisionInfo intersectPolygons(FlatVector[] verticesA, FlatVector[] verticesB) {
    double depth = Double.POSITIVE_INFINITY;
    FlatVector normal = null;

    // 以垂直于A的边的方向作为投影方向
    for (int i = 0; i < verticesA.length; i++) {
      FlatVector edge = FlatVector.sub(verticesA[(i + 1) % verticesA.length], verticesA[i]);
      // 顺时针旋转90度。不进行normalize
      FlatVector axis = new FlatVector(-edge.y, edge.x);
      // 向法线方向投影
      double[] projectionA = projection(verticesA, axis);
      double[] projectionB = projection(verticesB, axis);
      // 判断是否有gap, 如果有gap代表没有碰撞
      if (projectionA[1] < projectionB[0] || projectionB[1] < projectionA[0]) {
        return new CollisionInfo();
      }
      // 没有gap, 则试图招到最小的depth方向
      double currDepth = Math.min(projectionA[1] - projectionB[0], projectionB[1] - projectionA[0]);
      if (currDepth < depth) {
        depth = currDepth;
        normal = axis;
      }
    }

    // 重复上面的操作，但是需要以垂直于B的边的方向作为投影方向
    for (int i = 0; i < verticesB.length; i++) {
      FlatVector edge = FlatVector.sub(verticesB[i], verticesB[(i + 1) % verticesB.length]);
      FlatVector axis = new FlatVector(-edge.y, edge.x);

      double[] projectionA = projection(verticesA, axis);
      double[] projectionB = projection(verticesB, axis);

      if (projectionA[1] <= projectionB[0] || projectionB[1] <= projectionA[0]) {
        return new CollisionInfo();
      }
      double currDepth = Math.min(projectionA[1] - projectionB[0], projectionB[1] - projectionA[0]);
      if (currDepth < depth) {
        depth = currDepth;
        normal = axis;
      }
    }

    // 由于之前没有对axis做normalize，这里需要做normalize处理
    depth /= FlatVector.length(normal);
    normal = FlatMath.normalize(normal);
    // 这里指定发现方向，由A的中心指向B的中心
    FlatVector centerA = findArithmeticMean(verticesA);
    FlatVector centerB = findArithmeticMean(verticesB);
    FlatVector directionB2A = FlatVector.sub(centerB, centerA);
    normal = FlatMath.dot(directionB2A, normal) > 0 ? normal : normal.negative();

    return new CollisionInfo(true, normal, depth);
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

  private static FlatVector findArithmeticMean(FlatVector[] vertices) {
    double sumX = 0d;
    double sumY = 0d;

    for (FlatVector vertex : vertices) {
      sumX += vertex.x;
      sumY += vertex.y;
    }
    return new FlatVector(sumX / vertices.length, sumY / vertices.length);
  }
}
