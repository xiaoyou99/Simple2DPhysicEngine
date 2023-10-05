package org.simple.engine.simulation.input;

import java.util.List;

/**
 * InputHandler 接口
 *
 * @since 2023/10/03
 */
public interface InputHandler {

  /**
   * 安装
   */
  void install();

  /**
   * 卸载
   */
  void uninstall();

  /**
   * 当前时间是否生效状态
   *
   * @return 当前时间是否生效状态
   */
  boolean isActive();

  /**
   * 获取互斥Handler列表：部分操作之间应当是互斥的，例如，鼠标左键移动刚体和鼠标左键调整camera焦点，只有一个能生效
   *
   * @return 互斥行为列表
   */
  List<InputHandler> getMutexHandler();

  /**
   * 添加互斥Handler
   */
  void addMutexHandler(InputHandler inputHandler);
}
