package org.simple.engine.simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.simple.engine.simulation.body.SimulationBody;

/**
 * 画布，上面会画出所有的物理实体元素
 *
 * @since 2023/09/24
 */
public class SimulationPanel extends JPanel {
  private static final Color BACK_COLOR = new Color(110, 123, 139);

  private Camera camera;

  private List<SimulationBody> simulationBodies;

  public SimulationPanel(Camera camera) {
    super();
    this.setBackground(BACK_COLOR);
    this.camera = camera;
    this.simulationBodies = new ArrayList<>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2D = (Graphics2D) g;
    // 开启双缓冲、抗锯齿
    g2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    transformCoordinate(g2D);

    for (SimulationBody simulationBody : simulationBodies) {
      Graphics2DRender.render(g2D, simulationBody, camera);
    }
  }

  private void transformCoordinate(Graphics2D g2D) {
    // 翻转y轴
    AffineTransform yFlip = AffineTransform.getScaleInstance(1, -1);
    // 新坐标系原点设置为画面中心
    AffineTransform move = AffineTransform.getTranslateInstance(this.getWidth() / 2.0,
        - this.getHeight() / 2.0);
    // camera焦点偏移
    AffineTransform cameraMove = AffineTransform.getTranslateInstance(camera.offsetX, camera.offsetY);
    g2D.transform(yFlip);
    g2D.transform(move);
    g2D.transform(cameraMove);
  }

  public void addSimulationBody(SimulationBody body) {
    this.simulationBodies.add(body);
  }

  public Camera getCamera() {
    return this.camera;
  }
}
