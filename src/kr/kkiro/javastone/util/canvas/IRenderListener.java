package kr.kkiro.javastone.util.canvas;

import java.awt.Graphics2D;

public interface IRenderListener {
  public void render(Graphics2D g, int width, int height);
}
