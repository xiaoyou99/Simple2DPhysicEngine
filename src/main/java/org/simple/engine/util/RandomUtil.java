package org.simple.engine.util;

import java.awt.Color;
import java.util.Random;

/**
 * 随机工具类
 *
 * @since 2023/09/26
 */
public class RandomUtil {
  private static final Random random = new Random();

  /**
   * 获取随机颜色
   *
   * @return 随机颜色
   */
  public static Color getRandomColor() {
    return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
  }

  /**
   * 获取 [min, max) 之间的一个随机double
   *
   * @param min 最小
   * @param max 最大
   * @return [min, max) 之间的一个随机double
   */
  public static double randomDouble(double min, double max) {
    return random.nextDouble() * (max - min) + min;
  }
}
