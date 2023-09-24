package org.simple.engine.world;

/**
 * 刚体类型枚举
 *
 * @since 2023/09/24
 */
public enum BodyTypeEnum {
  CIRCLE(1),
  LINE(2);

  private Integer code;

  BodyTypeEnum(Integer code) {
    this.code = code;
  }

}
