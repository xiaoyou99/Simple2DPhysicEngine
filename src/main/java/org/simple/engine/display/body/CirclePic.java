package org.simple.engine.display.body;

import java.awt.Color;
import lombok.Getter;
import org.simple.engine.world.BodyTypeEnum;

/**
 * 画布上展示的圆形的信息
 *
 * @since 2023/09/24
 */
@Getter
public class CirclePic extends AbstractBodyPic {
  private float centerX;

  private float centerY;

  private float radius;

  private Color color;

  public CirclePic(float centerX, float centerY, float radius, int R, int G, int B) {
    super(BodyTypeEnum.CIRCLE);
    this.centerX = centerX;
    this.centerY = centerY;
    this.radius = radius;
    this.color = new Color(R, G, B);
  }
}
