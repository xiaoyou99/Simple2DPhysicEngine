package org.simple.engine;

import java.util.Arrays;
import org.simple.engine.display.Camera;
import org.simple.engine.display.MyScreen;
import org.simple.engine.display.WorldPanel;
import org.simple.engine.display.body.LinePic;
import org.simple.engine.world.FlatVector;

/**
 * 程序入口
 */
public class App {
  private MyScreen myScreen;

  private WorldPanel worldPanel;

  public static void main(String[] args) {
    App app = new App();
    app.myScreen = new MyScreen();
    app.worldPanel = app.myScreen.getWorldPanel();

    FlatVector test = new FlatVector(100f, 100f);
    Camera camera = new Camera(app.worldPanel.getHeight());
    LinePic linePic = camera.FlatVector2LinePic(FlatVector.Zero, test);
    app.worldPanel.refresh(Arrays.asList(linePic));
    app.worldPanel.repaint();
  }
}
