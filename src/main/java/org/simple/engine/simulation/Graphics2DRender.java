package org.simple.engine.simulation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import org.simple.engine.physics.FlatBody;
import org.simple.engine.physics.FlatVector;
import org.simple.engine.simulation.body.SimulationBody;

/**
 * Graphics2DRender, 静态工具类，用于画几何图形
 *
 * @since 2023/10/03
 */
public class Graphics2DRender {
  public static void render(Graphics2D g2d, SimulationBody simulationBody, Camera camera) {
    FlatBody flatBody = simulationBody.getFlatBody();
    switch (flatBody.bodyType) {
      case CIRCLE:
        renderCircle(g2d, simulationBody.getFlatBody(), simulationBody.getColor(), camera);
        break;
      case BOX:
        renderBox(g2d, simulationBody.getFlatBody(), simulationBody.getColor(), camera);
        break;
      case POLYGON:
        throw new RuntimeException("polygon not suupor now");
    }
  }

  private static void renderCircle(Graphics2D g2d, FlatBody flatBody, Color color, Camera camera) {
    // 画圆形
    FlatVector position = flatBody.getPosition();
    double radius = flatBody.radius;
    Ellipse2D.Double circle = new Ellipse2D.Double((position.x - radius) * camera.scale,
        (position.y - radius) * camera.scale, (2 * radius) * camera.scale,
        (2 * radius) * camera.scale);
    g2d.setColor(color);
    g2d.fill(circle);
    g2d.setColor(Color.WHITE);
    g2d.draw(circle);
  }

  private static void renderBox(Graphics2D g2d, FlatBody flatBody, Color color, Camera camera) {
//    double width = flatBody.width;
//    double height = flatBody.height;
//    FlatVector position = flatBody.getPosition();
//    Rectangle2D.Double rectangle = new Rectangle2D.Double((position.x - width / 2) * camera.scale,
//        (position.y - height / 2) * camera.scale, width * camera.scale,
//        height * camera.scale);

    FlatVector[] vertices = flatBody.getTransformVertices();
    // create the awt polygon
    Path2D.Double p = new Path2D.Double();
    p.moveTo(vertices[0].x * camera.scale, vertices[0].y * camera.scale);
    for (int i = 1; i < vertices.length; i++) {
      p.lineTo(vertices[i].x * camera.scale, vertices[i].y * camera.scale);
    }
    p.closePath();
    
    g2d.setColor(color);
    g2d.fill(p);
    g2d.setColor(Color.WHITE);
    g2d.draw(p);
  }
}
