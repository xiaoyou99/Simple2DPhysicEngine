package org.simple.engine.simulation;

import java.awt.Color;
import java.util.Random;

/**
 * 随机颜色工具类
 *
 * @since 2023/09/26
 */
public class RandomColorUtil {
  private static final Random random = new Random();

  public static Color getRandomColor() {
    return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
  }
}
