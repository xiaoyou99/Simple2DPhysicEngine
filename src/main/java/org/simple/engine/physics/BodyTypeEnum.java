package org.simple.engine.physics;

/**
 * 刚体类型枚举
 *
 * @since 2023/09/24
 */
public enum BodyTypeEnum {
  CIRCLE(1),
  BOX(2),
  POLYGON(3);

  private Integer code;

  BodyTypeEnum(Integer code) {
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }
}
