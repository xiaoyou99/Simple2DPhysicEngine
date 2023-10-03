package org.simple.engine.util;

/**
 * MathUtil
 *
 * @since 2023/10/04
 */
public class MathUtil {

  /**
   * 约束value的值，在[min, max] 这个闭区间之间
   *
   * @param value 入参
   * @param min 最小
   * @param max 最大
   * @return 约束后的值
   */
  public static double clamp(double value, double min, double max) {
    if (Double.compare(min, max) > 0) {
      throw new IllegalArgumentException("clamp func error! min cannot be greater than max");
    }
    if (Double.compare(value,min) < 0) {
      return min;
    }
    return Math.min(value, max);
  }
}
