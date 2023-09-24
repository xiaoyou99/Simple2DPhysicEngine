package org.simple.engine.display;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * 代表屏幕
 *
 * @since 2023/09/24
 */
public class MyScreen extends JFrame {

  private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize()
      .getWidth();
  public static final int FRAME_WIDTH = SCREEN_WIDTH * 3 / 4;
  public static final int FRAME_HEIGHT = FRAME_WIDTH * 2 / 3;

  public WorldPanel worldPanel;

  public MyScreen() {
    setTitle("2D Engine Demo");
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    // panel
    worldPanel = new WorldPanel();
    getContentPane().add(worldPanel);

    pack();
    setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
  }



}
