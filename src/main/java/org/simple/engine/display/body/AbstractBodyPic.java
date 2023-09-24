package org.simple.engine.display.body;

import lombok.Getter;
import org.simple.engine.world.BodyTypeEnum;

/**
 * 用于存储：物理实体映射到画布上时的属性
 *
 * @since 2023/09/24
 */
@Getter
public abstract class AbstractBodyPic {
  private BodyTypeEnum bodyType;

  public AbstractBodyPic(BodyTypeEnum bodyTypeEnum) {
    this.bodyType = bodyTypeEnum;
  }
}
