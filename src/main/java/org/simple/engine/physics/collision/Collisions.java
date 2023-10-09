package org.simple.engine.physics.collision;

import org.simple.engine.physics.FlatMath;
import org.simple.engine.physics.FlatVector;

/**
 * 碰撞处理类
 *
 * @since 2023/10/06
 */
public class Collisions {

  /**
   * 圆和圆的碰撞判断
   * 
   * @param centerA centerA
   * @param radiusA radiusA
   * @param centerB centerB
   * @param radiusB radiusB
   * @return 碰撞信息，其中法线由A指向B
   */
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

  /**
   * 多边形之间的碰撞判断
   * 
   * @param verticesA A的顶点
   * @param verticesB B的顶点
   * @return 碰撞信息。其中发现由A指向B
   */
  public static CollisionInfo intersectPolygons(FlatVector[] verticesA, FlatVector[] verticesB) {
    double depth = Double.POSITIVE_INFINITY;
    FlatVector normal = null;

    // 以垂直于A的边的方向作为投影方向
    for (int i = 0; i < verticesA.length; i++) {
      FlatVector edge = FlatVector.sub(verticesA[(i + 1) % verticesA.length], verticesA[i]);
      // 顺时针旋转90度,并normalize
      FlatVector axis = FlatMath.normalize(new FlatVector(-edge.y, edge.x));
      // 向法线方向投影
      double[] projectionA = projection(verticesA, axis);
      double[] projectionB = projection(verticesB, axis);
      // 判断是否有gap, 如果有gap代表没有碰撞
      if (projectionA[1] < projectionB[0] || projectionB[1] < projectionA[0]) {
        return new CollisionInfo();
      }
      // 没有gap, 则试图找到最小的depth方向
      double currDepth = Math.min(projectionA[1] - projectionB[0], projectionB[1] - projectionA[0]);
      if (currDepth < depth) {
        depth = currDepth;
        normal = axis;
      }
    }

    // 重复上面的操作，但是需要以垂直于B的边的方向作为投影方向
    for (int i = 0; i < verticesB.length; i++) {
      FlatVector edge = FlatVector.sub(verticesB[i], verticesB[(i + 1) % verticesB.length]);
      FlatVector axis = FlatMath.normalize(new FlatVector(-edge.y, edge.x));

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

    // 这里指定发现方向，整体趋势是A指向B
    FlatVector centerA = findArithmeticMean(verticesA);
    FlatVector centerB = findArithmeticMean(verticesB);
    FlatVector directionB2A = FlatVector.sub(centerB, centerA);
    normal = FlatMath.dot(directionB2A, normal) > 0 ? normal : normal.negative();

    return new CollisionInfo(true, normal, depth);
  }

  /**
   * 圆和多边形的碰撞检测
   * 
   * @param center 圆心
   * @param radius 半径
   * @param vertices 多边形顶点
   * @return 碰撞信息，其中法线方向由圆指向多边形
   */
  public static CollisionInfo intersectCirclePolygon(FlatVector center, double radius, FlatVector[] vertices) {
    double depth = Double.POSITIVE_INFINITY;
    FlatVector normal = null;

    // 以垂直于多边形的边的方向作为投影方向
    for (int i = 0; i < vertices.length; i++) {
      FlatVector edge = FlatVector.sub(vertices[(i + 1) % vertices.length], vertices[i]);
      // 顺时针旋转90度, 并normalize
      FlatVector axis = FlatMath.normalize(new FlatVector(-edge.y, edge.x));
      // 向法线方向投影
      double[] projPolygon = projection(vertices, axis);
      double[] projCircle = projPolygonCircleToAxis(center, radius, axis);
      // 判断是否有gap, 如果有gap代表没有碰撞
      if (projPolygon[1] < projCircle[0] || projCircle[1] < projPolygon[0]) {
        return new CollisionInfo();
      }
      // 没有gap, 则试图找到最小的depth方向
      double currDepth = Math.min(projPolygon[1] - projCircle[0], projCircle[1] - projPolygon[0]);
      if (currDepth < depth) {
        depth = currDepth;
        normal = axis;
      }
    }

    // 还需要一条轴作为投影方向，这条轴的方向是[圆心]和[距离圆形最近的一个多边形顶点]的连线防线
    int vertexIndex = getClosestVertexIndexFromPoint(center, vertices);
    FlatVector axis = FlatMath.normalize(FlatVector.sub(vertices[vertexIndex], center));
    // 向法线方向投影
    double[] projPolygon = projection(vertices, axis);
    double[] projCircle = projPolygonCircleToAxis(center, radius, axis);
    // 判断是否有gap, 如果有gap代表没有碰撞
    if (projPolygon[1] < projCircle[0] || projCircle[1] < projPolygon[0]) {
      return new CollisionInfo();
    }
    // 没有gap, 则试图找到最小的depth方向
    double currDepth = Math.min(projPolygon[1] - projCircle[0], projCircle[1] - projPolygon[0]);
    if (currDepth < depth) {
      depth = currDepth;
      normal = axis;
    }

    // 这里指定法线方向，整体趋势是由圆指向多边形
    FlatVector centerPolygon = findArithmeticMean(vertices);
    FlatVector direction = FlatVector.sub(centerPolygon, center);
    normal = FlatMath.dot(direction, normal) > 0 ? normal : normal.negative();

    return new CollisionInfo(true, normal, depth);
  }

  private static int getClosestVertexIndexFromPoint(FlatVector center, FlatVector[] vertices) {
    double minDistance = Double.POSITIVE_INFINITY;
    int index = -1;

    for(int i = 0; i < vertices.length; i++) {
      FlatVector vertex = vertices[i];
      double currDistance = FlatMath.distance(vertex, center);
      if (currDistance < minDistance) {
        minDistance = currDistance;
        index = i;
      }
    }

    return index;
  }

  private static double[] projPolygonCircleToAxis(FlatVector center, double radius, FlatVector axis) {
    // radius * normalizedDirectionVector
    FlatVector directionVec = FlatVector.multiply(FlatMath.normalize(axis), radius);

    FlatVector p1 = FlatVector.add(center, directionVec);
    FlatVector p2 = FlatVector.add(center, directionVec.negative());
    
    double proj1 = FlatMath.dot(p1, axis);
    double proj2 = FlatMath.dot(p2, axis);

    return new double[] {Math.min(proj1, proj2), Math.max(proj1, proj2)};
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
