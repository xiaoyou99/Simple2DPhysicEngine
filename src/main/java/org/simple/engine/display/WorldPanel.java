package org.simple.engine.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.simple.engine.display.body.AbstractBodyPic;
import org.simple.engine.display.body.CirclePic;
import org.simple.engine.display.body.LinePic;

/**
 * WorldPanel 画布，上面会画出所有的物理实体元素
 *
 * @since 2023/09/24
 */
public class WorldPanel extends JPanel {
  // 用来绘画实体的信息
  private List<AbstractBodyPic> bodyPics;

  public WorldPanel() {
    super();
    this.bodyPics = new ArrayList<>();
    this.setBackground(new Color(110, 123, 139));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    for (AbstractBodyPic bodyPic : bodyPics) {
      if (bodyPic instanceof CirclePic) {
        drawCircle(g2, (CirclePic) bodyPic);
      }
      if (bodyPic instanceof LinePic) {
        drawLine(g2, (LinePic) bodyPic);
      }
    }
  }

  private void drawCircle(Graphics2D g2, CirclePic circlePic) {
    float radius = circlePic.getRadius();
    Ellipse2D ellipse = new Ellipse2D.Float(circlePic.getCenterX() - radius, circlePic.getCenterY() - radius,
        radius, radius);
    g2.setColor(Color.WHITE);
    g2.draw(ellipse);
    g2.setColor(circlePic.getColor());
    g2.fill(ellipse);
  }

  private void drawLine(Graphics2D g2, LinePic linePic) {
    Line2D line2D = new Line2D.Float(linePic.getStartX(), linePic.getStartY(), linePic.getEndX(), linePic.getEndY());
    g2.setColor(linePic.getColor());
    g2.draw(line2D);
  }

  /**
   * 更新实体信息
   *
   * @param bodyPics 实体信息列表
   */
  public void refresh(List<AbstractBodyPic> bodyPics) {
    this.bodyPics = bodyPics;
  }
}
