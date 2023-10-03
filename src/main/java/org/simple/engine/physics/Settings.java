package org.simple.engine.physics;

import lombok.Getter;

/**
 * 设置类
 *
 * @since 2023/10/03
 */
@Getter
public class Settings {
  public static final double DEFAULT_FREQUENCY = 60.0d;

  public static final double DEFAULT_PERIOD = 1 / DEFAULT_FREQUENCY;

  private double frequency;

  private double period;

  public Settings() {
    this.frequency = DEFAULT_FREQUENCY;
    this.period = DEFAULT_PERIOD;
  }

  // setters

  public void setFrequency(double frequency) {
    this.frequency = frequency;
    this.period = 1 / frequency;
  }

  public void setPeriod(double period) {
    this.period = period;
    this.frequency = 1 / period;
  }
}
