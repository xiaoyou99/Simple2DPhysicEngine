package org.simple.engine.display.body;

import java.awt.Color;
import lombok.Getter;
import org.simple.engine.world.BodyTypeEnum;

/**
 * LinePic
 *
 * @since 2023/09/24
 */
@Getter
public class LinePicAbstract extends AbstractBodyPic {
  private float startX;

  private float startY;

  private float endX;

  private float endY;

  private Color color;

  public LinePicAbstract(float startX, float startY, float endX, float endY) {
    super(BodyTypeEnum.LINE);
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.color = Color.CYAN;
  }
}
