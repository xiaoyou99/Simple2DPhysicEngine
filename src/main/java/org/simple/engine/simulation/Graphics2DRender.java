package org.simple.engine.simulation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D.Double;
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
        throw new RuntimeException("box not suupor now");
      case POLYGON:
        throw new RuntimeException("polygon not suupor now");
    }
  }

  private static void renderCircle(Graphics2D g2d, FlatBody flatBody, Color color, Camera camera) {
    // 画圆形
    FlatVector position = flatBody.getPosition();
    double radius = flatBody.radius;
    Double circle = new Double((position.x - radius) * camera.scale,
        (position.y - radius) * camera.scale, (2 * radius) * camera.scale,
        (2 * radius) * camera.scale);
    g2d.setColor(color);
    g2d.fill(circle);
    g2d.setColor(Color.WHITE);
    g2d.draw(circle);
  }
}
