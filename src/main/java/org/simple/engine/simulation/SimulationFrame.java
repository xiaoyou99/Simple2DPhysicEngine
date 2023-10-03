package org.simple.engine.simulation;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import lombok.Getter;
import org.simple.engine.physics.FlatWorld;

/**
 * 代表屏幕中，整个应用的窗口
 *
 * @since 2023/09/24
 */
@Getter
public class SimulationFrame extends JFrame {

  private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize()
      .getWidth();
  private static final int FRAME_WIDTH = SCREEN_WIDTH * 3 / 4;
  private static final int FRAME_HEIGHT = FRAME_WIDTH * 2 / 3;

  private static final double NANO_TO_BASE = 1.0E-9;

  // 画布
  private final SimulationPanel simulationPanel;

  // 物理世界
  private FlatWorld flatWorld;

  // 上次step的time
  private long lastNanoTime;

  public SimulationFrame(Camera camera, FlatWorld flatWorld) {
    // 初始化panel
    simulationPanel = new SimulationPanel(camera);
    getContentPane().add(simulationPanel);
    // 属性初始化
    this.flatWorld = flatWorld;
    // frame设置
    setTitle("2D Engine Demo");
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    pack();
    setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
  }

  public void start() {
    lastNanoTime = System.nanoTime();
    // 用一个守护线程不断刷新界面图像
    Thread refreshThread = new Thread(() -> {
      while(true) {
        // 游戏主循环
        gameLoop();

        try {
          Thread.sleep(5);
        } catch (InterruptedException e) {
        }
      }

    });
    refreshThread.setDaemon(true);
    refreshThread.start();
  }

  private void gameLoop() {
    // 处理事件
    // 更新物理世界
    long nowNano = System.nanoTime();
    double elapsedTime = (nowNano - lastNanoTime) / NANO_TO_BASE;
    boolean update = this.flatWorld.update(elapsedTime);
    if (!update) {
      return;
    }
    lastNanoTime = nowNano;
    // 触发panel重画
    this.simulationPanel.repaint();
  }


}
