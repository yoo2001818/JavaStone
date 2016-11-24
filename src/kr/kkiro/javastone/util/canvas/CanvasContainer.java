package kr.kkiro.javastone.util.canvas;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

// A canvas container that allows drawing into the screen using IRenderListener.
public class CanvasContainer extends JPanel {
 
  private static final long serialVersionUID = 8863787379309473394L;
  private IRenderListener renderListener;
  
  public CanvasContainer() {
    this.setDoubleBuffered(true);
  }
  
  public void setRenderListener(IRenderListener renderListener) {
    this.renderListener = renderListener;
  }
  
  public IRenderListener getRenderListener() {
    return renderListener;
  }
  
  
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) (g.create());
    if (renderListener != null) {
      renderListener.render(g2d, getWidth(), getHeight());
    }
    g2d.dispose();
  }
}
