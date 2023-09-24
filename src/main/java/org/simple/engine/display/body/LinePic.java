package org.simple.engine.display.body;

import java.awt.Color;
import lombok.Getter;
import org.simple.engine.world.BodyTypeEnum;

/**
 * 画布上展示的线的信息
 *
 * @since 2023/09/24
 */
@Getter
public class LinePic extends AbstractBodyPic {
  private float startX;

  private float startY;

  private float endX;

  private float endY;

  private Color color;

  public LinePic(float startX, float startY, float endX, float endY) {
    super(BodyTypeEnum.LINE);
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.color = Color.CYAN;
  }
}
